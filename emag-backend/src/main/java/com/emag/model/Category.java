package com.emag.model;


import com.emag.config.ErrorMessages;
import com.emag.exceptions.CategoryException;

import java.util.LinkedHashSet;

public class Category {
    private Long id;
    private String name;
    private LinkedHashSet<Category> innerCategories;

    public Category(Long id, String name) throws CategoryException {
        setId(id);
        setName(name);
        this.innerCategories = new LinkedHashSet<>();
    }

    public void setName(String name) throws CategoryException {
        if (name != null && name.trim().length() > 0) {
            this.name = name;
        } else throw new CategoryException(ErrorMessages.INVALID_CATEGORY_NAME);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void addCategory(Category category) throws CategoryException {
        if(category!=null){
            innerCategories.add(category);
        }
        else throw new CategoryException(ErrorMessages.INVALID_INNER_CATEGORY);
    }


}
