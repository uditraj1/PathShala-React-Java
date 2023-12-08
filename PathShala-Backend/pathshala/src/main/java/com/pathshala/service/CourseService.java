package com.pathshala.service;

import com.pathshala.dao.CourseEntity;
import com.pathshala.dao.UserEntity;
import com.pathshala.dto.CourseDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.NotFoundException;
import com.pathshala.repository.CourseRepository;
import com.pathshala.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository courseRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    public List<CourseDTO> findAll(){
        List<CourseEntity> courseEntities = courseRepository.findAllByIsActiveTrue();
        List<CourseDTO> courseDTOS = courseEntities.stream().map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
        for(CourseDTO course: courseDTOS){
            Optional<UserEntity> userEntity = userRepository.findById(course.getUserId());
            if(userEntity.isPresent()){
                String userName = userEntity.get().getFirstName() +" "+ userEntity.get().getLastName();
                course.setInstructorName(userName);
            }
        }
        return courseDTOS;
    }


    public CourseDTO saveOrUpdate(CourseDTO courseDTO) {
        CourseEntity course = new CourseEntity();
        if (courseDTO.getId() != null){
            course = findEntityById(courseDTO.getId());
            course.setIsCoursePublished(true);
        } else{
            course.setIsCoursePublished(false);
        }
        modelMapper.map(courseDTO, course);
        course.setIsActive(true);
        CourseEntity savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDTO.class);
    }

    public CourseDTO findById(Long id) {
        CourseEntity course = this.findEntityById(id);
        return modelMapper.map(course, CourseDTO.class);
    }


    private CourseEntity findEntityById(Long id) {
        Optional<CourseEntity> course = courseRepository.findByIdAndIsActiveTrue(id);
        if (course.isEmpty()){
            throw new NotFoundException(ErrorCodes.COURSE_NOT_FOUND, "Course not found!");
        }
        return course.get();
    }

    public List<CourseDTO> getCourses(List<Long> courseIds) {
        List<CourseEntity> courses = courseRepository.findAllById(courseIds);
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    public List<CourseDTO> instructorCourse(Long userId) {
        List<CourseEntity> courses = courseRepository.findByUserIdAndIsActiveTrue(userId);
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    public List<CourseDTO> getPublishedCourses() {
        List<CourseEntity> publishedCourses = courseRepository.findByIsCoursePublishedTrue();
        return publishedCourses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public Boolean deleteCourse(Long courseId) {
        int noOfRecords = courseRepository.markCourseInActive(courseId);
        return noOfRecords == 1;
    }

    public void updateFilePath(Long courseId, String filePath) {
        CourseEntity course = findEntityById(courseId);
        course.setFilePath(filePath);
        courseRepository.save(course);
    }
}
