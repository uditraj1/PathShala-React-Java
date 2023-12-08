package com.pathshala.controller;

import com.pathshala.dto.LoginRequestDTO;
import com.pathshala.dto.UserDTO;
import com.pathshala.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "https://pathshala-api-8e4271465a87.herokuapp.com", maxAge = 360000)
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginRequestDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("Call login API");
        LoginRequestDTO loggedInUser = userService.login(loginRequestDTO);
        logger.info("Exit login API");
        return ResponseEntity.ok().body(loggedInUser);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> signUp(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Call signUp API");
        Boolean signUpBool = userService.saveUserData(userDTO);
        logger.info("Exit signUp API");
        return ResponseEntity.ok().body(signUpBool);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestParam Long userId) {
        logger.info("Call logout API");
        Boolean isLoggedOut = userService.logout(userId);
        logger.info("Exit logout API");
        return ResponseEntity.ok().body(isLoggedOut);
    }

    @GetMapping("/getInstructor")
    public ResponseEntity<List<UserDTO>> getInstructor(){
        logger.info("Call getInstructor API");
        List<UserDTO> instructors = userService.getInstructor();
        logger.info("Exit getInstructor API");
        return ResponseEntity.ok().body(instructors);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO payload){
        logger.info("Call updateUser API");
        UserDTO user = userService.updateUser(payload);
        logger.info("Exit updateUser API");
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/getStudent")
    public ResponseEntity<List<UserDTO>> getStudent(){
        logger.info("Call getStudent API");
        List<UserDTO> students = userService.getStudent();
        logger.info("Exit getStudent API");
        return ResponseEntity.ok().body(students);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        logger.info("Call deleteUser API");
        Boolean isUserDeleted = userService.deleteUser(id);
        logger.info("Exit deleteUser API");
        return ResponseEntity.ok().body(isUserDeleted);
    }

    @GetMapping("/getEnrolledStudents/{courseId}")
    public ResponseEntity<List<UserDTO>> getEnrolledStudents(@PathVariable @NotNull Long courseId){
        logger.info("Call getEnrolledStudents API");
        List<UserDTO> students = userService.getEnrolledStudents(courseId);
        logger.info("Exit getEnrolledStudents API");
        return ResponseEntity.ok().body(students);
    }
}

