package com.ncm.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ncm.crud.entity.Assets;
import com.ncm.crud.entity.Category;
import com.ncm.crud.entity.Subcategory;
import com.ncm.crud.service.AssetsService;
import com.ncm.crud.service.CategoryService;
import com.ncm.crud.service.SubcategoryService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AssetsController {

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

   
    @GetMapping("/assets")
    public String showForm(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
       
    	System.out.println("categoryId "+categoryId);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        if (categoryId != null) {
            List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(categoryId);
            model.addAttribute("subcategories", subcategories);
        } else {
            model.addAttribute("subcategories", List.of()); // Initialize empty list if no category is selected
        }

        model.addAttribute("selectedCategoryId", categoryId);
        return "assets"; 
    }

   
    @PostMapping("/assets")
    public String addItem(@RequestParam("category.id") Long categoryId,
                          @RequestParam("subcategory.id") Long subcategoryId,
                          @RequestParam("companyname") String companyname,
                          @RequestParam("slNo") String slNo,
                          @RequestParam("quantity") Integer quantity,
                          @RequestParam("date") LocalDate date,
                          Model model) {
System.out.println("category€Id "+categoryId);
        // Fetch category and subcategory entities from the database
        Category category = categoryService.getCategoryById(categoryId);
        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);

        // Check if category and subcategory are valid
        if (category == null || subcategory == null) {
            model.addAttribute("error", "Invalid category or subcategory.");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
            model.addAttribute("selectedCategoryId", categoryId);
            return "dashboard";
        }

        // Create and populate the asset entity
        Assets asset = new Assets();
        asset.setCategoryName(category.getName()); // Store category name
        asset.setSubcategoryName(subcategory.getSub_name()); // Store subcategory name
        asset.setCompanyname(companyname);
        asset.setSlNo(slNo);
        asset.setQuantity(quantity.toString()); 
        asset.setCreatedDate(date);

        // Save the asset
        assetsService.saveAsset(asset);

        model.addAttribute("message", "Asset added successfully!");
        return "redirect:/assets";
    }
}
