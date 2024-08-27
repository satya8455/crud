package com.ncm.crud.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncm.crud.entity.Category;
import com.ncm.crud.entity.Managment;
import com.ncm.crud.entity.Subcategory;
import com.ncm.crud.service.AssetsService;
import com.ncm.crud.service.CategoryService;
import com.ncm.crud.service.EmpService;
import com.ncm.crud.service.ManagmentService;
import com.ncm.crud.service.SubcategoryService;

@Controller
public class ManagmentController {

   
    private final AssetsService assetsService;

    

	public ManagmentController(AssetsService assetsService) {
		
		this.assetsService = assetsService;
	}


	@Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private ManagmentService manageService;

    @Autowired
    private EmpService empService;

    @GetMapping("/management")
    public String showForm(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        if (categoryId != null) {
            model.addAttribute("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
        }
        model.addAttribute("selectedCategoryId", categoryId);

        return "managment"; 
    }

    @PostMapping("/managment")
    public String saveAsset(@RequestParam("category.id") Long categoryId,
                            @RequestParam("subcategory.id") Long subcategoryId,
                            @RequestParam("companyname") String companyName,
                            @RequestParam("slNo") String serialNumber,
                            @RequestParam("employeeName") String employeeName,
                            @RequestParam("quantity") Integer quantity,
                            @RequestParam("date") LocalDate date,
                            Model model) {

        Category category = categoryService.getCategoryById(categoryId);
        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);

        // Check if category and subcategory are valid
        if (category == null || subcategory == null) {
            model.addAttribute("error", "Invalid category or subcategory.");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
            model.addAttribute("selectedCategoryId", categoryId);
            return "management"; // Ensure this matches your view template name
        }

        Managment asset = new Managment();
        asset.setCategoryName(category.getName());
        asset.setSubcategoryName(subcategory.getSub_name());
        asset.setCompanyName(companyName);
        asset.setSerialNumber(serialNumber);
        asset.setEmployeeName(employeeName); 
        asset.setQuantity(quantity.toString());
        asset.setDate(date);

        manageService.saveAsset(asset);
        model.addAttribute("message", "Asset saved successfully!");
        return "redirect:/viewmanagment";
    }

    @GetMapping("/fetchSerialNumbers")
    @ResponseBody
    public List<String> fetchSerialNumbers() {
        return assetsService.getAllSerialNumbers();
    }

    @GetMapping("/fetchCompanyNames")
    @ResponseBody
    public List<String> fetchCompanyNames() {
        List<String> companyNames = assetsService.getAllCompnay(); 
        if (companyNames == null) {
            return Collections.emptyList();
        }
        return companyNames;
    }

    @GetMapping("/fetchEmployees")
    @ResponseBody
    public List<String> fetchEmployees() {
        List<String> employees = empService.getAllEmployees();
        if (employees == null) {
            return Collections.emptyList();
        }
        return employees;
    }

    @GetMapping("/viewmanagment")
    public String viewManagement(Model model) {
        List<Managment> managementItems = manageService.getAllItems();
        model.addAttribute("managementItems", managementItems);
        return "viewmanagment";
    }


    @PostMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        manageService.deleteItem(id);
        redirectAttributes.addFlashAttribute("message", "Item deleted successfully!");
        return "redirect:/viewmanagment";
    }
}
