package com.pathshala.service;

import com.pathshala.dao.SubmissionEntity;
import com.pathshala.dto.SubmissionDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
//@Disabled
public class SubmissionServiceTests {

    private SubmissionService submissionService;

    @Mock
    private SubmissionRepository submissionRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void init(){
        submissionService = new SubmissionService(submissionRepository, modelMapper);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    public void saveOrUpdateSubmission_ValidSubmission_ReturnsDTO() {
        // Given
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(1L);

        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        // When
        SubmissionDTO result = submissionService.saveOrUpdateSubmission(submissionDTO);

        // Then
        assertNotNull(result);
        assertEquals(submissionDTO.getId(), result.getId());
        assertEquals(submissionDTO.getId(), result.getId());
        verify(submissionRepository).save(submissionEntity);
    }

    @Test
    public void saveOrUpdateSubmission_ValidSubmission_ReturnsDTO2() {
        // Given
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(2L);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(2L);

        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        // When
        SubmissionDTO result = submissionService.saveOrUpdateSubmission(submissionDTO);

        // Then
        assertNotNull(result);
        assertEquals(submissionDTO.getId(), result.getId());
        assertEquals(submissionDTO.getId(), result.getId());
        verify(submissionRepository).save(submissionEntity);
    }

    @Test
    public void saveOrUpdateSubmission_ValidSubmission_ReturnsDTO3() {
        // Given
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(3L);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(3L);

        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        // When
        SubmissionDTO result = submissionService.saveOrUpdateSubmission(submissionDTO);

        // Then
        assertNotNull(result);
        assertEquals(submissionDTO.getId(), result.getId());
        assertEquals(submissionDTO.getId(), result.getId());
        verify(submissionRepository).save(submissionEntity);
    }

    @Test
    void testUpdateGrade_Success() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setGradeReceived(95.0f);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(1L);
        submissionEntity.setGradeReceived(80.0f);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submissionEntity));
        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        SubmissionDTO updatedSubmission = submissionService.updateGrade(submissionDTO);

        assertNotNull(updatedSubmission);
        assertEquals(submissionDTO.getGradeReceived(), updatedSubmission.getGradeReceived());
    }

    @Test
    void testUpdateGrade_Success2() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setGradeReceived(95.0f);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(1L);
        submissionEntity.setGradeReceived(70.0f);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submissionEntity));
        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        SubmissionDTO updatedSubmission = submissionService.updateGrade(submissionDTO);

        assertNotNull(updatedSubmission);
        assertEquals(submissionDTO.getGradeReceived(), updatedSubmission.getGradeReceived());
    }

    @Test
    void testUpdateGrade_Success3() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setGradeReceived(9.0f);

        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setId(1L);
        submissionEntity.setGradeReceived(80.0f);

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submissionEntity));
        when(submissionRepository.save(any(SubmissionEntity.class))).thenReturn(submissionEntity);

        SubmissionDTO updatedSubmission = submissionService.updateGrade(submissionDTO);

        assertNotNull(updatedSubmission);
        assertEquals(submissionDTO.getGradeReceived(), updatedSubmission.getGradeReceived());
    }

    @Test
    void testUpdateGrade_InvalidSubmission() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setGradeReceived(95.0f);

        when(submissionRepository.findById(1L)).thenReturn(Optional.empty());

        GenericExceptions exception = assertThrows(GenericExceptions.class, () -> {
            submissionService.updateGrade(submissionDTO);
        });

        assertEquals(ErrorCodes.COURSE_NOT_FOUND, exception.getErrorCode());
        assertEquals("Submission not found", exception.getMessage());
    }

    @Test
    void testUpdateGrade_InvalidSubmission2() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(2L);
        submissionDTO.setGradeReceived(95.0f);

        when(submissionRepository.findById(2L)).thenReturn(Optional.empty());

        GenericExceptions exception = assertThrows(GenericExceptions.class, () -> {
            submissionService.updateGrade(submissionDTO);
        });

        assertEquals(ErrorCodes.COURSE_NOT_FOUND, exception.getErrorCode());
        assertEquals("Submission not found", exception.getMessage());
    }

    @Test
    void testUpdateGrade_MissingGrade() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setGradeReceived(Float.NaN);

        GenericExceptions exception = assertThrows(GenericExceptions.class, () -> {
            submissionService.updateGrade(submissionDTO);
        });

        assertEquals(ErrorCodes.MISSING_GRADE, exception.getErrorCode());
        assertEquals("Missing grade!", exception.getMessage());
    }

    @Test
    void testUpdateGrade_MissingGrade2() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(2L);
        submissionDTO.setGradeReceived(Float.NaN);

        GenericExceptions exception = assertThrows(GenericExceptions.class, () -> {
            submissionService.updateGrade(submissionDTO);
        });

        assertEquals(ErrorCodes.MISSING_GRADE, exception.getErrorCode());
        assertEquals("Missing grade!", exception.getMessage());
    }

    @Test
    void testUpdateGrade_MissingGrade3() {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(3L);
        submissionDTO.setGradeReceived(Float.NaN);

        GenericExceptions exception = assertThrows(GenericExceptions.class, () -> {
            submissionService.updateGrade(submissionDTO);
        });

        assertEquals(ErrorCodes.MISSING_GRADE, exception.getErrorCode());
        assertEquals("Missing grade!", exception.getMessage());
    }


}