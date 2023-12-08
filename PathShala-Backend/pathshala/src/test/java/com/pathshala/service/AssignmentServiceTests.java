package com.pathshala.service;

import com.pathshala.dao.AssignmentEntity;
import com.pathshala.dto.AssignmentDTO;
import com.pathshala.enums.UserType;
import com.pathshala.repository.AssignmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
//@Disabled
public class AssignmentServiceTests {

    private AssignmentService assignmentService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup(){
        assignmentService = new AssignmentService(assignmentRepository, modelMapper);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @AfterEach
    void tearDown(){
        assignmentService = null;
        modelMapper = null;
    }

    @Test
    void testFindAll(){
        Mockito.when(assignmentRepository.findAll()).thenReturn(assignmentDTOList());
        List<AssignmentDTO> assignmentList = assignmentService.findAll();
        Assertions.assertFalse(assignmentList.isEmpty());
        Assertions.assertEquals(assignmentList.size(), 2);
    }

    @Test
    void testSaveAssignment(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(null);
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(1L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(null));
        Assertions.assertEquals(savedAssignment.getId(), 1L);
    }

    @Test
    void testSaveAssignment2(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(null);
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(2L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(null));
        Assertions.assertEquals(savedAssignment.getId(), 2L);
    }

    @Test
    void testSaveAssignment3(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(null);
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(3L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(null));
        Assertions.assertEquals(savedAssignment.getId(), 3L);
    }

    @Test
    void testSaveAssignment4(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(null);
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(4L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(null));
        Assertions.assertEquals(savedAssignment.getId(), 4L);
    }

    @Test
    void testSaveAssignment5(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(null);
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(5L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(null));
        Assertions.assertEquals(savedAssignment.getId(), 5L);
    }

    @Test
    void testUpdateAssignment(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(Optional.of(assignmentEntity(1L)));
        Mockito.when(assignmentRepository.save(Mockito.any())).thenReturn(assignmentEntity(1L));
        AssignmentDTO savedAssignment = assignmentService.saveOrUpdate(assignmentDTO(1L));
        Assertions.assertEquals(savedAssignment.getId(), 1L);

    }

    @Test
    void testFindById(){
        Mockito.when(assignmentRepository.findById(Mockito.any())).thenReturn(Optional.of(assignmentEntity(2L)));
        AssignmentDTO assignmentDTO = assignmentService.findById(2L);
        Assertions.assertEquals(assignmentDTO.getId(),2L);
    }

    @Test
    void testFindByTopicId(){
        Mockito.when(assignmentRepository.findByTopicId(Mockito.any())).thenReturn(Optional.of(assignmentEntity(2L)));
        AssignmentDTO assignmentEntity = assignmentService.findByTopicId(2L);
        Assertions.assertEquals(assignmentEntity.getTopicId(), 2L);
    }

    private List<AssignmentEntity> assignmentDTOList(){
        //create a random Date object
        Date date = new Date();
        //Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        List<AssignmentEntity> assignmentEntityList = new ArrayList<>();
        assignmentEntityList.add(new AssignmentEntity(1L, "test", "testDesc", timestamp, 50f, "test", 1L));
        assignmentEntityList.add(new AssignmentEntity(2L, "test2", "testDesc2", timestamp, 60f, "test2", 2L));
        return assignmentEntityList;
    }

    private AssignmentEntity assignmentEntity(Long id){
        Date date = new Date();
        //Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        return new AssignmentEntity(id, "test2", "testDesc2", timestamp, 60f, "test2", 2L);
    }

    private AssignmentDTO assignmentDTO(Long id){
        Date date = new Date();
        //Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        return new AssignmentDTO(id, "test2", "testDesc2", timestamp, 60f, "test2", 2L, 1L, UserType.ADMIN);
    }

}
