package team.project.WhatToEatToday.dto;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.member.Manager;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class EatingHouseForm {

    @NotEmpty(message = "매장 이름은 필수 입니다.")
    private String Name;

    private Manager manager;

    private String description;

    private String address;

    private String addressDetail;

}
