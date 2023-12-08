package com.pathshala.service;

import com.pathshala.config.PropertyConfig;
import com.pathshala.exception.BaseRuntimeException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class FileService {

    private PropertyConfig config;
    private CourseService courseService;
    public String uploadFile(MultipartFile file, Long courseId) {
        String uploadPath = config.getPropertyByName("filePath");
        try{
            Path uploadFile = Paths.get(uploadPath);
            if(!Files.exists(uploadFile)){
                Files.createDirectory(uploadFile);
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            courseService.updateFilePath(courseId, filePath.toString());
            return filePath.toString();
        } catch(Exception e) {
            throw new BaseRuntimeException("","");
        }
    }

    public Resource downloadFile(String path) {
        try{
            Path filePath = Paths.get(path);
            return new UrlResource(filePath.toUri());
        } catch (Exception e){
            throw new BaseRuntimeException("","");
        }
    }
}
