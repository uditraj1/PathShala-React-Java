package com.pathshala.controller;

import com.pathshala.dto.StudyMaterialDTO;
import com.pathshala.service.StudyMaterialService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/studyMaterial")
public class StudyMaterialController {

    private StudyMaterialService studyMaterialService;

    private final Logger logger = LoggerFactory.getLogger(StudyMaterialController.class);

    @PostMapping
    public ResponseEntity<StudyMaterialDTO> saveOrUpdateStudyMaterial(@RequestBody StudyMaterialDTO studyMaterialDTO){
        logger.info("Entered saveOrUpdateStudyMaterial study material service");
        StudyMaterialDTO savedStudyMaterial = studyMaterialService.saveOrUpdateStudyMaterial(studyMaterialDTO);
        logger.info("Exited saveOrUpdateStudyMaterial study material service");
        return ResponseEntity.ok().body(savedStudyMaterial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyMaterialDTO> findById(@PathVariable Long id){
        logger.info("Started findByIdStudyMaterial study material service");
       StudyMaterialDTO studyMaterialDTO = studyMaterialService.findById(id);
        logger.info("Exited findByIdStudyMaterial study material service");
       return ResponseEntity.ok().body(studyMaterialDTO);
    }

}
