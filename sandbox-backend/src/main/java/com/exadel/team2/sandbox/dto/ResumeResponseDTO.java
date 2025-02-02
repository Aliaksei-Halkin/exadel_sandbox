package com.exadel.team2.sandbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ResumeResponseDTO {

    private Long id;

    private String link;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    private Integer size;


    private LocalDateTime createdAt;
}
