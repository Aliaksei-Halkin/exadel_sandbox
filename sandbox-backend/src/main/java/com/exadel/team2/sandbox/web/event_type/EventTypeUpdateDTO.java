package com.exadel.team2.sandbox.web.event_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTypeUpdateDTO {

    @NotNull
    private String name;
    @NotNull
    private String description;

}
