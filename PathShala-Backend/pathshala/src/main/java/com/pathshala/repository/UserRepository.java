package com.pathshala.repository;

import com.pathshala.dao.UserEntity;
import com.pathshala.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserIdAndIsActiveTrue(String userId);

    Optional<UserEntity> findByUserIdAndPasswordAndIsActiveTrue(String userId, String password);

    List<UserEntity> findAllByUserTypeAndIsActiveTrue(UserType userType);

    @Modifying
    @Query(value = "update user set isActive = '0' where id = :userId", nativeQuery = true)
    int markUserInActive(@Param("userId") Long userId);

    List<UserEntity> findByIdIn(List<Long> id);
}

  