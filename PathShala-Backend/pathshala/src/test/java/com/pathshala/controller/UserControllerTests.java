package com.pathshala.controller;

import com.pathshala.dto.CourseDTO;
import com.pathshala.dto.LoginRequestDTO;
import com.pathshala.dto.UserDTO;
import com.pathshala.enums.UserType;
import com.pathshala.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class UserControllerTests {

    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup(){
        userController = new UserController(userService);
    }

    @AfterEach
    void tearDown(){
        userController = null;
    }

    @Test
//    @Disabled
    void testLogin() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        when(userService.login(Mockito.any())).thenReturn(loginRequestDTO());
        ResponseEntity<LoginRequestDTO> testLoginResponse = userController.login(loginRequestDTO());
        assertTrue(testLoginResponse.hasBody());
        assertEquals(testLoginResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(testLoginResponse.getBody()).getUserId(), "TestUser");
    }

    @Test
    void testSignUp(){
        when(userService.saveUserData(Mockito.any())).thenReturn(true);
        ResponseEntity<Boolean> testSignUpResponse = userController.signUp(signUpUserDTO());
        assertTrue(testSignUpResponse.hasBody());
        assertEquals(testSignUpResponse.getStatusCode(), HttpStatus.OK);
        assertEquals(testSignUpResponse.getBody(), true);
    }

    @Test
    void testLogOut(){
        when(userService.logout(Mockito.any())).thenReturn(true);
        ResponseEntity testLogoutResponse = userController.logout(1L);
        assertEquals(testLogoutResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetInstructor() {
        // Prepare test data
        List<UserDTO> mockInstructors = new ArrayList<>();
        // Add some mock UserDTO objects to mockInstructors...

        // Mock the behavior of userService.getInstructor()
        when(userService.getInstructor()).thenReturn(mockInstructors);

        // Call the method to be tested
        ResponseEntity<List<UserDTO>> response = userController.getInstructor();

        // Verify that the method returned ResponseEntity with OK status
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockInstructors, response.getBody());

        // Verify that userService.getInstructor() was called exactly once
        verify(userService, times(1)).getInstructor();
    }

    @Test
    public void testUpdateUser_Success() {
        // Mocking the userService's updateUser method
        UserDTO inputPayload = new UserDTO(/* Provide test data */);
        UserDTO expectedUser = new UserDTO(/* Provide expected user data */);
        when(userService.updateUser(inputPayload)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(inputPayload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    public void testGetStudent_Success() {
        // Given
        List<UserDTO> mockStudents = List.of(
                signUpUserDTO()
        );
        when(userService.getStudent()).thenReturn(mockStudents);

        // When
        ResponseEntity<List<UserDTO>> response = userController.getStudent();

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockStudents, response.getBody());
        verify(userService, times(1)).getStudent();
    }

    @Test
    public void testDeleteUser_Success() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.deleteUser(userId);

        Assertions.assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Boolean.TRUE, response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testDeleteUser_Success2() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.deleteUser(userId);

        Assertions.assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(Boolean.TRUE, response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    private LoginRequestDTO loginRequestDTO(){
        return new LoginRequestDTO("TestUser", "testPass","testToken", UserType.ADMIN.toString(), signUpUserDTO(),"");

    }

    private UserDTO signUpUserDTO(){
        return new UserDTO(1L, "fname", "lname", "test@test.com", "99887766", UserType.STUDENT, "testId", "pass","pass", courseDTOList());
    }

    private List<CourseDTO> courseDTOList(){
        return List.of(new CourseDTO(1L, "test", "code101", "test", "test", 1L, UserType.INSTRUCTOR, "test",""));
    }
}
