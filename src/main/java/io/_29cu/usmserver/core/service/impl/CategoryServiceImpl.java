/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.Code;
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.exception.CategoryAlreadyExistException;
import io._29cu.usmserver.core.service.exception.CategoryDoesNotExistException;
import io._29cu.usmserver.core.service.utilities.CategoryList;
import io._29cu.usmserver.core.service.utilities.Codes;

@Component
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) throws CategoryAlreadyExistException {
    	checkForExistingCategory(category);
        return categoryRepository.save(category);
    }
    
    @Override
    public Category updateCategory(Category category) throws CategoryAlreadyExistException {
    	checkForExistingCategory(category);
    	Category existingCategory = findCategory(category.getId());
    	existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

	private void checkForExistingCategory(Category category) throws CategoryAlreadyExistException {
		Category existingCategory = findCategoryByName(category.getName());
    	if (existingCategory != null) {
			throw new CategoryAlreadyExistException("Category already exists");
		}
	}

    @Override
    public Category createCategory(String categoryName) throws CategoryAlreadyExistException {
        Category category = new Category(categoryName);
        return createCategory(category);
    }

    @Override
    public Category findCategory(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    @Override
    public CategoryList findCategories() {
    	CategoryList categoryList = new CategoryList();
    	categoryList.setCategories(categoryRepository.findCategories());
        return categoryList;
    }

	@Override
	public void deleteCategory(Long categoryId) throws CategoryDoesNotExistException {
		Category category = findCategory(categoryId);
		if(category==null){
			throw new CategoryDoesNotExistException("Category does not exist");
		}
		categoryRepository.delete(categoryId);		
	}

	@Override
    public Codes getCategories() {
    	Codes codes = new Codes();
    	for(Category category : categoryRepository.findAll()) {
    		codes.add(new Code(category.getId().toString(), category.getName()));
    	}
    	return codes;
    }
}
