package com.emag.service;

import com.emag.exceptions.CategoryException;
import com.emag.model.Category;

import java.util.HashSet;

public interface CategoryService {
    HashSet<Category> getAllCategories() throws CategoryException;
}
