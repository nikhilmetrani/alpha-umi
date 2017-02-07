package io._29cu.usmserver.controllers.rest.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.StoreController;
import io._29cu.usmserver.controllers.rest.resources.CategoryResource;
import io._29cu.usmserver.core.model.entities.Category;

public class CategoryResourceAssembler extends ResourceAssemblerSupport<Category, CategoryResource> {

	public CategoryResourceAssembler() {
		super(StoreController.class, CategoryResource.class);
	}

	@Override
	public CategoryResource toResource(Category category) {
		CategoryResource categoryResource = new CategoryResource();
		categoryResource.setRid(category.getId());
		categoryResource.setName(category.getName());
		return categoryResource;
	}

}
