package com.pathshala.service;

import com.pathshala.dao.UserCourseMappingEntity;
import com.pathshala.dto.CourseDTO;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.UserCourseMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class UserCourseMappingServiceTests {

    private UserCourseMappingService userCourseMappingService;

    @Mock
    private UserCourseMappingRepository userCourseMappingRepository;
    @Mock
    private CourseService courseService;

    @BeforeEach
    void init(){
        userCourseMappingService = new UserCourseMappingService(userCourseMappingRepository, courseService);
    }

    @Test
    public void testEnrollUserInCourseWhenUserAlreadyEnrolled() {
        Long userId = 1L;
        Long courseId = 2L;

        when(userCourseMappingRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(Optional.of(new UserCourseMappingEntity()));

        assertThrows(GenericExceptions.class, () -> userCourseMappingService.enrollUserInCourse(userId, courseId));
    }

    @Test
    public void testEnrollUserInCourseWhenUserNotEnrolled() {
        Long userId = 1L;
        Long courseId = 2L;

        when(userCourseMappingRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(Optional.empty());

        assertEquals("Successfully enrolled", userCourseMappingService.enrollUserInCourse(userId, courseId));
    }

    @Test
    public void testEnrollUserInCourseWhenUserNotEnrolled2() {
        Long userId = 2L;
        Long courseId = 3L;

        when(userCourseMappingRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(Optional.empty());

        assertEquals("Successfully enrolled", userCourseMappingService.enrollUserInCourse(userId, courseId));
    }

    @Test
    public void testEnrollUserInCourseWhenUserNotEnrolled3() {
        Long userId = 3L;
        Long courseId = 4L;

        when(userCourseMappingRepository.findByUserIdAndCourseId(userId, courseId))
                .thenReturn(Optional.empty());

        assertEquals("Successfully enrolled", userCourseMappingService.enrollUserInCourse(userId, courseId));
    }

    @Test
    public void testEnrollUserInCourse_whenUserIsValidAndCourseIsValid_shouldReturnSuccess() {
        // Arrange
        UserCourseMappingRepository userCourseMappingRepository = Mockito.mock(UserCourseMappingRepository.class);
        CourseService courseService = Mockito.mock(CourseService.class);

        Long userId = 1L;
        Long courseId = 2L;

        // Act
        String result = userCourseMappingService.enrollUserInCourse(userId, courseId);

        // Assert
        assertEquals("Successfully enrolled", result);
    }

    @Test
    public void testEnrollUserInCourse_UserAlreadyEnrolled() {
        // Mocking the repository method to return a non-empty Optional (indicating user is already enrolled)
        when(userCourseMappingRepository.findByUserIdAndCourseId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new UserCourseMappingEntity()));

        // Asserting that the GenericExceptions is thrown with the expected error message
        GenericExceptions exception = assertThrows(
                GenericExceptions.class,
                () -> userCourseMappingService.enrollUserInCourse(123L, 456L)
        );

        assertEquals("User already enrolled in Course", exception.getMessage());
        // Verifying that the repository method was called once with the correct arguments
        verify(userCourseMappingRepository, times(1)).findByUserIdAndCourseId(123L, 456L);
    }

    @Test
    public void testEnrollUserInCourse_UserAlreadyEnrolled2() {
        // Mocking the repository method to return a non-empty Optional (indicating user is already enrolled)
        when(userCourseMappingRepository.findByUserIdAndCourseId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new UserCourseMappingEntity()));

        // Asserting that the GenericExceptions is thrown with the expected error message
        GenericExceptions exception = assertThrows(
                GenericExceptions.class,
                () -> userCourseMappingService.enrollUserInCourse(123L, 456L)
        );

        assertEquals("User already enrolled in Course", exception.getMessage());
        // Verifying that the repository method was called once with the correct arguments
        verify(userCourseMappingRepository, times(1)).findByUserIdAndCourseId(123L, 456L);
    }

    @Test
    void testEnrolledStudentCourses() {
        Long userId = 123L;
        List<UserCourseMappingEntity> mockEnrolledCourses = Arrays.asList(
                new UserCourseMappingEntity(), // Replace with appropriate constructor and values
                new UserCourseMappingEntity()  // Replace with appropriate constructor and values
                // Add more mocked UserCourseMappingEntity objects as needed
        );

        List<Long> courseIds = mockEnrolledCourses.stream()
                .map(UserCourseMappingEntity::getCourseId)
                .collect(Collectors.toList());

        List<CourseDTO> mockCourses = Arrays.asList(
                new CourseDTO(),
                new CourseDTO()
        );

        when(userCourseMappingRepository.findAllByUserId(userId)).thenReturn(mockEnrolledCourses);
        when(courseService.getCourses(courseIds)).thenReturn(mockCourses);

        List<CourseDTO> result = userCourseMappingService.enrolledStudentCourses(userId);

        assertEquals(mockCourses.size(), result.size()); // Check if the sizes match
    }
}