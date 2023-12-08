package com.pathshala.repository;

import com.pathshala.dao.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {


    List<CourseEntity> findByUserIdAndIsActiveTrue(Long userId);

    List<CourseEntity> findByIsCoursePublishedTrue();

    @Modifying
    @Query(value = "update course set isActive = '0' where id = :courseId", nativeQuery = true)
    int markCourseInActive(@Param("courseId") Long courseId);

    List<CourseEntity> findAllByIsActiveTrue();

    Optional<CourseEntity> findByIdAndIsActiveTrue(Long id);
}
