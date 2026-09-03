package com.example.demobtvenha25_8.service.jpa;

import com.example.demobtvenha25_8.dao.jpa.CategoryDao;
import com.example.demobtvenha25_8.dao.jpa.ICategoryDao;
import com.example.demobtvenha25_8.model.Category;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDao categoryDao = new CategoryDao();

    @Override
    public void insert(Category category) {
        try {
            Category exist = categoryDao.findByCategoryName(category.getCateName());
            if (exist != null) {
                throw new RuntimeException("Tên danh mục đã tồn tại");
            }
            categoryDao.insert(category);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        Category old = categoryDao.findById(category.getCateId());
        if (old == null) {
            throw new RuntimeException("Danh mục không tồn tại");
        }
        try {
            Category exist = categoryDao.findByCategoryName(category.getCateName());
            if (exist != null && exist.getCateId() != category.getCateId()) {
                throw new RuntimeException("Tên danh mục đã tồn tại");
            }
            categoryDao.update(category);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int cateId) throws Exception {
        categoryDao.delete(cateId);
    }

    @Override
    public Category findById(int cateId) {
        return categoryDao.findById(cateId);
    }

    @Override
    public Category findByCategoryName(String cateName) {
        try {
            return categoryDao.findByCategoryName(cateName);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category> searchByName(String cateName) {
        return categoryDao.searchByName(cateName);
    }

    @Override
    public int count() {
        return categoryDao.count();
    }

    @Override
    public List<Category> findAll(int page, int pageSize) {
        return categoryDao.findAll(page, pageSize);
    }
}
