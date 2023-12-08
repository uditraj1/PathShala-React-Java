package com.pathshala.service;

import com.pathshala.config.PropertyConfig;
import com.pathshala.exception.BaseRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Disabled
public class FileServiceTests {

    private FileService fileService;

    @Mock
    private PropertyConfig config;
    @Mock
    private CourseService courseService;
    @BeforeEach
    void init(){
        fileService = new FileService(config, courseService);
    }

    @Test
    void testUploadFile() throws IOException {
        String filePath = "src/main/resources/uploadedFiles/";
        when(config.getPropertyByName("filePath")).thenReturn(filePath);

        String originalFilename = "testFile.txt";
        byte[] content = "Hello, this is a test file content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", originalFilename, "text/plain", content);

        String result = fileService.uploadFile(mockMultipartFile, 0L);

        assertNotNull(result);
    }

    @Test
    void testUploadFileWithException() {
        when(config.getPropertyByName("filePath")).thenReturn(null);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "testFile.txt", "text/plain", "Some Content".getBytes());

        BaseRuntimeException exception = assertThrows(BaseRuntimeException.class, () -> {
            fileService .uploadFile(mockMultipartFile, 0L);
        });

        assertEquals("", exception.getErrorCode()); // Replace "" with the expected error code
    }

    @Test
    public void testDownloadFile_validPath_shouldReturnUrlResource() {
        String path = "/path/to/file.txt";
        Path filePath = Paths.get(path);

        Resource resource = fileService.downloadFile(path);

        Assertions.assertNotEquals(resource, null);
    }
}