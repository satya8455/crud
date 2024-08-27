package com.ncm.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ncm.crud.entity.Subcategory;



public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {
	  List<Subcategory> findByCategoryId(Long categoryId);


}
