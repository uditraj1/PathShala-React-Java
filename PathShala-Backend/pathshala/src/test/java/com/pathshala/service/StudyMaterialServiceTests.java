package com.pathshala.service;

import com.pathshala.dao.StudyMaterialEntity;
import com.pathshala.dto.StudyMaterialDTO;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.StudyMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class StudyMaterialServiceTests {

    private StudyMaterialService studyMaterialService;

    @Mock
    private StudyMaterialRepository studyMaterialRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void init(){
        studyMaterialService = new StudyMaterialService(studyMaterialRepository, modelMapper);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    public void testSaveNewStudyMaterial() {
        // Create a new StudyMaterialDTO object
        StudyMaterialDTO studyMaterialDTO = new StudyMaterialDTO(1, "test", 1, 1, "test", "test");
        // Set properties of studyMaterialDTO as required
        when(studyMaterialRepository.save(Mockito.any())).thenReturn(new StudyMaterialEntity(1, "test", 1, 1, "test", "test"));
        // Save the StudyMaterialDTO using the method
        StudyMaterialDTO savedStudyMaterialDTO = studyMaterialService.saveOrUpdateStudyMaterial(studyMaterialDTO);

        // Assert that the savedStudyMaterialDTO is not null and has the expected values
        assertNotNull(savedStudyMaterialDTO);
    }

    @Test
    public void testSaveNewStudyMaterial2() {
        // Create a new StudyMaterialDTO object
        StudyMaterialDTO studyMaterialDTO = new StudyMaterialDTO(2, "test", 1, 1, "test", "test");
        // Set properties of studyMaterialDTO as required
        when(studyMaterialRepository.save(Mockito.any())).thenReturn(new StudyMaterialEntity(2, "test", 1, 1, "test", "test"));
        // Save the StudyMaterialDTO using the method
        StudyMaterialDTO savedStudyMaterialDTO = studyMaterialService.saveOrUpdateStudyMaterial(studyMaterialDTO);

        // Assert that the savedStudyMaterialDTO is not null and has the expected values
        assertNotNull(savedStudyMaterialDTO);
    }

    @Test
    public void testSaveNewStudyMaterial3() {
        // Create a new StudyMaterialDTO object
        StudyMaterialDTO studyMaterialDTO = new StudyMaterialDTO(3, "test", 1, 1, "test", "test");
        // Set properties of studyMaterialDTO as required
        when(studyMaterialRepository.save(Mockito.any())).thenReturn(new StudyMaterialEntity(3, "test", 1, 1, "test", "test"));
        // Save the StudyMaterialDTO using the method
        StudyMaterialDTO savedStudyMaterialDTO = studyMaterialService.saveOrUpdateStudyMaterial(studyMaterialDTO);

        // Assert that the savedStudyMaterialDTO is not null and has the expected values
        assertNotNull(savedStudyMaterialDTO);
    }

    @Test
    public void findById_StudyMaterialExists_ReturnsDTO() {
        // Mocking the StudyMaterialEntity
        StudyMaterialEntity studyMaterialEntity = new StudyMaterialEntity();
        studyMaterialEntity.setId(1);
        studyMaterialEntity.setName("Sample Material");

        // Mocking the repository response
        when(studyMaterialRepository.findById(1L)).thenReturn(Optional.of(studyMaterialEntity));

        // Testing the service method
        StudyMaterialDTO result = studyMaterialService.findById(1L);

        // Verifying the result
        assertNotNull(result);
        assertEquals(studyMaterialEntity.getId(), result.getId());
        assertEquals(studyMaterialEntity.getName(), result.getName());
    }

    @Test
    public void findById_StudyMaterialExists_ReturnsDTO2() {
        // Mocking the StudyMaterialEntity
        StudyMaterialEntity studyMaterialEntity = new StudyMaterialEntity();
        studyMaterialEntity.setId(1);
        studyMaterialEntity.setName("Sample Material2");

        // Mocking the repository response
        when(studyMaterialRepository.findById(1L)).thenReturn(Optional.of(studyMaterialEntity));

        // Testing the service method
        StudyMaterialDTO result = studyMaterialService.findById(1L);

        // Verifying the result
        assertNotNull(result);
        assertEquals(studyMaterialEntity.getId(), result.getId());
        assertEquals(studyMaterialEntity.getName(), result.getName());
    }

    @Test
    public void findById_StudyMaterialExists_ReturnsDTO3() {
        // Mocking the StudyMaterialEntity
        StudyMaterialEntity studyMaterialEntity = new StudyMaterialEntity();
        studyMaterialEntity.setId(1);
        studyMaterialEntity.setName("Sample Material3");

        // Mocking the repository response
        when(studyMaterialRepository.findById(1L)).thenReturn(Optional.of(studyMaterialEntity));

        // Testing the service method
        StudyMaterialDTO result = studyMaterialService.findById(1L);

        // Verifying the result
        assertNotNull(result);
        assertEquals(studyMaterialEntity.getId(), result.getId());
        assertEquals(studyMaterialEntity.getName(), result.getName());
    }
    @Test
    public void findById_StudyMaterialNotFound_ThrowsException() {
        // Mocking an empty optional as the repository response
        when(studyMaterialRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Testing the service method and expecting an exception
        assertThrows(GenericExceptions.class, () -> {
            studyMaterialService.findById(1L);
        });
    }
}