package io._29cu.usmserver.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.service.exception.CategoryAlreadyExistException;
import io._29cu.usmserver.core.service.exception.CategoryDoesNotExistException;
import io._29cu.usmserver.core.service.utilities.CategoryList;
import io._29cu.usmserver.core.service.utilities.Codes;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTests {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryRepository categoryRepository;

	private Category category;

	@Before
	@Rollback(false)
	public void setup() {
		category = categoryFactory("os");
		Exception exception = null;
		try {
			categoryService.createCategory(category);
		} catch (CategoryAlreadyExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}

	}

	private Category categoryFactory(String name) {
		Category category = new Category();
		category.setName(name);
		return category;
	}

	@Test
	public void testFindCategoryByName() {
		category = categoryService.findCategoryByName(category.getName());
		assertNotNull(category);

	}

	@Test
	public void testFindCategoryByNonExistingName() {
		String version = "v2.0";
		String name = category.getName().concat(version);
		Category notExistingCategory = categoryService.findCategoryByName(name);
		assertNull(notExistingCategory);
	}

	@Test
	public void testCreateAlreadyCreatedCategory() {
		Exception exception = null;
		try {
			categoryService.createCategory(category);
		} catch (CategoryAlreadyExistException e) {
			exception = e;
		} finally {
			assertNotNull("CategoryAlreadyExistException Not thrown", exception);
		}
	}

	@Test
	public void testCreateCategoryByName() {
		Exception exception = null;
		String categoryName = "newOs";
		try {
			categoryService.createCategory(categoryName);
		} catch (CategoryAlreadyExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}
		Category newCategory = categoryService.findCategoryByName(categoryName);
		assertNotNull(newCategory);
		assertEquals(categoryName, newCategory.getName());
		try {
			categoryService.deleteCategory(newCategory.getId());
		} catch (CategoryDoesNotExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}
		Category findCategory = categoryService.findCategoryByName(categoryName);
		assertNull(findCategory);
	}

	@Test
	public void testUpdateCategoryByExistingName() {
		Exception exception = null;
		category = categoryService.findCategoryByName(category.getName());
		try {
			categoryService.updateCategory(category);
		} catch (CategoryAlreadyExistException e) {
			exception = e;
		} finally {
			assertNotNull("CategoryAlreadyExistException Not thrown", exception);
		}
	}

	@Test
	public void testUpdateCategory() {
		Exception exception = null;
		String version = "v1.0";
		String newName = category.getName().concat(version);
		category.setName(newName);
		Category updatedCategory = null;
		try {
			updatedCategory = categoryService.updateCategory(category);
		} catch (CategoryAlreadyExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}
		assertNotNull(updatedCategory);
		assertEquals("Category Not Updated", updatedCategory.getName(), category.getName());
	}

	@Test
	public void testDeleteCategory() {
		Exception exception = null;
		String name = category.getName();
		Category deleteCategory = categoryService.findCategoryByName(name);
		try {
			categoryService.deleteCategory(deleteCategory.getId());
		} catch (CategoryDoesNotExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}
		Category findCategory = categoryService.findCategoryByName(name);
		assertNull(findCategory);
	}

	@Test
	public void testFindCategoryById() {
		Category findCategory = categoryService.findCategory(category.getId());
		assertNotNull(findCategory);
	}

	@Test
	public void testFindNonExistingCategoryById() {
		Category findCategory = categoryService.findCategory(2222l);
		assertNull(findCategory);
	}

	@Test
	public void testDeleteNotExistingCategory() {
		Exception exception = null;
		Long nonExistentId = 2222l;
		try {
			categoryService.deleteCategory(nonExistentId);
		} catch (CategoryDoesNotExistException e) {
			exception = e;
		} finally {
			assertNotNull("CategoryDoesNotExistException Not thrown", exception);
		}
	}

	@Test
	public void testFindCategories() {
		CategoryList categoryList = categoryService.findCategories();
		assertNotNull(categoryList);
		assertNotNull(categoryList.getCategories());
		assertTrue(!categoryList.getCategories().isEmpty());
	}

	@Test
	public void testGetCategories() {
		Codes codes = categoryService.getCategories();
		assertNotNull(codes);
		assertNotNull(codes.getCodes());
		assertTrue(!codes.getCodes().isEmpty());
	}

	@After
	public void tearDown() {
		Exception exception = null;
		String name = category.getName();
		Category deleteCategory = categoryService.findCategoryByName(name);
		try {
			if (deleteCategory != null) {
				categoryService.deleteCategory(deleteCategory.getId());
			}
		} catch (CategoryDoesNotExistException e) {
			exception = e;
		} finally {
			assertNull("CategoryAlreadyExistException thrown", exception);
		}
	}
}
