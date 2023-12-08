package com.pathshala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pathshala.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private UserType userType;
    private String userId;
    private String password;
    private String rePassword;
    private List<CourseDTO> courses;
}
