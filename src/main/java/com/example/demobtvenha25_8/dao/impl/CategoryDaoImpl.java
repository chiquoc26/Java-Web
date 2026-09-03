package com.example.demobtvenha25_8.dao.impl;

import com.example.demobtvenha25_8.dao.CategoryDAO;
import com.example.demobtvenha25_8.dao.DBcontext;
import com.example.demobtvenha25_8.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDAO {

    @Override
    public void insert(Category category) {

        String sql = """
                INSERT INTO Category (cate_name, icons)
                VALUES (?, ?)
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, category.getCateName());
            ps.setString(2, category.getIcons());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Category category) {

        String sql = """
                UPDATE Category
                SET cate_name = ?, icons = ?
                WHERE cate_id = ?
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, category.getCateName());
            ps.setString(2, category.getIcons());
            ps.setInt(3, category.getCateId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int cateId) {

        String sql = """
                DELETE FROM Category
                WHERE cate_id = ?
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, cateId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category get(int cateId) {

        String sql = """
                SELECT cate_id, cate_name, icons
                FROM Category
                WHERE cate_id = ?
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, cateId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Category category = new Category();

                    category.setCateId(rs.getInt("cate_id"));
                    category.setCateName(rs.getString("cate_name"));
                    category.setIcons(rs.getString("icons"));

                    return category;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Category get(String cateName) {

        String sql = """
                SELECT cate_id, cate_name, icons
                FROM Category
                WHERE cate_name = ?
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, cateName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Category category = new Category();

                    category.setCateId(rs.getInt("cate_id"));
                    category.setCateName(rs.getString("cate_name"));
                    category.setIcons(rs.getString("icons"));

                    return category;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Category> getAll() {

        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT cate_id, cate_name, icons
                FROM Category
                ORDER BY cate_id
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Category category = new Category();

                category.setCateId(rs.getInt("cate_id"));
                category.setCateName(rs.getString("cate_name"));
                category.setIcons(rs.getString("icons"));

                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public List<Category> search(String keyword) {

        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT cate_id, cate_name, icons
                FROM Category
                WHERE cate_name LIKE ?
                ORDER BY cate_id
                """;

        try (
                Connection conn = DBcontext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Category category = new Category();

                    category.setCateId(rs.getInt("cate_id"));
                    category.setCateName(rs.getString("cate_name"));
                    category.setIcons(rs.getString("icons"));

                    categories.add(category);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }
}
