package com.pathshala.controller;

import com.pathshala.dto.SubmissionDTO;
import com.pathshala.service.SubmissionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {

    private final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    private SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionDTO> saveOrUpdateSubmission(@RequestBody @Valid SubmissionDTO submissionDTO){
        logger.info("Entered saveOrUpdateSubmission Submission API");
        SubmissionDTO submissionResponse = submissionService.saveOrUpdateSubmission(submissionDTO);
        logger.info("Exited saveOrUpdateSubmission Submission API");
        return ResponseEntity.ok().body(submissionResponse);
    }

    @PostMapping("/updateGrade")
    public ResponseEntity<SubmissionDTO> updateSubmissionGrade(@RequestBody @Valid SubmissionDTO submissionDTO){
        logger.info("Entered saveOrUpdateSubmission Submission API");
        SubmissionDTO submissionResponse = submissionService.updateGrade(submissionDTO);
        logger.info("Exited saveOrUpdateSubmission Submission API");
        return ResponseEntity.ok().body(submissionResponse);
    }



}
