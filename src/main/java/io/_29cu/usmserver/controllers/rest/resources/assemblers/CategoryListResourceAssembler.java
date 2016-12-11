package io._29cu.usmserver.controllers.rest.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io._29cu.usmserver.controllers.rest.StoreController;
import io._29cu.usmserver.controllers.rest.resources.CategoryListResource;
import io._29cu.usmserver.controllers.rest.resources.CategoryResource;
import io._29cu.usmserver.core.service.utilities.CategoryList;

public class CategoryListResourceAssembler extends ResourceAssemblerSupport<CategoryList,CategoryListResource> {

	public CategoryListResourceAssembler() {
		super(StoreController.class, CategoryListResource.class);
	}

	@Override
	public CategoryListResource toResource(CategoryList categoryList) {
		List<CategoryResource> catResourceList = new CategoryResourceAssembler().toResources(categoryList.getCategories());
		CategoryListResource categoryListResource = new CategoryListResource();
		categoryListResource.setCategories(catResourceList);
		categoryListResource.add(linkTo(methodOn(StoreController.class).getCategories()).withSelfRel());
		return categoryListResource;
	}

}
