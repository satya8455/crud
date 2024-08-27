package com.ncm.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncm.crud.entity.Subcategory;

import com.ncm.crud.service.SubcategoryService;
import com.ncm.crud.service.CategoryService;

import java.util.List;

@Controller
public class SubcategorieController {

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/subcategorie")
    public String showSubcategoryForm(Model model) {
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategory", new Subcategory());

        return "subcategorie";
    }

    @PostMapping("/subcategory")
    public String addSubcategory(@ModelAttribute Subcategory subcategory) {
        subcategoryService.saveSubcategory(subcategory);
        return "redirect:/subcategorie";
    }
}

@RestController
class SubcategoryAjaxController {

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("/fetchSubcategories")
    @ResponseBody
    public List<Subcategory> fetchSubcategories(@RequestParam("categoryId") Long categoryId) {
    	System.out.println("categoryId "+categoryId);
        return subcategoryService.getSubcategoriesByCategoryId(categoryId);
    }
}
