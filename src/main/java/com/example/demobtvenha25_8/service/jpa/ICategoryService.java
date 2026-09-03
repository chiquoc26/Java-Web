package com.example.demobtvenha25_8.service.jpa;

import com.example.demobtvenha25_8.model.Category;
import java.util.List;

public interface ICategoryService {

    void insert(Category category);

    void update(Category category);

    void delete(int cateId) throws Exception;

    Category findById(int cateId);

    Category findByCategoryName(String cateName);

    List<Category> findAll();

    List<Category> searchByName(String cateName);

    int count();

    List<Category> findAll(int page, int pageSize);
}
