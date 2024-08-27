package com.ncm.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ncm.crud.entity.Category;


public interface CategoryRapo  extends JpaRepository<Category, Long>{

	Category findByName(String name);



}
