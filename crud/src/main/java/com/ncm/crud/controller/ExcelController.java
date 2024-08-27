package com.ncm.crud.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ExcelController {

    @GetMapping("/api/downloadExcel")
    public StreamingResponseBody downloadExcel(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String employeename,
            HttpServletResponse response) {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sample.xlsx");

        return outputStream -> {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("HR Data");

                // Convert strings to LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate start = LocalDate.parse(startDate, formatter);
                LocalDate end = LocalDate.parse(endDate, formatter);

                // Create header row
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Employee Name");

                // Create columns for each date
                LocalDate currentDate = start;
                int columnIndex = 1;
                while (!currentDate.isAfter(end)) {
                    header.createCell(columnIndex++).setCellValue(currentDate.format(formatter));
                    currentDate = currentDate.plusDays(1);
                }

                // Add a row with example data
                Row dataRow = sheet.createRow(1);
                dataRow.createCell(0).setCellValue(employeename);

                // Add empty cells for each date
                for (int i = 1; i < columnIndex; i++) {
                    dataRow.createCell(i).setCellValue("");
                }

                // Write the output to the response
                try (OutputStream out = response.getOutputStream()) {
                    workbook.write(out);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to generate Excel file", e);
            }
        };
    }
}
