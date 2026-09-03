package com.example.demobtvenha25_8.dao;

import com.example.demobtvenha25_8.model.Category;

import java.util.List;

public interface CategoryDAO {

    void insert(Category category);

    void edit(Category category);

    void delete(int cateId);

    Category get(int cateId);

    Category get(String cateName);

    List<Category> getAll();

    List<Category> search(String keyword);
}