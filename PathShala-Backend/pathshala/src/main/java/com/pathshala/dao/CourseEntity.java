package com.pathshala.dao;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity extends MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String courseCode;
    private String description;
    private String syllabus;
    private Long userId;
    private Boolean isCoursePublished;
    private String filePath;
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity course = (CourseEntity) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(courseCode, course.courseCode) && Objects.equals(description, course.description) && Objects.equals(syllabus, course.syllabus) && Objects.equals(userId, course.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, courseCode, description, syllabus, userId);
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", description='" + description + '\'' +
                ", syllabus='" + syllabus + '\'' +
                ", userId=" + userId +
                '}';
    }

}
