package com.pathshala.service;

import com.pathshala.dao.AssignmentEntity;
import com.pathshala.dto.AssignmentDTO;
import com.pathshala.exception.ErrorCodes;
import com.pathshala.exception.NotFoundException;
import com.pathshala.repository.AssignmentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;

    public List<AssignmentDTO> findAll() {
        List<AssignmentEntity> assignmentEntities = assignmentRepository.findAll();
        return assignmentEntities.stream().map(assignment -> modelMapper.map(assignment, AssignmentDTO.class))
                .collect(Collectors.toList());
    }

    public AssignmentDTO saveOrUpdate(AssignmentDTO assignmentDTO) {
        AssignmentEntity assignment = new AssignmentEntity();
        if (assignmentDTO.getId() != null){
            assignment = findEntityById(assignmentDTO.getId());
        }
        modelMapper.map(assignmentDTO, assignment);
        AssignmentEntity savedCourse = assignmentRepository.save(assignment);
        return modelMapper.map(savedCourse, AssignmentDTO.class);
    }

    private AssignmentEntity findEntityById(Long id) {
        Optional<AssignmentEntity> course = assignmentRepository.findById(id);
        if (course.isEmpty()){
            throw new NotFoundException(ErrorCodes.ASSIGNMENT_NOT_FOUND, "Assignment not found!");
        }
        return course.get();
    }

    public AssignmentDTO findById(Long id) {
        AssignmentEntity course = this.findEntityById(id);
        return modelMapper.map(course, AssignmentDTO.class);
    }

    public AssignmentDTO findByTopicId(Long topicId) {
        Optional<AssignmentEntity> course = assignmentRepository.findByTopicId(topicId);
        if (course.isEmpty()){
            throw new NotFoundException(ErrorCodes.ASSIGNMENT_NOT_FOUND, "Assignment not found!");
        }
        return modelMapper.map(course, AssignmentDTO.class);
    }
}
