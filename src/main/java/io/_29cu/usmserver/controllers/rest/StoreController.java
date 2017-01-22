/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest;

import io._29cu.usmserver.controllers.rest.resources.ApplicationListResource;
import io._29cu.usmserver.controllers.rest.resources.ApplicationResource;
import io._29cu.usmserver.controllers.rest.resources.CategoryListResource;
import io._29cu.usmserver.controllers.rest.resources.CategoryResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ApplicationResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.CategoryListResourceAssembler;
import io._29cu.usmserver.controllers.rest.resources.assemblers.CategoryResourceAssembler;
import io._29cu.usmserver.core.model.entities.Application;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.model.enumerations.AppListType;
import io._29cu.usmserver.core.model.enumerations.AppState;
import io._29cu.usmserver.core.service.ApplicationListService;
import io._29cu.usmserver.core.service.ApplicationService;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.SubscriptionService;
import io._29cu.usmserver.core.service.exception.CategoryAlreadyExistException;
import io._29cu.usmserver.core.service.exception.CategoryDoesNotExistException;
import io._29cu.usmserver.core.service.utilities.ApplicationList;
import io._29cu.usmserver.core.service.utilities.CategoryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/api/1/store")
public class StoreController {
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	ApplicationListService applicationListService;

	@Autowired
	CategoryService categoryService;

	/**
	 * Get active store applications
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> store() {
		try {
			ApplicationList appList = applicationService.getAllActiveApplications();
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * get all store applications
	 * @param category
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(value = "/{category}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getApplications(@PathVariable String category) {
		try {
			ApplicationList appList = applicationService.findApplicationsByCategoryAndState(category,
					AppState.Active);
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get store applications by browse type
	 * @param browseType The browse type to be search with
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(value = "/browse/{browseType}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getApplicationsByBrowseType(@PathVariable AppListType browseType) {
		try {
			ApplicationList appList = applicationListService.getApplicationBrowsingList(browseType);
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get categories
	 * @return
	 * @see CategoryListResource
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<CategoryListResource> getCategories() {
		try {
			CategoryList categoryList = categoryService.findCategories();
			CategoryListResource resource = new CategoryListResourceAssembler().toResource(categoryList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get category by category id
	 * @param categoryId The category id to be searched with
	 * @return
	 * @see CategoryResource
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<CategoryResource> getCategory(@PathVariable Long categoryId) {
		try {
			Category category = categoryService.findCategory(categoryId);
			if (category == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			CategoryResource categoryResource = new CategoryResourceAssembler().toResource(category);
			return new ResponseEntity<>(categoryResource, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Create category
	 * @param categoryResource The category to be created
	 * @return
	 * @see CategoryResource
	 */
	@RequestMapping(path = "/category/create", method = RequestMethod.POST)
	public ResponseEntity<CategoryResource> createCategory(@RequestBody CategoryResource categoryResource) {
		Category category = categoryResource.toEntity();
		try {
			category = categoryService.createCategory(category);
		} catch (CategoryAlreadyExistException e) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		}
		CategoryResource createdCategoryResource = new CategoryResourceAssembler().toResource(category);
		return new ResponseEntity<>(createdCategoryResource, HttpStatus.CREATED);
	}

	/**
	 * Update category
	 * @param categoryResource The category to be updated
	 * @return
	 * @see CategoryResource
	 */
	@RequestMapping(path = "/category/update", method = RequestMethod.POST)
	public ResponseEntity<CategoryResource> updateCategory(@RequestBody CategoryResource categoryResource) {
		Category category = categoryResource.toEntity();
		try {
			category = categoryService.updateCategory(category);
		} catch (CategoryAlreadyExistException e) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		}
		CategoryResource updatedCategoryResource = new CategoryResourceAssembler().toResource(category);
		return new ResponseEntity<>(updatedCategoryResource, HttpStatus.OK);
	}

	/**
	 * Delete category
	 * @param categoryId The id of the category to be deleted
	 * @return
	 * @see CategoryResource
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<CategoryResource> deleteCategory(@PathVariable Long categoryId) {
		try {
			categoryService.deleteCategory(categoryId);
		} catch (CategoryDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Check whether the category name already exists
	 * @param name The category name to be checked
	 * @return
	 * @see CategoryResource
	 */
	@RequestMapping(path = "/category/check", method = RequestMethod.GET)
	public ResponseEntity<CategoryResource> checkCategoryNameExistsForDeveloper(
			@RequestParam String name
	) {
		//Let's check whether the application is already registered.
		Category existingCat = categoryService.findCategoryByName(name);

		if (null == existingCat) { //We can't find the category in our database
			return new ResponseEntity<CategoryResource>(HttpStatus.NO_CONTENT);
		} else {
			// Category with same name already exists
			return new ResponseEntity<CategoryResource>(HttpStatus.OK);
		}
	}

	/**
	 * Search storey applications
	 * @param keyword The keyword to be searched with
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> searchApplication(@RequestParam("keyword") String keyword) {
		try {
			if (keyword == null || keyword.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ApplicationList appList = applicationService.findApplicationsByKeyword(keyword);
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Search store applications by category
	 * @param categoryId The category id to be search with
	 * @param keyword The keyword to be search with
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(value = "/search/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> searchApplicationByCateogry(@PathVariable Long categoryId, @RequestParam("keyword") String keyword) {
		try {
			if (keyword == null || keyword.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			ApplicationList appList = applicationService.findApplicationsByCategoryAndKeyword(categoryId, keyword);
			if (appList.getItems() == null || appList.getItems().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get application by id
	 * @param appId The application id to be searched with
	 * @return
	 * @see ApplicationResource
	 */
	@RequestMapping(path = "/applications/{appId}", method = RequestMethod.GET)
	public ResponseEntity<ApplicationResource> getApplication(
			@PathVariable String appId
	) {
		try {
			Application application = applicationService.findApplication(appId);
			ApplicationResource applicationResource = new ApplicationResourceAssembler().toResource(application);
			return new ResponseEntity<>(applicationResource, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get trending applications
	 * @return
	 * @see ApplicationListResource
	 */
	@RequestMapping(value = "/trending", method = RequestMethod.GET)
	public ResponseEntity<ApplicationListResource> getTrendingApplications() {
		try {
			ApplicationList appList = subscriptionService.getTrendingApplications();
			if (appList.getItems() == null || appList.getItems().isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			ApplicationListResource resource = new ApplicationListResourceAssembler().toResource(appList);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
