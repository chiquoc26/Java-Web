package com.example.demobtvenha25_8.service.impl;

import com.example.demobtvenha25_8.dao.CategoryDAO;
import com.example.demobtvenha25_8.dao.impl.CategoryDaoImpl;
import com.example.demobtvenha25_8.model.Category;
import com.example.demobtvenha25_8.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDaoImpl();

    @Override
    public void insert(Category category) {
        categoryDAO.insert(category);
    }

    @Override
    public void edit(Category category) {
        categoryDAO.edit(category);
    }

    @Override
    public void delete(int cateId) {
        categoryDAO.delete(cateId);
    }

    @Override
    public Category get(int cateId) {
        return categoryDAO.get(cateId);
    }

    @Override
    public Category get(String cateName) {
        return categoryDAO.get(cateName);
    }

    @Override
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Override
    public List<Category> search(String keyword) {
        return categoryDAO.search(keyword);
    }
}