package team.project.WhatToEatToday.dto;

import lombok.Getter;
import lombok.Setter;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.EatingHouse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuForm {
    private String name;
    private int price;
    private Long category;
}
