package io._29cu.usmserver.controllers.rest.resources;

import java.util.ArrayList;
import java.util.List;

import io._29cu.usmserver.core.service.utilities.CategoryList;

public class CategoryListResource  extends EntityResourceBase<CategoryList> {
	
	 private List<CategoryResource> categories = new ArrayList<>();

	    public List<CategoryResource> getCategories() {
	        return categories;
	    }

	    public void setCategories(List<CategoryResource> categories) {
	        this.categories = categories;
	    }

	    @Override
	    public CategoryList toEntity() {
	        return null;
	    }

}
