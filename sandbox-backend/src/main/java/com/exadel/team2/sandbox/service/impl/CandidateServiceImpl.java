package com.exadel.team2.sandbox.service.impl;

import com.exadel.team2.sandbox.dao.CandidateDAO;
import com.exadel.team2.sandbox.dao.rsql.CustomRsqlVisitor;
import com.exadel.team2.sandbox.dto.CandidateCreateDTO;
import com.exadel.team2.sandbox.dto.CandidateResponseDTO;
import com.exadel.team2.sandbox.dto.CandidateUpdateDTO;
import com.exadel.team2.sandbox.entity.CandidateEntity;
import com.exadel.team2.sandbox.mapper.ModelMap;
import com.exadel.team2.sandbox.service.CandidateService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final String SUBJECT = "Exadel Internship";
    private final String MESSAGE = "Dear candidate, you've just registered at Exadel Internship. " + "\n" +
            "Please check your post to be aware regarding next steps";

    private final CandidateDAO candidateDAO;
    private final ModelMap modelMap;
    private final SendMailImpl sendMail;

    @Override
    public CandidateResponseDTO findById(Long id) {
        CandidateEntity candidateEntity = candidateDAO.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));

        return modelMap.convertTo(candidateEntity, CandidateResponseDTO.class);
    }

    @Override
    public Page<CandidateResponseDTO> getAllPageable(Pageable pageable, String search) {

        if (search.isEmpty()) {
            return new PageImpl<>(candidateDAO.findAll(pageable).stream()
                    .map((CandidateEntity candidateEntity) ->
                            (CandidateResponseDTO) modelMap.convertTo(
                                    candidateEntity, CandidateResponseDTO.class))
                    .collect(Collectors.toList()));
        }


        Node rootNode = new RSQLParser().parse(search);
        Specification<CandidateEntity> specification = rootNode.accept(new CustomRsqlVisitor<>());

        return new PageImpl<>(candidateDAO.findAll(specification, pageable).stream()
                .map((CandidateEntity candidateEntity) ->
                        (CandidateResponseDTO) modelMap.convertTo(
                                candidateEntity, CandidateResponseDTO.class))
                .collect(Collectors.toList()));
    }

    @Override
    public CandidateResponseDTO save(CandidateCreateDTO candidateCreateDTO) {

        sendMail.sendEmail(candidateCreateDTO.getEmail(), SUBJECT, MESSAGE);

        return modelMap.convertTo(candidateDAO.save(candidateDAO.save(modelMap
                        .convertTo(candidateCreateDTO, CandidateEntity.class))),
                CandidateResponseDTO.class);
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

        if (candidateUpdateDTO.getMainSkill() != null) {
            candidateEntity.setMainSkill(candidateUpdateDTO.getMainSkill());
        }

        if (candidateUpdateDTO.getOtherSkills() != null) {
            candidateEntity.setOtherSkills(candidateUpdateDTO.getOtherSkills());
        }

        if (candidateUpdateDTO.getEmail() != null) {
            candidateEntity.setEmail(candidateUpdateDTO.getEmail());
        }

        if (candidateUpdateDTO.getInstitution() != null) {
            candidateEntity.setInstitution(candidateUpdateDTO.getInstitution());
        }

        if (candidateUpdateDTO.getFaculty() != null) {
            candidateEntity.setFaculty(candidateUpdateDTO.getFaculty());
        }

        if (candidateUpdateDTO.getSpeciality() != null) {
            candidateEntity.setSpeciality(candidateUpdateDTO.getSpeciality());
        }

        if (candidateUpdateDTO.getGraduationDate() != null) {
            candidateEntity.setGraduationDate(candidateUpdateDTO.getGraduationDate());
        }

        if (candidateUpdateDTO.getCountry() != null) {
            candidateEntity.setCountry(candidateUpdateDTO.getCountry());
        }

        if (candidateUpdateDTO.getCity() != null) {
            candidateEntity.setCity(candidateUpdateDTO.getCity());
        }

        candidateEntity.setUpdatedAt(candidateUpdateDTO.getUpdatedAt());

        return modelMap.convertTo(candidateDAO.save(candidateEntity), CandidateUpdateDTO.class);
    }

    @Override
    public void delete(Long id) {
        candidateDAO.deleteById(id);
    }
}
