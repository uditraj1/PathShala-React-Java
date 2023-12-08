package com.pathshala.controller;

import com.pathshala.dto.AssignmentDTO;
import com.pathshala.service.AssignmentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
public class AssignmentController {

    private final Logger logger = LoggerFactory.getLogger(AssignmentController.class);
    private AssignmentService assignmentService;

    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> findAll(){
        logger.info("Call findAllAssignment API");
        List<AssignmentDTO> courseDTOList = assignmentService.findAll();
        logger.info("Exit findAllAssignment API");
        return ResponseEntity.ok().body(courseDTOList);
    }

    @PostMapping
    public ResponseEntity<AssignmentDTO> saveOrUpdate(@RequestBody @Valid AssignmentDTO course) {
        logger.info("Call saveOrUpdateAssignment API");
        AssignmentDTO savedCourse = assignmentService.saveOrUpdate(course);
        logger.info("Exit saveOrUpdateAssignment API");
        return ResponseEntity.ok().body(savedCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> findById(@PathVariable Long id){
        logger.info("Call findByIdAssignment API");
        AssignmentDTO courseDTO = assignmentService.findById(id);
        logger.info("Exit findByIdAssignment API");
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping("/topic")
    public ResponseEntity<AssignmentDTO> findByTopicId(@RequestParam Long topicId){
        logger.info("Call findByTopicIdAssignment API");
        AssignmentDTO courseDTO = assignmentService.findByTopicId(topicId);
        logger.info("Exit findByTopicIdAssignment API");
        return ResponseEntity.ok().body(courseDTO);
    }

}
