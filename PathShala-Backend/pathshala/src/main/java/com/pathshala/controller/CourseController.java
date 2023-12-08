package com.pathshala.controller;

import com.pathshala.dto.CourseDTO;
import com.pathshala.service.CourseService;
import com.pathshala.service.UserCourseMappingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private CourseService courseService;
    private UserCourseMappingService userCourseMappingService;
    @GetMapping
    public ResponseEntity<List<CourseDTO>> findAll(){
        logger.info("Call findAllCourses API");
        List<CourseDTO> courseDTOList = courseService.findAll();
        logger.info("Exit findAllCourses API");
        return ResponseEntity.ok().body(courseDTOList);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> saveOrUpdate(@RequestBody @Valid CourseDTO course) {
        logger.info("Entered saveOrUpdate course service");
        CourseDTO savedCourse = courseService.saveOrUpdate(course);
        logger.info("Exited saveOrUpdate course service");
        return ResponseEntity.ok().body(savedCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable @NotNull Long id){
        logger.info("Entered findById course service");
        CourseDTO courseDTO = courseService.findById(id);
        logger.info("Exited findById course service");
        return ResponseEntity.ok().body(courseDTO);
    }

    @GetMapping("/enroll")
    public ResponseEntity<String> enrollUserInCourse(@RequestParam @NotNull Long userId, @RequestParam @NotNull Long courseId){
        logger.info("Entered enrollUserInCourse course service");
        String response = userCourseMappingService.enrollUserInCourse(userId, courseId);
        logger.info("Exited enrollUserInCourse course service");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<CourseDTO>> instructorCourse(@RequestParam @NotNull Long userId) {
        logger.info("Entered instructorCourse course service");
        List<CourseDTO> courseDTOS = courseService.instructorCourse(userId);
        logger.info("Exited instructorCourse course service");
        return ResponseEntity.ok().body(courseDTOS);
    }
    @GetMapping("/student")
    public ResponseEntity<List<CourseDTO>> studentCourse(@RequestParam @NotNull Long userId) {
        logger.info("Entered studentCourse course service");
        List<CourseDTO> courseDTOS = userCourseMappingService.enrolledStudentCourses(userId);
        logger.info("Exited studentCourse course service");
        return ResponseEntity.ok().body(courseDTOS);
    }

    @GetMapping("/getUnEnrolledCourses")
    public ResponseEntity<List<CourseDTO>> getUnEnrolledCourses(@RequestParam @NotNull Long userId) {
        logger.info("Entered getUnEnrolledCourses service");
        List<CourseDTO> courseDTOS = userCourseMappingService.getUnEnrolledCourses(userId);
        logger.info("Exited getUnEnrolledCourses service");
        return ResponseEntity.ok().body(courseDTOS);
    }


    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id){
        logger.info("Entered deleteCourse service");
        Boolean isCourseDeleted = courseService.deleteCourse(id);
        logger.info("Exited deleteCourse service");
        return ResponseEntity.ok().body(isCourseDeleted);
    }
}
