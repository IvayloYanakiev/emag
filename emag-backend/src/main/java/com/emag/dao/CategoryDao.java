package com.emag.dao;

import com.emag.exception.CategoryException;
import java.util.Map;

public interface CategoryDao {
    Map getAllCategories() throws CategoryException;
}
