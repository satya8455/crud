package com.ncm.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Assets;
import com.ncm.crud.entity.Category;
import com.ncm.crud.repository.AssetsRapo;
import com.ncm.crud.repository.CategoryRapo;


public interface AssetsService {
   
	
	 List<Category> getAllCategories();
	 Assets saveAsset(Assets asset);
	 List<Assets> getAllAssets();
	 Optional<Assets> getAssetById(Integer id);
	 void deleteAsset(Integer id);
	 List<String> getAllSerialNumbers();
	 List<String> getAllCompnay();
	 Assets save(Assets asset);	
	 Optional<Assets> findByCategoryAndSubcategoryAndCompanyNameAndSerialNumber(String category,
				String subcategory, String companyName, String serialNumber);
	}

	
	
	
	
