package com.emag.model;


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
        } else throw new CategoryException("Invalid category name");
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

    private void addCategory(Category category) throws CategoryException {
        if(category!=null){
            innerCategories.add(category);
        }
        else throw new CategoryException("Inner category doesn't exists");
    }


}
