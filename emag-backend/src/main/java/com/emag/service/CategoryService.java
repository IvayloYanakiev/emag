package com.emag.service;

import com.emag.exception.CategoryException;
import com.emag.model.Category;

import java.util.HashMap;

public interface CategoryService {
    HashMap<Long, Category> getAllCategories() throws CategoryException;
}
