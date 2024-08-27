package com.ncm.crud.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncm.crud.entity.Assets;
import com.ncm.crud.entity.Managment;
import com.ncm.crud.service.AssetsService;
import com.ncm.crud.service.ManagmentService;

@Controller
public class FileUploadController {

    @Autowired
    private ManagmentService manageService;

    @Autowired
    private AssetsService assetsService;

    // Define the date formatter for the 'dd-MM-yyyy' format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @PostMapping("/uploadCsv")  
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No file selected.");
            return "redirect:/managment";
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            redirectAttributes.addFlashAttribute("message", "Invalid file type. Please upload a CSV file.");
            return "redirect:/managment";
        }

        List<Managment> validManages = new ArrayList<>();
        List<String> invalidRecords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 6) {
                    invalidRecords.add(line); 
                    continue; 
                }

                try {
                    String category = values[0].trim();
                    String companyName = values[1].trim();
                    LocalDate date = LocalDate.parse(values[2].trim(), DATE_FORMATTER);
                    String employeeName = values[3].trim();
                    String quantity = values[4].trim();
                    String serialNumber = values[5].trim();
                    String subcategory = values.length > 6 ? values[6].trim() : "";

                    // Validate if the data exists in the Assets table
                    Optional<Assets> asset = assetsService.findByCategoryAndSubcategoryAndCompanyNameAndSerialNumber(category, subcategory, companyName, serialNumber);

                    if (asset.isPresent()) {
                        Managment manage = new Managment();
                        manage.setCategoryName(category);
                        manage.setSubcategoryName(subcategory);
                        manage.setCompanyName(companyName);
                        manage.setSerialNumber(serialNumber);
                        manage.setEmployeeName(employeeName);
                        manage.setQuantity(quantity);
                        manage.setDate(date);
                        validManages.add(manage);
                    } else {
                        invalidRecords.add(line); 
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    invalidRecords.add(line);
                }
            }

            // Save valid records
            if (!validManages.isEmpty()) {
                manageService.saveAll(validManages);
            }

            // Set messages for the redirect
            if (!invalidRecords.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "File processed with some invalid records.");
                redirectAttributes.addFlashAttribute("invalidRecords", invalidRecords);
            } else {
                redirectAttributes.addFlashAttribute("message", "File processed successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to process the file.");
        }

        return "redirect:/managment";
    }
}
