package com.pathshala.controller;

import com.pathshala.dto.SubmissionDTO;
import com.pathshala.service.SubmissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@Disabled
public class SubmissionControllerTests {

    private SubmissionController submissionController;

    @Mock
    private SubmissionService submissionService;

    @BeforeEach
    void setup() {
        submissionController = new SubmissionController(submissionService);
    }

    @AfterEach
    void tearDown() {
        submissionController = null;
    }

    @Test
    void testSaveOrUpdateSubmission() {
        Mockito.when(submissionService.saveOrUpdateSubmission(Mockito.any())).thenReturn(submissionDTO());
        ResponseEntity<SubmissionDTO> testResponse = submissionController.saveOrUpdateSubmission(submissionDTO());
        Assertions.assertTrue(testResponse.hasBody());
        Assertions.assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(testResponse.getBody().getUserId(), 1);
    }

    @Test
    void testUpdateGrade() {
        Mockito.when(submissionService.updateGrade(Mockito.any())).thenReturn(submissionDTO());
        ResponseEntity<SubmissionDTO> updatedResponse = submissionController.updateSubmissionGrade(submissionDTO());
        Assertions.assertTrue(updatedResponse.hasBody());
        Assertions.assertEquals(updatedResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(updatedResponse.getBody().getUserId(), 1);
    }

    private SubmissionDTO submissionDTO() {
        return new SubmissionDTO(1L, 1, 1, "/testFilePath", 80f);
    }
}

