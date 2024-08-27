package com.ncm.crud.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Category;
import com.ncm.crud.repository.CategoryRapo;
import com.ncm.crud.service.CategoryService;
@Service
public class CategoryServiceimpl implements CategoryService {
	 @Autowired
	    private CategoryRapo categoryRepository;
	@Override
	 public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }


	@Override
	public  List<Category> getAllCategories() {
		 return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Long categoryId) {
	     return categoryRepository.findById(categoryId).orElse(null);
	}
	@Override
	 public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

	@Override
	  public Category save(Category category) {
        return categoryRepository.save(category);
    }

}
