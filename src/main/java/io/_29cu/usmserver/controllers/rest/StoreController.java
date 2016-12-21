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

package io._29cu.usmserver.controllers.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.CategoryListResource;
import io._29cu.usmserver.controllers.rest.resources.CategoryResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.CategoryListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.CategoryResourceAssembler;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationListService;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.exception.CategoryAlreadyExistException;
import io._29cu.usmserver.core.service.exception.CategoryDoesNotExistException;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.service.utilities.CategoryList;

@Controller
@RequestMapping("/api/1/store")
public class StoreController {
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	ApplicationListService applicationListService;

	@Autowired
	CategoryService categoryService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> store() {
		try {
			ApplicationList appList = applicationService.getAllActiveApplications();
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{category}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getApplication(@PathVariable String category) {
		try {
			ApplicationList appList = applicationService.findApplicationsByCategoryAndState(category,
					AppState.Active);
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/browse/{browseType}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getApplicationsByBrowseType(@PathVariable AppListType browseType) {
		try {
			ApplicationList appList = applicationListService.getApplicationBrowsingList(browseType);
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<CategoryListResource> getCategories() {
		try {
			CategoryList categoryList = categoryService.findCategories();
			CategoryListResource resource = new CategoryListResourceAssembler().toResource(categoryList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<CategoryListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<CategoryListResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<CategoryResource> getCategory(@PathVariable Long categoryId) {
		try {
			Category category = categoryService.findCategory(categoryId);
			if (category == null) {
				return new ResponseEntity<CategoryResource>(HttpStatus.NOT_FOUND);
			}
			CategoryResource categoryResource = new CategoryResourceAssembler().toResource(category);
			return new ResponseEntity<CategoryResource>(categoryResource, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<CategoryResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(path = "/category/create", method = RequestMethod.POST)
	public ResponseEntity<CategoryResource> createCategory(@RequestBody CategoryResource categoryResource) {
		Category category = categoryResource.toEntity();
		try {
			category = categoryService.createCategory(category);
		} catch (CategoryAlreadyExistException e) {
			return new ResponseEntity<CategoryResource>(HttpStatus.FOUND);
		}
		CategoryResource createdCategoryResource = new CategoryResourceAssembler().toResource(category);
		return new ResponseEntity<CategoryResource>(createdCategoryResource, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/category/update", method = RequestMethod.POST)
	public ResponseEntity<CategoryResource> updateCategory(@RequestBody CategoryResource categoryResource) {
		Category category = categoryResource.toEntity();
		try {
			category = categoryService.updateCategory(category);
		} catch (CategoryAlreadyExistException e) {
			return new ResponseEntity<CategoryResource>(HttpStatus.FOUND);
		}
		CategoryResource updatedCategoryResource = new CategoryResourceAssembler().toResource(category);
		return new ResponseEntity<CategoryResource>(updatedCategoryResource, HttpStatus.OK);
	}

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<CategoryResource> deleteCategory(@PathVariable Long categoryId) {
		try {
			categoryService.deleteCategory(categoryId);
		} catch (CategoryDoesNotExistException e) {
			return new ResponseEntity<CategoryResource>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CategoryResource>(HttpStatus.OK);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> searchApplication(@RequestParam("keyword") String keyword) {
		try {
			if(keyword == null || keyword.isEmpty()) {
				return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
			}
			ApplicationList appList = applicationService.findApplicationsByKeyword(keyword);
			if (appList.getItems() == null || appList.getItems().isEmpty()) {
				return new ResponseEntity<ApplicationListResource>(HttpStatus.NOT_FOUND);
			}
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/search/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> searchApplicationByCateogry(@PathVariable Long categoryId, @RequestParam("keyword") String keyword) {
		try {
			if(keyword == null || keyword.isEmpty()) {
				return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
			}
			ApplicationList appList = applicationService.findApplicationsByCategoryAndKeyword(categoryId, keyword);
			if (appList.getItems() == null || appList.getItems().isEmpty()) {
				return new ResponseEntity<ApplicationListResource>(HttpStatus.NOT_FOUND);
			}
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<ApplicationListResource>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<ApplicationListResource>(HttpStatus.BAD_REQUEST);
		}
	}
}
