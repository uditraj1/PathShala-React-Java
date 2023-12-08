package com.pathshala.controller;

import com.pathshala.dto.StudyMaterialDTO;
import com.pathshala.service.StudyMaterialService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class StudyMaterialControllerTests {

    private StudyMaterialController studyMaterialController;

    @Mock
    private StudyMaterialService studyMaterialService;

    @BeforeEach
    void init(){
        studyMaterialController = new StudyMaterialController(studyMaterialService);
    }

    @AfterEach
    void teardown(){
        studyMaterialController = null;
    }

    @Test
    public void testSaveOrUpdateStudyMaterial() {
        // Mocked input data
        StudyMaterialDTO inputStudyMaterialDTO = new StudyMaterialDTO(/* Initialize with required data */);
        StudyMaterialDTO expectedSavedStudyMaterial = new StudyMaterialDTO(/* Initialize with expected saved data */);

        // Mocking the service method call
        when(studyMaterialService.saveOrUpdateStudyMaterial(inputStudyMaterialDTO)).thenReturn(expectedSavedStudyMaterial);

        // Performing the method call
        ResponseEntity<StudyMaterialDTO> responseEntity = studyMaterialController.saveOrUpdateStudyMaterial(inputStudyMaterialDTO);

        // Assertions
        assertEquals(200, responseEntity.getStatusCodeValue()); // Asserting HTTP status code
        assertEquals(expectedSavedStudyMaterial, responseEntity.getBody()); // Asserting the saved study material
    }

    @Test
    public void findById_ValidId_ReturnsStudyMaterialDTO() {
        // Given
        Long id = 1L;
        StudyMaterialDTO expectedDTO = new StudyMaterialDTO(); // Assuming a valid StudyMaterialDTO
        when(studyMaterialService.findById(id)).thenReturn(expectedDTO);

        // When
        ResponseEntity<StudyMaterialDTO> response = studyMaterialController.findById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
        verify(studyMaterialService, times(1)).findById(id);
    }

    @Test
    public void findById_ValidId_ReturnsStudyMaterialDTO2() {
        // Given
        Long id = 2L;
        StudyMaterialDTO expectedDTO = new StudyMaterialDTO(); // Assuming a valid StudyMaterialDTO
        when(studyMaterialService.findById(id)).thenReturn(expectedDTO);

        // When
        ResponseEntity<StudyMaterialDTO> response = studyMaterialController.findById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
        verify(studyMaterialService, times(1)).findById(id);
    }

}