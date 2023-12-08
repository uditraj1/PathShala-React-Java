package com.pathshala.controller;

import com.pathshala.dto.AssignmentDTO;
import com.pathshala.enums.UserType;
import com.pathshala.service.AssignmentService;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
@Disabled
public class AssignmentControllerTests {

    @Mock
    private AssignmentService assignmentService;

    private AssignmentController assignmentController;

    @BeforeEach
    void setup(){
        assignmentController = new AssignmentController(assignmentService);
    }

    @AfterEach
    void tearDown(){
        assignmentController = null;
    }
    @Test
    void testFindAllAssignmentsWhenRecordExists(){
        Mockito.when(assignmentService.findAll()).thenReturn(assignmentDTOList());
        //Mockito.when(assignmentService.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<AssignmentDTO>> assignmentListResponse = assignmentController.findAll();
        //check for has body
        Assertions.assertTrue(assignmentListResponse.hasBody());
        Assertions.assertEquals(assignmentListResponse.getStatusCode(), HttpStatus.OK);
        List<AssignmentDTO> assignmentList = assignmentListResponse.getBody();
        //test for null/not null
        assert assignmentList != null;
        Assertions.assertEquals(assignmentList.size(), 2);
        Assertions.assertEquals(assignmentList.get(0).getId(), 1L);
    }

    @Test
    void testFindAllAssignmentsWhenRecordDoesNotExists(){
        // Mocking the behavior of the assignmentService.findAll() when no records exist
        Mockito.when(assignmentService.findAll()).thenReturn(new ArrayList<>());

        // Invoking the controller method
        ResponseEntity<List<AssignmentDTO>> assignmentListResponse = assignmentController.findAll();

        // Asserting the response
        Assertions.assertTrue(assignmentListResponse.hasBody());
        Assertions.assertEquals(assignmentListResponse.getStatusCode(), HttpStatus.OK);

        // Checking that the returned list is empty
        List<AssignmentDTO> assignmentList = assignmentListResponse.getBody();
        // Check that the returned list is not null
        Assertions.assertNotNull(assignmentList);
        Assertions.assertTrue(assignmentList.isEmpty());


    }

