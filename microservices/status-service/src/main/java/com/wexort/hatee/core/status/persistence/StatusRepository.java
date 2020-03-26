package com.wexort.hatee.core.status.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface StatusRepository extends PagingAndSortingRepository<StatusEntity, String> {
    Optional<StatusEntity> findByStatusId(int statusId);
}
