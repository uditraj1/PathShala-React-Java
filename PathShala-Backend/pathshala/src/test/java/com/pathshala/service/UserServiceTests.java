package com.pathshala.service;

import com.pathshala.dao.UserEntity;
import com.pathshala.dto.LoginRequestDTO;
import com.pathshala.dto.UserDTO;
import com.pathshala.enums.UserType;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.exception.RecordExistsException;
import com.pathshala.repository.UserRepository;
import com.pathshala.security.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class UserServiceTests {

    private UserService userService;


    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @Mock
    private EmailService emailService;
    @Mock
    private SessionInfoService sessionInfoService;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private UserCourseMappingService userCourseMappingService;

    @Mock
    private CourseService courseService;

    @BeforeEach
    void setup(){
        userService = new UserService(userRepository, tokenService, sessionInfoService, modelMapper, userCourseMappingService, courseService, emailService);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @AfterEach
    void tearDown(){
        userService = null;
    }

    @Test
    public void saveUserData_UserAlreadyPresent_ThrowsRecordExistsException() {
        // Mocking scenario where user with the given ID is already present
        Mockito.when(userRepository.findByUserIdAndIsActiveTrue(any())).thenReturn(Optional.of(new UserEntity()));

        UserDTO userDTO = new UserDTO(); // Set userDTO with required data

        // Assertion
        assertThrows(RecordExistsException.class, () -> userService.saveUserData(userDTO));
    }

    @Test
    public void saveUserData_PasswordMismatch_andMissingEmailThrowsGenericExceptions() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("someUserId");
        userDTO.setPassword("password1");
        userDTO.setUserType(UserType.INSTRUCTOR);
        userDTO.setRePassword("password2");// Setting different passwords intentionally

        // Assertion
        assertThrows(GenericExceptions.class, () -> userService.saveUserData(userDTO));
    }

    @Test
    public void saveUserData_EmailIdIsNull_ThrowsGenericExceptions() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("someUserId");
        userDTO.setUserType(UserType.INSTRUCTOR);
        userDTO.setPassword("password");
        userDTO.setRePassword("password");
        userDTO.setEmailId(null); // Setting emailId as null intentionally

        // Assertion
        assertThrows(GenericExceptions.class, () -> userService.saveUserData(userDTO));
    }

    @Test
    public void saveUserData_ValidInput_ReturnsTrue() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId("someUserId");
        userDTO.setPassword("password");
        userDTO.setUserType(UserType.ADMIN);
        userDTO.setRePassword("password");
        userDTO.setEmailId("test@example.com");

        // Mocking repository save method
        Mockito.when(userRepository.findByUserIdAndIsActiveTrue(any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any())).thenReturn(new UserEntity());

        // Assertion
        assertTrue(userService.saveUserData(userDTO));
    }

    @Test
    public void testLogin_Success_for_Student() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("userId", "password", "test", "test", new UserDTO(), "");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserType(UserType.STUDENT);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserEntity(1L, "test", "test", "test", "test",UserType.STUDENT, "test", "test", true)));
        when(userRepository.findByUserIdAndPasswordAndIsActiveTrue(anyString(), anyString())).thenReturn(Optional.of(userEntity));
        when(tokenService.createToken(anyLong(), anyString())).thenReturn("token");
        when(sessionInfoService.createSession(anyLong(), anyString(), anyString())).thenReturn(true);

        LoginRequestDTO result = userService.login(loginRequestDTO);

        assertEquals("1", result.getUserId());
        assertEquals("STUDENT", result.getUserType());
        assertNotNull(result.getToken());
        assertNotNull(result.getUserDetails());
    }

    @Test
    public void testLogin_Success_for_Instructor() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("userId", "password", "test", "test", new UserDTO(), "");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserType(UserType.INSTRUCTOR);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserEntity(1L, "test", "test", "test", "test",UserType.INSTRUCTOR, "test", "test", true)));
        when(userRepository.findByUserIdAndPasswordAndIsActiveTrue(anyString(), anyString())).thenReturn(Optional.of(userEntity));
        when(tokenService.createToken(anyLong(), anyString())).thenReturn("token");
        when(sessionInfoService.createSession(anyLong(), anyString(), anyString())).thenReturn(true);

        LoginRequestDTO result = userService.login(loginRequestDTO);

        assertEquals("1", result.getUserId());
        assertEquals("INSTRUCTOR", result.getUserType());
        assertNotNull(result.getToken());
        assertNotNull(result.getUserDetails());
    }

    @Test
    public void testLogin_Success_for_ADMIN() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("userId", "password", "test", "test", new UserDTO(), "");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserType(UserType.ADMIN);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserEntity(1L, "test", "test", "test", "test",UserType.ADMIN, "test", "test", true)));
        when(userRepository.findByUserIdAndPasswordAndIsActiveTrue(anyString(), anyString())).thenReturn(Optional.of(userEntity));
        when(tokenService.createToken(anyLong(), anyString())).thenReturn("token");
        when(sessionInfoService.createSession(anyLong(), anyString(), anyString())).thenReturn(true);

        LoginRequestDTO result = userService.login(loginRequestDTO);

        assertEquals("1", result.getUserId());
        assertEquals("ADMIN", result.getUserType());
        assertNotNull(result.getToken());
        assertNotNull(result.getUserDetails());
    }

    @Test
    public void testLogout() {
        Long userId = 123L;

        // Set up the mock behavior for the expireSessionForUserId method
        when(sessionInfoService.expireSessionForUserId(userId)).thenReturn(true); // Set your expected return value here

        // Call the method
        Boolean result = userService.logout(userId);

        // Verify that the method was called with the correct arguments
        verify(sessionInfoService).expireSessionForUserId(userId);

        // Verify the result
        assertTrue(result); // Change this assertion based on your expected behavior
    }

    @Test
    void testGetInstructor(){
        Mockito.when(userRepository.findAllByUserTypeAndIsActiveTrue(any())).thenReturn(userEntityList());
        List<UserDTO> userDTOList = userService.getInstructor();
        Assertions.assertEquals(userDTOList.size(), 2);
    }

    @Test
    void testGetStudent(){
        Mockito.when(userRepository.findAllByUserTypeAndIsActiveTrue(any())).thenReturn(userEntityList());
        List<UserDTO> userDTOList = userService.getStudent();
        Assertions.assertEquals(userDTOList.size(), 2);
    }

