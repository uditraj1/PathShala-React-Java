package com.pathshala.repository;

import com.pathshala.dao.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {

    Optional<AssignmentEntity> findByTopicId(Long topicId);
}
