package com.example.demobtvenha25_8.service.impl;

import com.example.demobtvenha25_8.dao.ProductDAO;
import com.example.demobtvenha25_8.dao.impl.ProductDaoImpl;
import com.example.demobtvenha25_8.model.Product;
import com.example.demobtvenha25_8.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDao = new ProductDaoImpl();

    @Override public List<Product> getAll()                        { return productDao.getAll(); }
    @Override public Product       getById(int id)                 { return productDao.getById(id); }
    @Override public List<Product> getLatest10()                   { return productDao.getLatest10(); }
    @Override public List<Product> getPage(int page, int pageSize) { return productDao.getPage(page, pageSize); }
    @Override public int           countAll()                      { return productDao.countAll(); }
    @Override public void          insert(Product p)               { productDao.insert(p); }
    @Override public void          update(Product p)               { productDao.update(p); }
    @Override public void          delete(int id)                  { productDao.delete(id); }
}
