package com.pathshala.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity(name = "studyMaterial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMaterialEntity extends MetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int courseId;
    private int topicId;
    private String description;
    private String filePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyMaterialEntity that = (StudyMaterialEntity) o;
        return id == that.id && courseId == that.courseId && topicId == that.topicId && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, courseId, topicId, description, filePath);
    }

    @Override
    public String toString() {
        return "StudyMaterialEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseId=" + courseId +
                ", topicId=" + topicId +
                ", description='" + description + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
