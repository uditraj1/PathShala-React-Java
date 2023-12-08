package com.pathshala.repository;

import com.pathshala.dao.StudyMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterialEntity, Long> {



}
