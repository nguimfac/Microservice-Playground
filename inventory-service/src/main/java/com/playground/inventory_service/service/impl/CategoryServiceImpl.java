package com.playground.inventory_service.service.impl;

import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.inventory_service.model.Category;
import com.playground.inventory_service.repository.CategoryRepository;
import com.playground.inventory_service.service.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(()->new NoSuchElementFoundException("No category found with id " + id));
    }
}
