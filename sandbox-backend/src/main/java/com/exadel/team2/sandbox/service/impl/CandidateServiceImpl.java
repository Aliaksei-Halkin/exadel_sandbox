package com.exadel.team2.sandbox.service.impl;

import com.exadel.team2.sandbox.dao.CandidateDAO;
import com.exadel.team2.sandbox.dto.CandidateCreateDTO;
import com.exadel.team2.sandbox.dto.CandidateResponseDTO;
import com.exadel.team2.sandbox.dto.CandidateUpdateDTO;
import com.exadel.team2.sandbox.entity.CandidateEntity;
import com.exadel.team2.sandbox.mapper.ModelMap;
import com.exadel.team2.sandbox.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateDAO candidateDAO;
    private final ModelMap modelMap;

    @Override
    public CandidateResponseDTO findById(Long id) {
        return modelMap.convertTo(candidateDAO.findById(id).orElse(null),
                CandidateResponseDTO.class);
    }

    @Override
    public List<CandidateResponseDTO> getAll() {
        return candidateDAO.findAll().stream()
                .map((CandidateEntity candidateEntity) ->
                        (CandidateResponseDTO) modelMap.convertTo(
                                candidateEntity, CandidateResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateCreateDTO save(CandidateCreateDTO candidateCreateDTO) {
        return modelMap.convertTo(candidateDAO.save(modelMap.convertTo(candidateCreateDTO, CandidateEntity.class)),
                CandidateCreateDTO.class);
    }

    @Override
    public CandidateUpdateDTO update(Long id, CandidateUpdateDTO candidateUpdateDTO) {
        CandidateEntity candidateEntity = candidateDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        if (candidateUpdateDTO.getRsmId() != null) {
            candidateEntity.setRsmId(candidateUpdateDTO.getRsmId());
        }

        if (candidateUpdateDTO.getFirstName() != null) {
            candidateEntity.setFirstName(candidateUpdateDTO.getFirstName());
        }

        if (candidateUpdateDTO.getLastName() != null) {
            candidateEntity.setLastName(candidateUpdateDTO.getLastName());
        }

        if (candidateUpdateDTO.getPhone() != null) {
            candidateEntity.setPhone(candidateUpdateDTO.getPhone());
        }

        if (candidateUpdateDTO.getSkype() != null) {
            candidateEntity.setSkype(candidateUpdateDTO.getSkype());
        }

        if (candidateUpdateDTO.getEnglishLevel() != null) {
            candidateEntity.setEnglishLevel(candidateUpdateDTO.getEnglishLevel());
        }

        if (candidateUpdateDTO.getExpertise() != null) {
            candidateUpdateDTO.setExpertise(candidateUpdateDTO.getExpertise());
        }

        if (candidateUpdateDTO.getExpertise() != null) {
            candidateEntity.setExperience(candidateUpdateDTO.getExperience());
        }

        if (candidateUpdateDTO.getEducation() != null) {
            candidateEntity.setEducation(candidateUpdateDTO.getEducation());
        }

        if (candidateUpdateDTO.getLocation() != null) {
            candidateEntity.setLocation(candidateUpdateDTO.getLocation());
        }

        candidateEntity.setUpdatedAt(candidateUpdateDTO.getUpdatedAt());

        return modelMap.convertTo(candidateDAO.save(candidateEntity), CandidateUpdateDTO.class);
    }

    @Override
    public void delete(Long id) {
        candidateDAO.deleteById(id);
    }
}
