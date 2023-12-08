package com.pathshala.service;

import com.pathshala.dao.CourseEntity;
import com.pathshala.dto.CourseDTO;
import com.pathshala.enums.UserType;
import com.pathshala.repository.CourseRepository;
import com.pathshala.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class CourseServiceTests {

    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup(){
        courseService = new CourseService(courseRepository, modelMapper, userRepository);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void testFindAll(){
        when(courseRepository.findAllByIsActiveTrue()).thenReturn(courseEntityList());
        List<CourseDTO> courseDTOList = courseService.findAll();
        assertEquals(courseDTOList.size(), 2);
        assertEquals(courseDTOList.get(0).getId(), 1L);
    }

    @Test
    void testFindAll2(){
        when(courseRepository.findAllByIsActiveTrue()).thenReturn(courseEntityList());
        List<CourseDTO> courseDTOList = courseService.findAll();
        assertEquals(courseDTOList.size(), 2);
        assertEquals(courseDTOList.get(1).getId(), 2L);
    }

    @Test
    void testSaveOrUpdate(){
        when(courseRepository.findByIdAndIsActiveTrue(Mockito.any())).thenReturn(Optional.of(courseEntity(1L)));
        when(courseRepository.save(Mockito.any())).thenReturn(courseEntity(1L));
        CourseDTO savedCourse = courseService.saveOrUpdate(testCourseDTO());
        assertEquals(savedCourse.getId(), 1L);
    }

    @Test
    void testFindById(){
        when(courseRepository.findByIdAndIsActiveTrue(Mockito.any())).thenReturn(Optional.of(courseEntity(1L)));
        CourseDTO course = courseService.findById(1L);
        assertEquals(course.getId(), 1L);
    }

    @Test
    public void testGetCourses_WithValidCourseIds_ReturnsListOfCourseDTOs() {
        // Given
        List<Long> courseIds = new ArrayList<>();
        courseIds.add(1L);
        courseIds.add(2L);
        // Mocking CourseEntities returned by repository
        List<CourseEntity> mockedCourses = new ArrayList<>();
        mockedCourses.add(courseEntity(1L));
        mockedCourses.add(courseEntity(2L));
        when(courseRepository.findAllById(courseIds)).thenReturn(mockedCourses);

        // When
        List<CourseDTO> result = courseService.getCourses(courseIds);

        // Then
        assertEquals(mockedCourses.size(), result.size());
        assertEquals(mockedCourses.get(0).getName(), result.get(0).getName());
        assertEquals(mockedCourses.get(1).getName(), result.get(1).getName());
    }

    @Test
    void testInstructorCourse(){
        Mockito.when(courseRepository.findByUserIdAndIsActiveTrue(Mockito.any())).thenReturn(List.of(courseEntity(1L)));
        List<CourseDTO> courseDTOList = courseService.instructorCourse(1L);
        Assertions.assertEquals(courseDTOList.size(), 1);

    }

    private List<CourseEntity> courseEntityList(){
        List<CourseEntity> courseEntities = new ArrayList<>();
        courseEntities.add(new CourseEntity(1L, "test", "test101", "desc", "testSyllabus", 1L,true,"",true));
        courseEntities.add(new CourseEntity(2L, "test2", "test102", "desc2", "testSyllabus2", 2L,true,"",true));
        return courseEntities;
    }

    private CourseEntity courseEntity(Long id){
        return new CourseEntity(id, "test", "test101", "desc", "testSyllabus", 1L,true,"",true);
    }

    private CourseDTO testCourseDTO() {
        return new CourseDTO(2L, "TestCourse","test","test","test", 2L, UserType.INSTRUCTOR, "test","");
    }


}
