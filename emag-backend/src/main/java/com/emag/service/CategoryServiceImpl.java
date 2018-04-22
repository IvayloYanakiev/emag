package com.emag.service;

import com.emag.dao.CategoryDao;
import com.emag.exceptions.CategoryException;
import com.emag.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public HashMap<Long, Category> getAllCategories() throws CategoryException {
        return categoryDao.getAllCategories();
    }
}
