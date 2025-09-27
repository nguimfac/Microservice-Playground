package com.playground.inventory_service.service;

import com.playground.inventory_service.entities.category.Category;

public interface CategoryService {
    Category findCategoryById(long id);
}
