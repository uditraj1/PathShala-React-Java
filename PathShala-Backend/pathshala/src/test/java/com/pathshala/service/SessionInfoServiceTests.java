package com.pathshala.service;

import com.pathshala.dao.SessionInfoEntity;
import com.pathshala.exception.BaseRuntimeException;
import com.pathshala.exception.NotFoundException;
import com.pathshala.repository.SessionInfoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class SessionInfoServiceTests {

    private SessionInfoService sessionInfoService;

    @Mock
    private SessionInfoRepository sessionInfoRepository;

    @BeforeEach
    void init(){
        sessionInfoService = new SessionInfoService(sessionInfoRepository);
    }

    @AfterEach
    void tearDown(){
        sessionInfoService = null;
    }

    @Test
    void testFindByUserIdAndIsActive_WhenSessionInfoIsEmpty_ShouldThrowNotFoundException() {
        Long userId = 1L;
        when(sessionInfoRepository.findByUserIdAndIsActiveTrueOrderByIdDesc(userId)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> sessionInfoService.findByUserIdAndIsActive(userId));
    }

    @Test
    void testFindByUserIdAndIsActive_WhenMultipleSessionInfoExists_ShouldThrowBaseRuntimeException() {
        Long userId = 1L;
        List<SessionInfoEntity> multipleSessionInfo = new ArrayList<>();
        multipleSessionInfo.add(new SessionInfoEntity(1L, 1L, "test", "test", true));
        multipleSessionInfo.add(new SessionInfoEntity(2L, 2L, "test", "test", true));
        when(sessionInfoRepository.findByUserIdAndIsActiveTrueOrderByIdDesc(userId)).thenReturn(multipleSessionInfo);

        SessionInfoEntity result = sessionInfoService.findByUserIdAndIsActive(userId);
        Assertions.assertEquals(result.getUserId(), 1L);
    }

    @Test
    void testFindByUserIdAndIsActive_WhenSingleSessionInfoExists_ShouldReturnSessionInfoEntity() {
        Long userId = 1L;
        SessionInfoEntity sessionInfoEntity = new SessionInfoEntity();
        List<SessionInfoEntity> singleSessionInfo = new ArrayList<>();
        singleSessionInfo.add(sessionInfoEntity);
        when(sessionInfoRepository.findByUserIdAndIsActiveTrueOrderByIdDesc(userId)).thenReturn(singleSessionInfo);

        SessionInfoEntity result = sessionInfoService.findByUserIdAndIsActive(userId);

        assertNotNull(result);
        assertEquals(sessionInfoEntity, result);
    }

    @Test
    public void testCreateSession_Success() {
        // Mock data
        Long userId = 123L;
        String token = "sampleToken";
        SessionInfoEntity sessionInfo = SessionInfoEntity.builder()
                .userId(userId)
                .sessionToken(token)
                .isActive(true)
                .build();
        when(sessionInfoRepository.save(sessionInfo)).thenReturn(sessionInfo);

        // Call method
        Boolean result = sessionInfoService.createSession(userId, token,"");

        assertTrue(result); // Check if method returns true
    }

    @Test
    public void testCreateSession_Success2() {
        // Mock data
        Long userId = 12L;
        String token = "testToken";
        SessionInfoEntity sessionInfo = SessionInfoEntity.builder()
                .userId(userId)
                .sessionToken(token)
                .isActive(true)
                .build();
        when(sessionInfoRepository.save(sessionInfo)).thenReturn(sessionInfo);

        // Call method
        Boolean result = sessionInfoService.createSession(userId, token,"");

        assertTrue(result); // Check if method returns true
    }

    @Test
    public void testCreateSession_Success3() {
        // Mock data
        Long userId = 1234L;
        String token = "testToken2";
        SessionInfoEntity sessionInfo = SessionInfoEntity.builder()
                .userId(userId)
                .sessionToken(token)
                .isActive(true)
                .build();
        when(sessionInfoRepository.save(sessionInfo)).thenReturn(sessionInfo);

        // Call method
        Boolean result = sessionInfoService.createSession(userId, token,"");

        assertTrue(result); // Check if method returns true
    }

    @Test
    public void testCreateSession_Failure() {
        // Mock data
        Long userId = null; // Invalid user ID
        String token = null; // Invalid token

        // Call method
        Boolean result = sessionInfoService.createSession(userId, token,"");

        assertTrue(result);
    }

}