package com.example.demobtvenha25_8;

import com.example.demobtvenha25_8.dao.CategoryDAO;
import com.example.demobtvenha25_8.dao.impl.CategoryDaoImpl;
import com.example.demobtvenha25_8.model.Category;

public class CategoryTest {

    public static void main(String[] args) {

        CategoryDAO categoryDAO = new CategoryDaoImpl();

        /*
         * =========================================================
         * 1. GET ALL
         * =========================================================
         */
        System.out.println("========================================");
        System.out.println("1. GET ALL");
        System.out.println("========================================");

        for (Category category : categoryDAO.getAll()) {
            System.out.println(category);
        }


        /*
         * =========================================================
         * 2. GET BY ID
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("2. GET BY ID");
        System.out.println("========================================");

        int testId = 1;

        Category categoryById = categoryDAO.get(testId);

        if (categoryById != null) {
            System.out.println("Tìm thấy Category:");
            System.out.println(categoryById);
        } else {
            System.out.println("Không tìm thấy Category có ID = " + testId);
        }


        /*
         * =========================================================
         * 3. GET BY NAME
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("3. GET BY NAME");
        System.out.println("========================================");

        String testName = "Laptop";

        Category categoryByName = categoryDAO.get(testName);

        if (categoryByName != null) {
            System.out.println("Tìm thấy Category:");
            System.out.println(categoryByName);
        } else {
            System.out.println("Không tìm thấy Category có tên = " + testName);
        }


        /*
         * =========================================================
         * 4. INSERT
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("4. INSERT");
        System.out.println("========================================");

        Category newCategory = new Category(
                "Test Category",
                "test-icon.png"
        );

        categoryDAO.insert(newCategory);

        System.out.println("Đã INSERT:");
        System.out.println(newCategory);


        /*
         * =========================================================
         * 5. TÌM CATEGORY VỪA INSERT
         *
         * Vì insert() hiện tại của DAO chưa trả về generated ID,
         * ta tìm bằng tên.
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("5. GET INSERTED CATEGORY");
        System.out.println("========================================");

        Category insertedCategory = categoryDAO.get("Test Category");

        if (insertedCategory != null) {

            System.out.println("Tìm thấy Category vừa INSERT:");
            System.out.println(insertedCategory);

        } else {

            System.out.println("INSERT thất bại!");
            return;
        }


        /*
         * =========================================================
         * 6. UPDATE
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("6. UPDATE");
        System.out.println("========================================");

        insertedCategory.setCateName("Test Category Updated");
        insertedCategory.setIcons("updated-icon.png");

        categoryDAO.edit(insertedCategory);

        System.out.println("Đã UPDATE:");
        System.out.println(categoryDAO.get(insertedCategory.getCateId()));


        /*
         * =========================================================
         * 7. SEARCH
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("7. SEARCH");
        System.out.println("========================================");

        String keyword = "Test";

        for (Category category : categoryDAO.search(keyword)) {
            System.out.println(category);
        }


        /*
         * =========================================================
         * 8. DELETE
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("8. DELETE");
        System.out.println("========================================");

        int insertedId = insertedCategory.getCateId();

        categoryDAO.delete(insertedId);

        System.out.println("Đã DELETE Category có ID = " + insertedId);


        /*
         * =========================================================
         * 9. KIỂM TRA DELETE
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("9. CHECK DELETE");
        System.out.println("========================================");

        Category deletedCategory = categoryDAO.get(insertedId);

        if (deletedCategory == null) {
            System.out.println("DELETE thành công!");
        } else {
            System.out.println("DELETE thất bại!");
            System.out.println(deletedCategory);
        }


        /*
         * =========================================================
         * KẾT THÚC
         * =========================================================
         */
        System.out.println();
        System.out.println("========================================");
        System.out.println("CATEGORY DAO TEST FINISHED");
        System.out.println("========================================");
    }
}