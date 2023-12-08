package com.pathshala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDTO {
    private String userId;
    private String password;
    private String token;
    private String userType;
    private UserDTO userDetails;
    private String ip;
}
