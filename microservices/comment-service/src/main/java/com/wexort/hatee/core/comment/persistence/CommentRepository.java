package com.wexort.hatee.core.comment.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentRepository extends CrudRepository<CommentEntity, Integer>{

    @Transactional(readOnly = true)
    List<CommentEntity> findByStatusId(int statusId);
}
