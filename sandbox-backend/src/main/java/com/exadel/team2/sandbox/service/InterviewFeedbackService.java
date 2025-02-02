package com.exadel.team2.sandbox.service;

import com.exadel.team2.sandbox.web.interview_feedback.CreateInterviewFeedbackDto;
import com.exadel.team2.sandbox.web.interview_feedback.ResponseInterviewFeedbackDto;
import com.exadel.team2.sandbox.web.interview_feedback.UpdateInterviewFeedbackDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InterviewFeedbackService {
    ResponseInterviewFeedbackDto getById(Long id);

    List<ResponseInterviewFeedbackDto> getAll();

    ResponseInterviewFeedbackDto save(CreateInterviewFeedbackDto createInterviewFeedbackDTO);

    ResponseInterviewFeedbackDto update(Long id, UpdateInterviewFeedbackDto updateInterviewFeedbackDto);

    void delete(Long id);

    Page<ResponseInterviewFeedbackDto> getAllPageable(Pageable pageable, String search);

}
