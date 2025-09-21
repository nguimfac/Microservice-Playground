package com.playground.inventory_service.mapper.helper;

import com.playground.inventory_service.model.category.Category;
import com.playground.inventory_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryHelper {

    private final CategoryService categoryService;

    public CategoryHelper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category map(Long categoryId) {
       return Objects.isNull(categoryId) ?  null :  categoryService.findCategoryById(categoryId);
    }

}
