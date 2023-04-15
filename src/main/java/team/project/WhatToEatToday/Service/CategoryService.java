package team.project.WhatToEatToday.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.project.WhatToEatToday.domain.Category;
import team.project.WhatToEatToday.domain.EatingHouse;
import team.project.WhatToEatToday.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private  final CategoryRepository categoryRepository;


    public List<Category> findCategoryExOne() {
        List<Category> category = categoryRepository.findAll();
        category.remove(0);
        return category;
    }


}
