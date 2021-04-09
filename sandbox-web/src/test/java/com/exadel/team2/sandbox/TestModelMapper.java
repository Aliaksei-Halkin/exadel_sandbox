package com.exadel.team2.sandbox;

import com.exadel.team2.sandbox.dto.CandidateUpdateDTO;
import com.exadel.team2.sandbox.entity.CandidateEntity;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

public class TestModelMapper {
    private final static ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkCandidateMapper() {
        CandidateUpdateDTO candidateUpdateDTO = CandidateUpdateDTO.builder()
                .email("///")
                .englishLevel("...")
                .firstName("...")
                .phone("...")
                .skype("...")
                .location("...")
                .id(1L)
                .rsmId(1L)
                .build();

        CandidateEntity candidateEntity = modelMapper
                .map(candidateUpdateDTO, CandidateEntity.class);

        assertEquals(candidateUpdateDTO.getPhone(), candidateEntity.getPhone());
    }
}
