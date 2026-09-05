package com.example.demobtvenha25_8.dao.impl;

import com.example.demobtvenha25_8.dao.DBcontext;
import com.example.demobtvenha25_8.dao.ProductDAO;
import com.example.demobtvenha25_8.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDAO {

    @Override
    public List<Product> getAll() {
        String sql = "SELECT p.*, c.cate_name FROM Product p "
                   + "LEFT JOIN Category c ON p.cate_id = c.cate_id "
                   + "ORDER BY p.product_id DESC";
        return query(sql);
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT p.*, c.cate_name FROM Product p "
                   + "LEFT JOIN Category c ON p.cate_id = c.cate_id "
                   + "WHERE p.product_id = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Product> getLatest10() {
        String sql = "SELECT TOP 10 p.*, c.cate_name FROM Product p "
                   + "LEFT JOIN Category c ON p.cate_id = c.cate_id "
                   + "ORDER BY p.product_id DESC";
        return query(sql);
    }

    @Override
    public List<Product> getPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT p.*, c.cate_name FROM Product p "
                   + "LEFT JOIN Category c ON p.cate_id = c.cate_id "
                   + "ORDER BY p.product_id DESC "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Product";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public void insert(Product p) {
        String sql = "INSERT INTO Product(product_name, description, price, quantity, image, cate_id, created_date) "
                   + "VALUES (?,?,?,?,?,?, GETDATE())";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getDescription());
            ps.setBigDecimal(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getCateId());
            ps.executeUpdate();
        } catch (Exception e) {
            // Neu bang khong co cot created_date thi fallback ve insert 6 tham so
            String fallbackSql = "INSERT INTO Product(product_name, description, price, quantity, image, cate_id) "
                               + "VALUES (?,?,?,?,?,?)";
            try (Connection conn2 = DBcontext.getConnection();
                 PreparedStatement ps2 = conn2.prepareStatement(fallbackSql)) {
                ps2.setString(1, p.getProductName());
                ps2.setString(2, p.getDescription());
                ps2.setBigDecimal(3, p.getPrice());
                ps2.setInt(4, p.getQuantity());
                ps2.setString(5, p.getImage());
                ps2.setInt(6, p.getCateId());
                ps2.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    @Override
    public void update(Product p) {
        String sql = "UPDATE Product SET product_name=?, description=?, price=?, "
                   + "quantity=?, image=?, cate_id=? WHERE product_id=?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setString(2, p.getDescription());
            ps.setBigDecimal(3, p.getPrice());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getImage());
            ps.setInt(6, p.getCateId());
            ps.setInt(7, p.getProductId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setProductName(rs.getString("product_name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setQuantity(rs.getInt("quantity"));
        p.setImage(rs.getString("image"));
        p.setCateId(rs.getInt("cate_id"));
        try { p.setCreatedDate(rs.getTimestamp("created_date")); } catch (SQLException ignored) {}
        try { p.setCateName(rs.getString("cate_name")); } catch (SQLException ignored) {}
        return p;
    }

    private List<Product> query(String sql) {
        try (Connection conn = DBcontext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Product> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (Exception e) { e.printStackTrace(); }
        return new ArrayList<>();
    }
}
