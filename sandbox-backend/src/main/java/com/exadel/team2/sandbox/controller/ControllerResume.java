package com.exadel.team2.sandbox.controller;

import com.exadel.team2.sandbox.dto.ResumeCreateDTO;
import com.exadel.team2.sandbox.dto.ResumeResponseDTO;
import com.exadel.team2.sandbox.dto.ResumeUpdateDTO;
import com.exadel.team2.sandbox.service.impl.ResumeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ControllerResume {

    private final ResumeServiceImpl resumeService;

    @GetMapping(value = "/{id}")
    public ResumeResponseDTO getResume(@PathVariable Long id) {
        return resumeService.getById(id);
    }

    @GetMapping
    public List<ResumeResponseDTO> getAll() {
        return resumeService.getAll();
    }

    @PostMapping
    public ResumeCreateDTO addResume(@RequestBody ResumeCreateDTO resumeCreateDTO) {
        return resumeService.save(resumeCreateDTO);
    }

    @PutMapping
    public ResumeUpdateDTO updateResume(@RequestBody ResumeUpdateDTO resumeUpdateDTO) {
        return resumeService.update(resumeUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteResume(@PathVariable Long id) {
        resumeService.delete(id);
    }
}