    @Test
    void testSaveAssignment() {
        String assignmentName = "Test1";
        // Create a sample AssignmentDTO for testing
        Mockito.when(assignmentService.saveOrUpdate(Mockito.any())).thenReturn(createTempAssignment(assignmentName));
        ResponseEntity<AssignmentDTO> testResponse = assignmentController.saveOrUpdate(createTempAssignment(assignmentName));
        Assertions.assertTrue(testResponse.hasBody());
        Assertions.assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(testResponse.getBody()).getName(), assignmentName);

    }

    @Test
    void testUpdateAssignment(){
        String assignmentName = "Test1";
        //save the assignment and test if its saved
        Mockito.when(assignmentService.saveOrUpdate(Mockito.any())).thenReturn(createTempAssignment(assignmentName));
        ResponseEntity<AssignmentDTO> testResponse = assignmentController.saveOrUpdate(createTempAssignment(assignmentName));
        Assertions.assertTrue(testResponse.hasBody());
        Assertions.assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(testResponse.getBody()).getName(), assignmentName);

        //update the same object with different name
        String updatedAssignmentName = "Test11";
        Mockito.when(assignmentService.saveOrUpdate(Mockito.any())).thenReturn(createTempAssignment(updatedAssignmentName));
        ResponseEntity<AssignmentDTO> newTestResponse = assignmentController.saveOrUpdate(createTempAssignment(updatedAssignmentName));
        Assertions.assertTrue(newTestResponse.hasBody());
        Assertions.assertEquals(newTestResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(newTestResponse.getBody()).getName(), updatedAssignmentName);
    }

    @Test
    void testFindAssignmentByIdWhenRecordExists() {
        Long assignmentId = 1L;
        Mockito.when(assignmentService.findById(assignmentId)).thenReturn(createTempAssignmentID(assignmentId));
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findById(assignmentId);


        // Invoking the controller method

        // Asserting the response
        Assertions.assertTrue(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);

        // Checking that the returned AssignmentDTO matches the expected one
        AssignmentDTO assignmentList = assignmentResponse.getBody();
        assert assignmentList != null;
        Assertions.assertEquals(assignmentList.getId(), 1L);
        Assertions.assertEquals(assignmentList.getName(), "Test1");
        Assertions.assertEquals(assignmentList.getDescription(),"Description1");
        // Add more assertions for other fields if needed
    }

    @Test
    void testFindAssignmentByIdWhenRecordDoesNotExist() {
        // Mocking the behavior of the assignmentService.findById() when no record exists
        Long assignmentId = 2L;
        Mockito.when(assignmentService.findById(assignmentId)).thenReturn(null);

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findById(assignmentId);

        // Asserting the response
        Assertions.assertFalse(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testFindByTopicIdWhenRecordExists() {
        // Mocking the behavior of the assignmentService.findByTopicId() when a record exists
        Mockito.when(assignmentService.findByTopicId(Mockito.anyLong())).thenReturn(createTempTopicID(Mockito.anyLong()));

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findByTopicId(1L);

        // Asserting the response
        Assertions.assertTrue(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);

        // Checking that the returned AssignmentDTO matches the expected one
        AssignmentDTO assignmentList = assignmentResponse.getBody();
        assert assignmentList != null;
        Assertions.assertEquals(assignmentList.getTopicId(), 1L);
        Assertions.assertEquals(assignmentList.getName(), "Test1");
        Assertions.assertEquals(assignmentList.getDescription(),"Description1");
    }

    @Test
    void testFindByTopicIdWhenRecordExists2() {
        // Mocking the behavior of the assignmentService.findByTopicId() when a record exists
        Mockito.when(assignmentService.findByTopicId(Mockito.anyLong())).thenReturn(createTempTopicID(3L));

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findByTopicId(3L);

        // Asserting the response
        Assertions.assertTrue(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);

        // Checking that the returned AssignmentDTO matches the expected one
        AssignmentDTO assignmentList = assignmentResponse.getBody();
        assert assignmentList != null;
        Assertions.assertEquals(assignmentList.getId(), 3L);
        Assertions.assertEquals(assignmentList.getName(), "Test1");
        Assertions.assertEquals(assignmentList.getDescription(),"Description1");
    }

    @Test
    void testFindByTopicIdWhenRecordExists3() {
        // Mocking the behavior of the assignmentService.findByTopicId() when a record exists
        Mockito.when(assignmentService.findByTopicId(Mockito.anyLong())).thenReturn(createTempTopicID(4L));

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findByTopicId(4L);

        // Asserting the response
        Assertions.assertTrue(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);

        // Checking that the returned AssignmentDTO matches the expected one
        AssignmentDTO assignmentList = assignmentResponse.getBody();
        assert assignmentList != null;
        Assertions.assertEquals(assignmentList.getId(), 4L);
        Assertions.assertEquals(assignmentList.getName(), "Test1");
        Assertions.assertEquals(assignmentList.getDescription(),"Description1");
    }
    @Test
    void testFindByTopicIdWhenRecordDoesNotExist() {
        // Mocking the behavior of the assignmentService.findByTopicId() when no record exists
        Long topicId = 2L;
        Mockito.when(assignmentService.findByTopicId(topicId)).thenReturn(null);

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findByTopicId(topicId);

        // Asserting the response
        Assertions.assertFalse(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testFindByTopicIdWhenRecordDoesNotExist2() {
        // Mocking the behavior of the assignmentService.findByTopicId() when no record exists
        Long topicId = 2L;
        Mockito.when(assignmentService.findByTopicId(topicId)).thenReturn(null);

        // Invoking the controller method
        ResponseEntity<AssignmentDTO> assignmentResponse = assignmentController.findByTopicId(topicId);

        // Asserting the response
        Assertions.assertFalse(assignmentResponse.hasBody());
        Assertions.assertEquals(assignmentResponse.getStatusCode(), HttpStatus.OK);
    }


    private List<AssignmentDTO> assignmentDTOList(){
        // Create a random Date object
        Date date = new Date();
        // Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());

        List<AssignmentDTO> res = new ArrayList<>();
        res.add(new AssignmentDTO(1L, "Test1", "Test11", timestamp , 100.00f , "/testFilePath1", 1L,1L, UserType.ADMIN));
        res.add(new AssignmentDTO(2L, "Test2", "Test22", timestamp , 200.00f , "/testFilePath2", 1L, 2L, UserType.ADMIN));
        return res;
    }

    private AssignmentDTO createTempAssignment(String name) {
        // Create a random Date object
        Date date = new Date();
        // Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        return new AssignmentDTO(1L, name, "Description1", timestamp, 100f, "/testFilePath1", 1L, 1L, UserType.ADMIN);
    }

    private AssignmentDTO createTempAssignmentID(Long id){
        //create a random Date object
        Date date = new Date();
        //Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        return new AssignmentDTO(id, "Test1", "Description1", timestamp, 100f, "/testFilePath1", 1L, 1L, UserType.ADMIN);
    }
    private AssignmentDTO createTempTopicID(Long topicId){
        //create a random Date object
        Date date = new Date();
        //Create a Timestamp object from the Date object
        Timestamp timestamp = new Timestamp(date.getTime());
        return new AssignmentDTO(topicId, "Test1", "Description1", timestamp, 100f, "/testFilePath1", 1L, 1L, UserType.ADMIN);
    }

}
