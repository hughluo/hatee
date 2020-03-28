package com.wexort.hatee.core.status;

import com.wexort.hatee.core.status.persistence.StatusRepository;
import com.wexort.hatee.core.status.persistence.StatusEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;
import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RunWith(SpringRunner.class)
@DataMongoTest
public class StatusPersistenceTests {

    @Autowired
    private StatusRepository repository;

    private StatusEntity savedEntity;

    @Before
    public void setupDb() {
        repository.deleteAll();

        StatusEntity entity = new StatusEntity(0, 0, 0, "I am status content created when setupDb in StatusPersistenceTests");
        savedEntity = repository.save(entity);

        assertEqualsStatus(entity, savedEntity);
    }

    @Test
    public void create() {

        StatusEntity newEntity = new StatusEntity(1, 1, 1, "I am status content");
        repository.save(newEntity);

        StatusEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsStatus(newEntity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        String contentUpdated = "I am status content updated";
        savedEntity.setContent(contentUpdated);
        repository.save(savedEntity);

        StatusEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals(contentUpdated, foundEntity.getContent());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByStatusId() {
        Optional<StatusEntity> entity = repository.findByStatusId(savedEntity.getStatusId());

        assertTrue(entity.isPresent());
        assertEqualsStatus(savedEntity, entity.get());
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateError() {
        StatusEntity entity = new StatusEntity(savedEntity.getStatusId(), 1, 1, "I am content from duplicateError test");
        repository.save(entity);
    }

    @Test
    public void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        StatusEntity entity1 = repository.findById(savedEntity.getId()).get();
        StatusEntity entity2 = repository.findById(savedEntity.getId()).get();

        String content1 = "content for entity1";
        String content2 = "content for entity2";

        // Update the entity using the first entity object
        entity1.setContent(content1);
        repository.save(entity1);

        //  Update the entity using the second entity object.
        // This should fail since the second entity now holds a old version number, i.e. a Optimistic Lock Error
        try {
            entity2.setContent(content2);
            repository.save(entity2);

            fail("Expected an OptimisticLockingFailureException");
        } catch (OptimisticLockingFailureException e) {}

        // Get the updated entity from the database and verify its new sate
        StatusEntity updatedEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (int)updatedEntity.getVersion());
        assertEquals(content1, updatedEntity.getContent());
    }

    @Test
    public void paging() {
        repository.deleteAll();
        List<StatusEntity> newProducts = rangeClosed(1001, 1010)
                .mapToObj(i -> new StatusEntity(i, 0, 0, "content with statusId " + i))
                .collect(Collectors.toList());
        repository.saveAll(newProducts);

        Pageable nextPage = PageRequest.of(0, 4, ASC, "statusId");
        nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]",
                true);
        nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]",
                true);
        nextPage = testNextPage(nextPage, "[1009, 1010]", false);
    }


    private void assertEqualsStatus(StatusEntity expectedEntity, StatusEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getStatusId(),        actualEntity.getStatusId());
        assertEquals(expectedEntity.getUserId(),           actualEntity.getUserId());
        assertEquals(expectedEntity.getContent(),           actualEntity.getContent());
    }

    private Pageable testNextPage(Pageable nextPage, String expectedStatusIds, boolean expectsNextPage) {
        Page<StatusEntity> statusPage = repository.findAll(nextPage);
        assertEquals(expectedStatusIds, statusPage.getContent()
                .stream().map(p -> p.getStatusId()).collect(Collectors.
                        toList()).toString());
        assertEquals(expectsNextPage, statusPage.hasNext());
        return statusPage.nextPageable();
    }

}
