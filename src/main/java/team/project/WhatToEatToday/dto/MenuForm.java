package team.project.WhatToEatToday.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.EatingHouse;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuForm {
    @NotEmpty
    private String name;
    private int price;
    private Long category;
    private MultipartFile multipartFile;
}
