package com.ncm.crud.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.crud.entity.Employee;
import com.ncm.crud.service.EmpService;

@Controller
public class UserController {

    @Autowired
    private EmpService service;

    @Value("${upload.dir}")
    private String uploadDir; 
    @GetMapping("/")
    public String home() {
        return "createaccount";
    }

    @PostMapping("/users")
    public String empRegister(@ModelAttribute Employee u, @RequestParam("image") MultipartFile image) {
        try {
            if (!image.isEmpty()) {
                // Ensure directory exists
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); 
                }
               // Generate a unique file name to avoid conflicts
                String originalFilename = image.getOriginalFilename();
                String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
                String imagePath = Paths.get(uploadDir, uniqueFilename).toString();
                
                // Save the image to the specified directory
                Path path = Paths.get(imagePath);
                Files.write(path, image.getBytes());
                
                // Store the relative path or URL in the employee record
                u.setImagePath(uniqueFilename);
        } 
        service.addEmp(u);
        
        }
        
        catch (IOException e) {
            e.printStackTrace(); // Consider adding user feedback here
        }
        
       
        return "redirect:/view";
    }

    @GetMapping("/view")
    public String viewEmployees(Model model) {
        model.addAttribute("employees", service.getAll());
        return "view";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        Optional<Employee> employeeOpt = service.getEmployeeById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            String imagePath = Paths.get(uploadDir, employee.getImagePath()).toString();
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            service.deleteEmployee(id);
        }
        return "redirect:/view";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Integer id, Model model) {
        Optional<Employee> employeeOpt = service.getEmployeeById(id);
        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            return "edit";
        } else {
            return "redirect:/view";
        }
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, @ModelAttribute Employee employee, @RequestParam("image") MultipartFile image) {
        Optional<Employee> existingEmployeeOpt = service.getEmployeeById(id);
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            try {
                if (!image.isEmpty()) {
                   
                    // Save the new image to the specified directory
                    String newImagePath = Paths.get(uploadDir, image.getOriginalFilename()).toString();
                    Path path = Paths.get(newImagePath);
                    Files.write(path, image.getBytes());

                    // Delete the old image file if it exists
                    if (existingEmployee.getImagePath() != null) {
                        String oldImagePath = Paths.get(uploadDir, existingEmployee.getImagePath()).toString();
                        File oldImageFile = new File(oldImagePath);
                        if (oldImageFile.exists()) {
                            oldImageFile.delete();
                        }
                    }

                    // Set the new image path in the Employee object
                    employee.setImagePath(image.getOriginalFilename());
                } else {
                    // Keep the old image path if no new image is uploaded
                    employee.setImagePath(existingEmployee.getImagePath());
                }
            } catch (IOException e) {
                e.printStackTrace(); 
            }

            employee.setId(id);
            service.addEmp(employee);
        }
        return "redirect:/view";
    }
}
