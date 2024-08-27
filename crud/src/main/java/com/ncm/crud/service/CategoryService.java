package com.ncm.crud.service;

import java.util.List;

import com.ncm.crud.entity.Category;

public interface CategoryService {
	Category saveCategory(Category category);
	List<Category> getAllCategories();
	Category getCategoryById(Long categoryId);
	Category findByName(String name);
	Category save(Category category);
	}

