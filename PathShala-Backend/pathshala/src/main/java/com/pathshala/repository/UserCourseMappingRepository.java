package com.pathshala.repository;

import com.pathshala.dao.UserCourseMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseMappingRepository extends JpaRepository<UserCourseMappingEntity, Long> {

    Optional<UserCourseMappingEntity> findByUserIdAndCourseId(Long userId, Long courseId);

    List<UserCourseMappingEntity> findAllByUserId(Long userId);

    List<UserCourseMappingEntity> findAllByCourseId(Long courseId);
}
