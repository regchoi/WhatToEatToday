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


    @Transactional
    public Long join(Category category) {
        categoryRepository.save(category);
        return category.getId();
    }
//
//    @Transactional
//    public Long delete(Category category) {
//        Long deletedId = category.getId();
//        categoryRepository.delete(category);
//        return deletedId;
//    }

    //전체 조회
    public List<Category> findCategory() { return categoryRepository.findAll(); }

    public List<Category> findCategoryExOne() {
        List<Category> category = categoryRepository.findAll();
        category.remove(0);
        return category;
    }


    public Category findOne(Long categoryId) {
        return categoryRepository.findOne(categoryId);
    }


}
