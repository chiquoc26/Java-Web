package com.example.demobtvenha25_8;

import com.example.demobtvenha25_8.dao.jpa.CategoryDao;
import com.example.demobtvenha25_8.dao.jpa.ICategoryDao;
import com.example.demobtvenha25_8.model.Category;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryJpaTest {

    private static ICategoryDao categoryDao;
    private static int testId = -1;
    private static final String testName = "TestJPA_Category";
    private static final String testUpdatedName = "TestJPA_Category_Updated";

    @BeforeAll
    public static void setup() {
        categoryDao = new CategoryDao();
    }

    @Test
    @Order(1)
    public void testFindAll() {
        List<Category> list = categoryDao.findAll();
        assertNotNull(list);
    }

    @Test
    @Order(2)
    public void testInsert() {
        Category category = new Category();
        category.setCateName(testName);
        category.setIcons("test_icon.jpg");

        categoryDao.insert(category);
        assertTrue(category.getCateId() > 0, "Insert failed, category ID should be generated");
        testId = category.getCateId();
    }

    @Test
    @Order(3)
    public void testFindById() {
        Category category = categoryDao.findById(testId);
        assertNotNull(category);
        assertEquals(testName, category.getCateName());
    }

    @Test
    @Order(4)
    public void testFindByCategoryName() throws Exception {
        Category category = categoryDao.findByCategoryName(testName);
        assertNotNull(category);
        assertEquals(testId, category.getCateId());
    }

    @Test
    @Order(5)
    public void testUpdate() {
        Category category = categoryDao.findById(testId);
        assertNotNull(category);
        category.setCateName(testUpdatedName);
        categoryDao.update(category);

        Category updated = categoryDao.findById(testId);
        assertEquals(testUpdatedName, updated.getCateName());
    }

    @Test
    @Order(6)
    public void testSearchByName() {
        List<Category> list = categoryDao.searchByName("TestJPA");
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    @Order(7)
    public void testCount() {
        int count = categoryDao.count();
        assertTrue(count > 0);
    }

    @Test
    @Order(8)
    public void testDelete() throws Exception {
        categoryDao.delete(testId);
        Category deleted = categoryDao.findById(testId);
        assertNull(deleted);
    }
}
