package com.exadel.team2.sandbox.service.impl;


import com.exadel.team2.sandbox.dao.CandidateDAO;
import com.exadel.team2.sandbox.dao.EmployeeDAO;
import com.exadel.team2.sandbox.dao.InterviewFeedbackDAO;
import com.exadel.team2.sandbox.dao.rsql.CustomRsqlVisitor;
import com.exadel.team2.sandbox.entity.CandidateEntity;
import com.exadel.team2.sandbox.entity.EmployeeEntity;
import com.exadel.team2.sandbox.entity.InterviewFeedbackEntity;
import com.exadel.team2.sandbox.exceptions.NoSuchException;
import com.exadel.team2.sandbox.mapper.InterviewFeedbackMapper;
import com.exadel.team2.sandbox.service.InterviewFeedbackService;
import com.exadel.team2.sandbox.web.interview_feedback.CreateInterviewFeedbackDto;
import com.exadel.team2.sandbox.web.interview_feedback.ResponseInterviewFeedbackDto;
import com.exadel.team2.sandbox.web.interview_feedback.UpdateInterviewFeedbackDto;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class InterviewFeedbackServiceImpl implements InterviewFeedbackService {

    private final InterviewFeedbackDAO interviewFeedbackDAO;
    private final InterviewFeedbackMapper interviewFeedbackMapper;
    private final EmployeeDAO employeeDAO;
    private final CandidateDAO candidateDAO;

    @Override
    public ResponseInterviewFeedbackDto getById(Long id) {
        InterviewFeedbackEntity interviewFeedbackEntity = interviewFeedbackDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback Not Found"));
        return interviewFeedbackMapper.convertEntityToDto(interviewFeedbackEntity);
    }

    @Override
    public List<ResponseInterviewFeedbackDto> getAll() {
        List<InterviewFeedbackEntity> interviewFeedbackEntities = interviewFeedbackDAO.findAll();
        if (interviewFeedbackEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Content");
        }
        return interviewFeedbackEntities.stream()
                .map(interviewFeedbackMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseInterviewFeedbackDto save(CreateInterviewFeedbackDto createInterviewFeedbackDTO) {
        InterviewFeedbackEntity interviewFeedbackEntity = interviewFeedbackMapper.convertDtoToEntity(createInterviewFeedbackDTO);
        EmployeeEntity employeeEntity = employeeDAO.findById(createInterviewFeedbackDTO.getIdEmployee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        interviewFeedbackEntity.setEmployee(employeeEntity);
        CandidateEntity candidateEntity = candidateDAO.findById(createInterviewFeedbackDTO.getIdCandidate())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        interviewFeedbackEntity.setCandidate(candidateEntity);
        interviewFeedbackEntity.setFeedback(createInterviewFeedbackDTO.getFeedback());
        interviewFeedbackDAO.save(interviewFeedbackEntity);
        return interviewFeedbackMapper.convertEntityToDto(interviewFeedbackEntity);
    }

    @Override
    public ResponseInterviewFeedbackDto update(Long id, UpdateInterviewFeedbackDto updateInterviewFeedbackDto) {
        InterviewFeedbackEntity interviewFeedbackEntity = interviewFeedbackDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "InterviewFeedback not found"));
        EmployeeEntity employeeEntity = employeeDAO.findById(updateInterviewFeedbackDto.getIdEmployee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        interviewFeedbackEntity.setEmployee(employeeEntity);
        CandidateEntity candidateEntity = candidateDAO.findById(updateInterviewFeedbackDto.getIdCandidate())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        interviewFeedbackEntity.setCandidate(candidateEntity);
        interviewFeedbackEntity.setFeedback(updateInterviewFeedbackDto.getFeedback());
        return interviewFeedbackMapper.convertEntityToDto(interviewFeedbackDAO.save(interviewFeedbackEntity));
    }

    @Override
    public void delete(Long id) {
        if (!interviewFeedbackDAO.existsById(id)) {
            throw new NoSuchException("interviewFeedback with ID = " + id + " not found in Database. " +
                    "Unable to delete an feedback that does not exist.");
        }
        interviewFeedbackDAO.deleteById(id);
    }

    @Override
    public Page<ResponseInterviewFeedbackDto> getAllPageable(Pageable pageable, String search) {
        if (search.isEmpty()) {
            return interviewFeedbackDAO.findAll(pageable).map(interviewFeedbackMapper::convertEntityToDto);
        } else {
            Node rootNode = new RSQLParser().parse(search);
            Specification<InterviewFeedbackEntity> spec = rootNode.accept(new CustomRsqlVisitor<>());
            return interviewFeedbackDAO.findAll(spec, pageable)
                    .map(interviewFeedbackMapper::convertEntityToDto);
        }
    }
}
