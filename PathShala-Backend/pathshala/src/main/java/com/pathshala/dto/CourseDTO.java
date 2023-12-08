package com.pathshala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pathshala.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String courseCode;
    private String description;
    private String syllabus;
    @JsonProperty(value = "instructorId")
    private Long userId;
    private UserType userType;
    private String instructorName;
    private String filePath;
}
