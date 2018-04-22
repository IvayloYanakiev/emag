package com.emag.dao;

import com.emag.exceptions.CategoryException;
import com.emag.model.Category;

import java.util.HashSet;

public interface CategoryDao {
    HashSet<Category> getAllCategories() throws CategoryException;
}
