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

    private Pageable testNextPage(Pageable nextPage, String expectedStatusIds, boolean expectsNextPage) {
        Page<StatusEntity> statusPage = repository.findAll(nextPage);
        assertEquals(expectedStatusIds, statusPage.getContent().stream().map(p -> p.getStatusId()).collect(Collectors.toList()).toString());
        assertEquals(expectsNextPage, statusPage.hasNext());
        return statusPage.nextPageable();
    }

    private void assertEqualsStatus(StatusEntity expectedEntity, StatusEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getStatusId(),        actualEntity.getStatusId());
        assertEquals(expectedEntity.getUserId(),           actualEntity.getUserId());
        assertEquals(expectedEntity.getContent(),           actualEntity.getContent());
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


}
