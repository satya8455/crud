package com.ncm.crud.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ncm.crud.entity.LeaveRequest;
import com.ncm.crud.repository.LeaveRequestRepository;
import com.ncm.crud.repository.EmpRapo;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("/api")
public class ExcelFileUploadController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository; 

    @Autowired
    private EmpRapo employeeRepository;

    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No file selected.");
            return "No file selected.";
        }

        // Fetch valid employee names from EmployeeRepository
        List<String> validEmployeeNames = employeeRepository.findAllEmployeeFirstNames(); 
        Set<String> validEmployeeNameSet = new HashSet<>(validEmployeeNames);

        // Fetch valid statuses from LeaveRequestRepository
        List<String> validStatuses = leaveRequestRepository.findDistinctStatuses();
        Set<String> validStatusSet = new HashSet<>(validStatuses);

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Read header row (first row) for dates
            Row headerRow = rowIterator.next();
            int numCols = headerRow.getPhysicalNumberOfCells();

            // Update the date format to match the format in your Excel file
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Process each row starting from the second row (data rows)
            while (rowIterator.hasNext()) {
                Row dataRow = rowIterator.next();
                String employeeName = getCellValueAsString(dataRow.getCell(0));

                if (employeeName == null || employeeName.isEmpty() || !validEmployeeNameSet.contains(employeeName)) {
                    System.err.println("Invalid employee name: " + employeeName);
                    continue; 
                }

                // Process each date and status column
                for (int col = 1; col < numCols; col++) {
                    // Read the date from the header row
                    String dateStr = getCellValueAsString(headerRow.getCell(col));
                    String status = getCellValueAsString(dataRow.getCell(col));

                    if (status == null || status.isEmpty() || !validStatusSet.contains(status)) {
                        System.err.println("Invalid status: " + status);
                        continue; 
                    }

                    LocalDate date;
                    try {
                        date = LocalDate.parse(dateStr, dateFormatter);
                    } catch (Exception e) {
                        System.err.println("Date parsing failed for value: " + dateStr + " Error: " + e.getMessage());
                        continue;
                    }

                    // Check if the leave request data already exists
                    if (leaveRequestRepository.existsByEmployeenameAndStartDateAndStatus(employeeName, date, status)) {
                        System.err.println("Leave request already exists for employee: " + employeeName + ", date: " + date + ", status: " + status);
                        continue; 
                    }

                    LeaveRequest leaveRequest = new LeaveRequest();
                    leaveRequest.setEmployeename(employeeName);
                    leaveRequest.setStartDate(date);
                    leaveRequest.setStatus(status);
                    leaveRequest.setModifydate(LocalDate.now()); 

                    System.out.println("Saving LeaveRequest: " + leaveRequest);

                    leaveRequestRepository.save(leaveRequest);
                }
            }

            return "File uploaded and data saved successfully";

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file. Error: " + e.getMessage();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Convert date to LocalDate
                    return cell.getDateCellValue().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            default:
                return cell.toString();
        }
    }
}
