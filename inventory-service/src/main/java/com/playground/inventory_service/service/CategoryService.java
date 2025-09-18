package com.playground.inventory_service.service;

import com.playground.inventory_service.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CategoryService {
    Category findCategoryById(long id);
}
