package team.project.WhatToEatToday.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LongIdForm {

    private Long id;
    private List<Long> ids;
}
