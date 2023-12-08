package com.pathshala.controller;

import com.pathshala.dto.CourseDTO;
import com.pathshala.enums.UserType;
import com.pathshala.service.CourseService;
import com.pathshala.service.UserCourseMappingService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class CourseControllerTests {

    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @Mock
    private UserCourseMappingService userCourseMappingService;

    @BeforeEach
    void setup() {
        courseController = new CourseController(courseService, userCourseMappingService);
    }

    @AfterEach
    void tearDown() {
        courseController = null;
    }

    @Test
    void testFindAll() {
        when(courseService.findAll()).thenReturn(testCourseDTOList());
        ResponseEntity<List<CourseDTO>> testCourseList = courseController.findAll();
        Assertions.assertTrue(testCourseList.hasBody());
        assertEquals(testCourseList.getStatusCode(), HttpStatus.OK);
        assertEquals(testCourseList.getBody().size(), 2);
    }

    @Test
    void testSaveOrUpdateCourse() {
        when(courseService.saveOrUpdate(Mockito.any())).thenReturn(testCourseDTO(2L));
        ResponseEntity<CourseDTO> testSavedCourse = courseController.saveOrUpdate(testCourseDTO(2L));
        Assertions.assertTrue(testSavedCourse.hasBody());
        assertEquals(testSavedCourse.getStatusCode(), HttpStatus.OK);
        assertEquals(testSavedCourse.getBody().getId(), 2L);
    }

    @Test
    void testFindById() {
        when(courseService.findById(Mockito.any())).thenReturn(testCourseDTO(1L));
        ResponseEntity<CourseDTO> testResponse = courseController.findById(1L);
        Assertions.assertTrue(testResponse.hasBody());
        assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(testResponse.getBody().getId(), 1L);
    }

    @Test
    void testFindById2() {
        when(courseService.findById(Mockito.any())).thenReturn(testCourseDTO(2L));
        ResponseEntity<CourseDTO> testResponse = courseController.findById(2L);
        Assertions.assertTrue(testResponse.hasBody());
        assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(testResponse.getBody().getId(), 2L);
    }

    @Test
    public void enrollUserInCourse_Success() {
        Long userId = 1L;
        Long courseId = 2L;
        String response = "Enrolled successfully";

        when(userCourseMappingService.enrollUserInCourse(userId, courseId)).thenReturn(response);

        ResponseEntity<String> result = courseController.enrollUserInCourse(userId, courseId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(userCourseMappingService, times(1)).enrollUserInCourse(userId, courseId);
    }

    @Test
    public void enrollUserInCourse_Success2() {
        Long userId = 2L;
        Long courseId = 3L;
        String response = "Enrolled successfully";

        when(userCourseMappingService.enrollUserInCourse(userId, courseId)).thenReturn(response);

        ResponseEntity<String> result = courseController.enrollUserInCourse(userId, courseId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(userCourseMappingService, times(1)).enrollUserInCourse(userId, courseId);
    }

    @Test
    public void enrollUserInCourse_Success3() {
        Long userId = 3L;
        Long courseId = 4L;
        String response = "Enrolled successfully";

        when(userCourseMappingService.enrollUserInCourse(userId, courseId)).thenReturn(response);

        ResponseEntity<String> result = courseController.enrollUserInCourse(userId, courseId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(userCourseMappingService, times(1)).enrollUserInCourse(userId, courseId);
    }

    @Test
    public void enrollUserInCourse_Success4() {
        Long userId = 4L;
        Long courseId = 5L;
        String response = "Enrolled successfully";

        when(userCourseMappingService.enrollUserInCourse(userId, courseId)).thenReturn(response);

        ResponseEntity<String> result = courseController.enrollUserInCourse(userId, courseId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(userCourseMappingService, times(1)).enrollUserInCourse(userId, courseId);
    }

    @Test
    void testInstructorCourse_ValidUserId_ReturnsListOfCourseDTO() {
        Long userId = 1L;
        List<CourseDTO> expectedCourses = new ArrayList<>(); // Define your expected courses here

        // Mocking the behavior of courseService.instructorCourse(userId)
        when(courseService.instructorCourse(userId)).thenReturn(expectedCourses);

        ResponseEntity<List<CourseDTO>> response = courseController.instructorCourse(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCourses, response.getBody());

        // Verify that the method in courseService is called once with the specified userId
        verify(courseService, times(1)).instructorCourse(userId);
    }

    @Test
    public void testStudentCourse_Success() {
        // Mocking the enrolledStudentCourses method of UserCourseMappingService
        when(userCourseMappingService.enrolledStudentCourses(anyLong())).thenReturn(testCourseDTOList());

        // Calling the method to be tested
        ResponseEntity<List<CourseDTO>> responseEntity = courseController.studentCourse(123L);

        // Verifying that the service method was called with the correct userId
        verify(userCourseMappingService, times(1)).enrolledStudentCourses(123L);

        // Verifying that the response entity has HTTP status OK
        assert responseEntity != null;
        assert responseEntity.getStatusCode() == HttpStatus.OK;
    }

    private List<CourseDTO> testCourseDTOList() {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList.add(new CourseDTO(1L, "TestCourse","test","test","test", 1L, UserType.INSTRUCTOR, "test",""));
        courseDTOList.add(new CourseDTO(2L, "TestCourse","test","test","test", 2L, UserType.INSTRUCTOR, "test",""));
        return courseDTOList;
    }

    private CourseDTO testCourseDTO(Long id) {
        return new CourseDTO(id, "TestCourse","test","test","test", 2L, UserType.INSTRUCTOR, "test","");
    }
}

