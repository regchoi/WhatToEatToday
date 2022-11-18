package team.project.WhatToEatToday.dto;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.member.Manager;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class EditConcateForm {

    private String before;
    private String after;


}
