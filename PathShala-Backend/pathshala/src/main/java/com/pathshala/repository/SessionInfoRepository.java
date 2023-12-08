package com.pathshala.repository;

import com.pathshala.dao.SessionInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionInfoRepository extends JpaRepository<SessionInfoEntity, Long> {

    List<SessionInfoEntity> findByUserIdAndIsActiveTrueOrderByIdDesc(Long userId);

    @Query(value = "update sessionInfo set isActive = '0' where createdOn <= now() - interval 1 hour and isActive = '1'"
            ,nativeQuery = true)
    @Modifying
    int expireToken();

    @Modifying
    @Query(value = "update sessionInfo set isActive = '0' where userId = :userId", nativeQuery = true)
    int expireSessionByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update sessionInfo set isActive = '0' where id = :id", nativeQuery = true)
    int expireTokenById(@Param("id") Long id);
}
