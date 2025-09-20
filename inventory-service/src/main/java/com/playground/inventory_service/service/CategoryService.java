package com.playground.inventory_service.service;

import com.playground.inventory_service.model.category.Category;

public interface CategoryService {
    Category findCategoryById(long id);
}
