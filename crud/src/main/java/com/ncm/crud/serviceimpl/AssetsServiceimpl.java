package com.ncm.crud.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Assets;
import com.ncm.crud.entity.Category;
import com.ncm.crud.repository.AssetsRapo;
import com.ncm.crud.repository.CategoryRapo;
import com.ncm.crud.service.AssetsService;

@Service
public class AssetsServiceimpl implements AssetsService {

	@Autowired
	private CategoryRapo categoryRapo;
	@Autowired
	private AssetsRapo assetRepository;
@Override
	public List<Category> getAllCategories() {
		return categoryRapo.findAll();
	}
@Override
	public Assets saveAsset(Assets asset) {
		return assetRepository.save(asset);
	}
@Override
	public List<Assets> getAllAssets() {
		return assetRepository.findAll();
	}
@Override
	public Optional<Assets> getAssetById(Integer id) {
		return assetRepository.findById(id);
	}
@Override
	public void deleteAsset(Integer id) {
		assetRepository.deleteById(id);
	}
@Override
	public List<String> getAllSerialNumbers() {

		return assetRepository.findAllSerialNumbers();
	}
@Override
	public List<String> getAllCompnay() {

		return assetRepository.findAllCompanyName();
	}
@Override
	public Assets save(Assets asset) {
		return assetRepository.save(asset);
	}
@Override
	public Optional<Assets> findByCategoryAndSubcategoryAndCompanyNameAndSerialNumber(String category,
			String subcategory, String companyName, String serialNumber) {
		// TODO Auto-generated method stub
		System.out.println("Category: " + category);
		System.out.println("Subcategory ID: " + subcategory);
		System.out.println("Company Name: " + companyName);
		System.out.println("Serial Number: " + serialNumber);
		return assetRepository.findByCategoryAndSubcategoryAndCompanyNameAndSerialNumber(category, subcategory,
				companyName, serialNumber);
	}
}
