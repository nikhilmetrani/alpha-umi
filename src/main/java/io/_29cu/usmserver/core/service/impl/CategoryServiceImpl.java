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
import io._29cu.usmserver.core.repositories.CategoryRepository;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.utilities.CategoryList;

@Component
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category createCategory(String categoryName) {
        Category category = new Category(categoryName);
        return categoryRepository.save(category);
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
}