//    @Test
//    void testUpdateUser(){
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(new UserEntity(1L, "test", "test", "test", "test",UserType.STUDENT, "test", "test", true)));
//        Mockito.when(userRepository.save(Mockito.any())).thenReturn(Optional.of(new UserEntity(1L, "test1", "test", "test", "test",UserType.STUDENT, "test", "test", true)));
//        UserDTO res = userService.updateUser(new UserDTO(1L, "test1", "test", "test", "test", UserType.STUDENT, "test", "test", "test", new ArrayList<>()));
//        Assertions.assertEquals(res.getId(), 1L);
//    }

    @Test
    public void testUpdateUser() {
        // Create a sample user DTO
        UserDTO sampleUserDTO = new UserDTO();
        sampleUserDTO.setId(1L);
        sampleUserDTO.setPassword("password");
        sampleUserDTO.setUserType(UserType.ADMIN);
        sampleUserDTO.setUserId("userId");

        // Create a sample user entity
        UserEntity sampleUserEntity = new UserEntity();
        sampleUserEntity.setId(1L);
        sampleUserEntity.setPassword("password");
        sampleUserEntity.setUserType(UserType.ADMIN);
        sampleUserEntity.setUserId("userId");

        // Mock behavior for the findEntityById method
//        when(userService.findEntityById(1L)).thenReturn(sampleUserEntity);
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sampleUserEntity));
        // Mock behavior for the save method of userRepository
        when(userRepository.save(any())).thenReturn(sampleUserEntity);

        // Mock behavior for modelMapper.map method
        when(modelMapper.map(sampleUserDTO, UserEntity.class)).thenReturn(sampleUserEntity);
        when(modelMapper.map(sampleUserEntity, UserDTO.class)).thenReturn(sampleUserDTO);

        // Call the method to be tested
        UserDTO updatedUserDTO = userService.updateUser(sampleUserDTO);

        // Verify if the userRepository's save method was called once
        verify(userRepository, times(1)).save(any());

        // Validate the expected result
        assertEquals("userId", updatedUserDTO.getUserId());
    }

    @Test
    void testDelete(){
        Mockito.when(userRepository.markUserInActive(Mockito.any())).thenReturn(1);
        Boolean res = userService.deleteUser(1L);
        Assertions.assertTrue(res);
    }

    @Test
    void testDelete2(){
        Mockito.when(userRepository.markUserInActive(Mockito.any())).thenReturn(2);
        Boolean res = userService.deleteUser(1L);
        Assertions.assertFalse(res);
    }

    @Test
    void testDelete3(){
        Mockito.when(userRepository.markUserInActive(Mockito.any())).thenReturn(3);
        Boolean res = userService.deleteUser(1L);
        Assertions.assertFalse(res);
    }


    private List<UserEntity> userEntityList(){
        return List.of(new UserEntity(1L, "test", "test", "test", "test",UserType.STUDENT, "test", "test", true),
                new UserEntity(2L, "test", "test", "test", "test",UserType.STUDENT, "test", "test", true));
    }

}
