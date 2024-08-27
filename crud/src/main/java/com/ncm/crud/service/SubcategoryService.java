package com.ncm.crud.service;

import java.util.List;
import com.ncm.crud.entity.Category;
import com.ncm.crud.entity.Subcategory;

public interface SubcategoryService {

	public List<Subcategory> getAllSubcategories();

	public Subcategory saveSubcategory(Subcategory subcategory);

	public List<Category> getAllCategories();

	public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId);

	public Subcategory getSubcategoryById(Long subcategoryId);

	public Subcategory save(Subcategory subcategory);

}
