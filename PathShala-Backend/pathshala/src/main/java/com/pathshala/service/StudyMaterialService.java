package com.pathshala.service;

import com.pathshala.dao.StudyMaterialEntity;
import com.pathshala.dto.StudyMaterialDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.StudyMaterialRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudyMaterialService {

    private StudyMaterialRepository studyMaterialRepository;

    private ModelMapper modelMapper;

    public StudyMaterialDTO saveOrUpdateStudyMaterial(StudyMaterialDTO studyMaterialDTO){
        StudyMaterialEntity savedStudyMaterial = studyMaterialRepository.save(modelMapper.map(studyMaterialDTO, StudyMaterialEntity.class));
        return modelMapper.map(savedStudyMaterial, StudyMaterialDTO.class);
    }

    public StudyMaterialDTO findById(Long id){
        Optional<StudyMaterialEntity> studyMaterial = studyMaterialRepository.findById(id);
        if(studyMaterial.isPresent()){
           return modelMapper.map(studyMaterial.get(), StudyMaterialDTO.class);
        }
        throw new GenericExceptions(ErrorCodes.COURSE_NOT_FOUND, "Study material not found");
    }

}
