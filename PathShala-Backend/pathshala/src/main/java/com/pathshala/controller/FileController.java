package com.pathshala.controller;

import com.pathshala.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@Valid @RequestBody MultipartFile file, @RequestParam Long courseId){
        logger.info("Entered uploadFile file service");
        String filePath = fileService.uploadFile(file, courseId);
        logger.info("Exited uploadFile file service");
        return ResponseEntity.ok().body(filePath);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String path){
        logger.info("Entered downloadFile file service");
        Resource resource = fileService.downloadFile(path);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\".pdf";
        logger.info("Exited downloadFile file service");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
