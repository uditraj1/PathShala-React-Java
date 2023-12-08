package com.pathshala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pathshala.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentDTO {
    private Long id;
    private String name;
    private String description;
    private Timestamp deadline;
    private Float points;
    private String filePath;
    private Long topicId;
    private Long loggedInUserId;
    private UserType userType;
}
