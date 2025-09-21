package com.playground.inventory_service.service.impl;

import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.inventory_service.model.category.Category;
import com.playground.inventory_service.dao.CategoryRepository;
import com.playground.inventory_service.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(()->new NoSuchElementFoundException("No category found with id " + id));
    }
}
