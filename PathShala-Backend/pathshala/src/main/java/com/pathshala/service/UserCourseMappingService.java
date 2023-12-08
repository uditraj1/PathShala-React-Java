package com.pathshala.service;

import com.pathshala.dao.UserCourseMappingEntity;
import com.pathshala.dto.CourseDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.UserCourseMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserCourseMappingService {
    private UserCourseMappingRepository userCourseMappingRepository;
    private CourseService courseService;

    public String enrollUserInCourse(Long userId, Long courseId){
        Optional<UserCourseMappingEntity> optionalUserCourseMapping =
                userCourseMappingRepository.findByUserIdAndCourseId(userId, courseId);
        if(optionalUserCourseMapping.isPresent()){
            throw new GenericExceptions(ErrorCodes.USER_COURSE_PRESENT, "User already enrolled in Course");
        }
        UserCourseMappingEntity mappingEntity = UserCourseMappingEntity.builder().courseId(courseId).userId(userId).build();
        userCourseMappingRepository.save(mappingEntity);
    return "Successfully enrolled";
    }

    public List<CourseDTO> enrolledStudentCourses(Long userId){
        List<UserCourseMappingEntity> enrolledCourses = userCourseMappingRepository.findAllByUserId(userId);
        List<Long> courseIds = enrolledCourses.stream().map(UserCourseMappingEntity::getCourseId).collect(Collectors.toList());
        return courseService.getCourses(courseIds);
    }

    public List<Long> getEnrolledStudentsByCourseId(Long courseId) {
        return userCourseMappingRepository.findAllByCourseId(courseId)
                .stream().map(UserCourseMappingEntity::getUserId)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getUnEnrolledCourses(Long userId) {
        List<CourseDTO> allCourses = courseService.getPublishedCourses();
        List<CourseDTO> unEnrolledCourses = new ArrayList<>();
        List<Long> enrolledCourses = enrolledStudentCourses(userId).stream().map(CourseDTO::getId).collect(Collectors.toList());
        for (CourseDTO course: allCourses) {
            if(!enrolledCourses.contains(course.getId())){
                unEnrolledCourses.add(course);
            }
        }
        return unEnrolledCourses;
    }


}
