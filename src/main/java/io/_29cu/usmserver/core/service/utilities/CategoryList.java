package io._29cu.usmserver.core.service.utilities;

import java.util.List;

import io._29cu.usmserver.core.model.entities.Category;

public class CategoryList extends EntityList<Category> {

	public CategoryList() {
	}

	public CategoryList(List<Category> categories) {
		super();
		super.setItems(categories);
	}

	public List<Category> getCategories() {
		return super.getItems();
	}

	public void setCategories(List<Category> categories) {
		super.setItems(categories);
	}
}
