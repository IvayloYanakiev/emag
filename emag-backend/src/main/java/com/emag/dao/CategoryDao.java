package com.emag.dao;

import com.emag.exception.CategoryException;
import com.emag.model.Category;

import java.util.HashMap;

public interface CategoryDao {
    HashMap<Long, Category> getAllCategories() throws CategoryException;
}
