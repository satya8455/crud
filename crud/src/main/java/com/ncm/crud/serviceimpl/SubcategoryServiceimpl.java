package com.ncm.crud.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Category;
import com.ncm.crud.entity.Subcategory;
import com.ncm.crud.repository.CategoryRapo;
import com.ncm.crud.repository.SubcategoryRepo;
import com.ncm.crud.service.SubcategoryService;

@Service
public class SubcategoryServiceimpl implements SubcategoryService{
	  
    @Autowired
    private SubcategoryRepo subcategoryRepo;
    @Autowired
    private CategoryRapo categoryRapo;
    
	@Override
	public List<Subcategory> getAllSubcategories() {
		 return subcategoryRepo.findAll();
	}

	@Override
	public Subcategory saveSubcategory(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRapo.findAll();
	}

	@Override
	public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId) {
		 return subcategoryRepo.findByCategoryId(categoryId);
	}

	@Override
	public Subcategory getSubcategoryById(Long subcategoryId) {
		return subcategoryRepo.findById(subcategoryId).orElse(null);
	}

	@Override
	public Subcategory save(Subcategory subcategory) {
		 return subcategoryRepo.save(subcategory);
	}
    
}
