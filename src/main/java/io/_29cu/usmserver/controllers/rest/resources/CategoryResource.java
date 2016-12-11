package io._29cu.usmserver.controllers.rest.resources;

import io._29cu.usmserver.core.model.entities.Category;

public class CategoryResource extends EntityResourceBase<Category> {

	private Long rid;
	private String name;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Category toEntity() {
		Category category = new Category();
		category.setId(getRid());
		category.setName(getName());
		return category;
	}

}
