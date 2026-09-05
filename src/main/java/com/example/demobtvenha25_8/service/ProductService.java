package com.example.demobtvenha25_8.service;

import com.example.demobtvenha25_8.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product       getById(int id);
    List<Product> getLatest10();
    List<Product> getPage(int page, int pageSize);
    int           countAll();
    void          insert(Product p);
    void          update(Product p);
    void          delete(int id);
}
