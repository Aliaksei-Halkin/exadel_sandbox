package com.exadel.team2.sandbox.dto;

import com.exadel.team2.sandbox.entity.enums.CandidateStatus;
import com.exadel.team2.sandbox.web.canidate_availability_time.CreateCandidateAvailabilityTimeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateUpdateDTO {

    private Long id;

    private Long rsmId;

    private List<CreateCandidateAvailabilityTimeDto> availabilityTimeSlots;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String skype;

    private String englishLevel;

    private String mainSkill;

    private String otherSkills;

    private String institution;

    private String faculty;

    private String speciality;

    private LocalDate graduationDate;

    private String country;

    private String city;

    private LocalDateTime interviewTime;

    private CandidateStatus status;

    @JsonIgnore
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
