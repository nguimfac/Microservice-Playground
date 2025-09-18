package com.playground.inventory_service.mapper.helper;

import com.playground.inventory_service.model.Category;
import com.playground.inventory_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryService categoryService;

    public Category map(Long categoryId) {
       return Objects.isNull(categoryId) ?  null :  categoryService.findCategoryById(categoryId);
    }

}
