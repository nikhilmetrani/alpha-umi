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

package io._29cu.usmserver.core.service;

import org.springframework.stereotype.Component;

import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.service.exception.CategoryAlreadyExistException;
import io._29cu.usmserver.core.service.exception.CategoryDoesNotExistException;
import io._29cu.usmserver.core.service.utilities.CategoryList;

@Component
public interface CategoryService {
    public Category createCategory(Category category) throws CategoryAlreadyExistException;
    public Category createCategory(String categoryName) throws CategoryAlreadyExistException;
    public Category findCategory(Long id);
    public Category findCategoryByName(String name);
	public CategoryList findCategories();
	public Category updateCategory(Category category) throws CategoryAlreadyExistException;
	public void deleteCategory(Long categoryId) throws CategoryDoesNotExistException;
}
