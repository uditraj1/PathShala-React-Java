package com.pathshala.controller;

import com.pathshala.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
public class FileControllerTests {

    private FileController fileController;

    @Mock
    private FileService fileService;

    @BeforeEach
    void init(){
        fileController = new FileController(fileService);
    }

    @AfterEach
    void tearDown(){
        fileController = null;
    }

    @Test
    public void testUploadFile() {
        // Mock file and its service
        String testFilePath = "/test/file/path.ext";
        MultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        when(fileService.uploadFile(mockFile, 0L)).thenReturn(testFilePath);

        // Call the method to be tested
        ResponseEntity<String> response = fileController.uploadFile(mockFile,0L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testFilePath, response.getBody());
    }

    @Test
    public void testUploadFile2() {
        // Mock file and its service
        String testFilePath = "/test1/file/path.ext";
        MultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        when(fileService.uploadFile(mockFile, 0L)).thenReturn(testFilePath);

        // Call the method to be tested
        ResponseEntity<String> response = fileController.uploadFile(mockFile,0L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testFilePath, response.getBody());
    }

    @Test
    public void testUploadFile3() {
        // Mock file and its service
        String testFilePath = "/test1/file/path.ext";
        MultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        when(fileService.uploadFile(mockFile, 0L)).thenReturn(testFilePath);

        // Call the method to be tested
        ResponseEntity<String> response = fileController.uploadFile(mockFile, 0L);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testFilePath, response.getBody());
    }

    @Test
    public void testDownloadFile() {
        // Mocking the Resource
        Resource mockResource = mock(Resource.class);
        when(mockResource.getFilename()).thenReturn("exampleFile.txt");

        // Mocking the fileService behavior
        when(fileService.downloadFile(anyString())).thenReturn(mockResource);

        // Call the method to test
        ResponseEntity<?> response = fileController.downloadFile("your-file-path");

        // Assert the ResponseEntity
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/octet-stream", response.getHeaders().getContentType().toString());
        assertEquals("attachment; filename=\"exampleFile.txt\"", response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0));
        assertEquals(mockResource, response.getBody());
    }

}
