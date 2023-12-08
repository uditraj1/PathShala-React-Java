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

@Entity(name = "submission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionEntity extends MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int userId;
    private int assignmentId;
    private String filePath;
    private float gradeReceived;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionEntity that = (SubmissionEntity) o;
        return id == that.id && userId == that.userId && assignmentId == that.assignmentId && Float.compare(that.gradeReceived, gradeReceived) == 0 && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, assignmentId, filePath, gradeReceived);
    }

    @Override
    public String toString() {
        return "SubmissionEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", assignmentId=" + assignmentId +
                ", filePath='" + filePath + '\'' +
                ", gradeReceived=" + gradeReceived +
                '}';
    }
}
