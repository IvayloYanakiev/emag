package com.emag.service;

import com.emag.exception.CategoryException;
import com.emag.model.Category;

import java.util.Map;

public interface CategoryService {
     Map<Long, Category> getAllCategories() throws CategoryException;
}
