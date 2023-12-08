package com.pathshala.service;

import com.pathshala.dao.SubmissionEntity;
import com.pathshala.dto.SubmissionDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.GenericExceptions;
import com.pathshala.repository.SubmissionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SubmissionService {
    private SubmissionRepository submissionRepository;
    private ModelMapper modelMapper;

    public SubmissionDTO saveOrUpdateSubmission(SubmissionDTO submissionDTO){
        SubmissionEntity submission = new SubmissionEntity();
        modelMapper.map(submissionDTO, submission);
        SubmissionEntity savedSubmission = submissionRepository.save(submission);
        return modelMapper.map(savedSubmission, SubmissionDTO.class);
    }

    public SubmissionDTO updateGrade(SubmissionDTO submissionDTO) {
        if(Float.isNaN(submissionDTO.getGradeReceived())){
            throw new GenericExceptions(ErrorCodes.MISSING_GRADE, "Missing grade!");
        }
        Optional<SubmissionEntity> savedSubmission = submissionRepository.findById(submissionDTO.getId());
        if(!savedSubmission.isPresent()){
            throw new GenericExceptions(ErrorCodes.COURSE_NOT_FOUND, "Submission not found");
        }
        savedSubmission.get().setGradeReceived(submissionDTO.getGradeReceived());
        return modelMapper.map(submissionRepository.save(savedSubmission.get()), SubmissionDTO.class);
    }
}
