package com.ncm.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.ncm.crud.entity.LeaveRequest;
import com.ncm.crud.service.EmpService;
import com.ncm.crud.service.LeaveRequestService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api")
public class Hrcontroller {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private EmpService employeeService;

    @GetMapping("/hr")
    public String showApplyLeaveForm(Model model) {
        return "hr";
    }

    // For fetching the employee
    @GetMapping("/fetchEmployees")
    @ResponseBody
    public List<String> fetchEmployees() {
        List<String> employees = employeeService.getAllEmployees();
        if (employees == null) {
            return Collections.emptyList();
        }   
        return employees;
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        try {
            LocalDate now = LocalDate.now();
            leaveRequest.setModifydate(now);
            leaveRequestService.applyForLeave(leaveRequest);
            return ResponseEntity.ok("Leave applied successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to apply for leave");
        }
    }

    @GetMapping("/viewLeaves")
    public String viewLeavesPage(Model model) {
        return "viewreport";
    }

    @GetMapping("/fetchhr")
    @ResponseBody
    public ResponseEntity<List<LeaveRequest>> fetchHr(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr,
            @RequestParam("employeename") String employee) {

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            if (startDate.isAfter(endDate)) {
                return ResponseEntity.badRequest().body(null);
            }

            List<LeaveRequest> hrModules = leaveRequestService.getdataByDateRange(startDate, endDate, employee);

            return ResponseEntity.ok(hrModules);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/editLeave")
    public String editLeavePage(@RequestParam("id") Long id, Model model) {
        try {
            LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
            model.addAttribute("leaveRequest", leaveRequest);
            return "editLeave"; 
        } catch (Exception e) {
            model.addAttribute("error", "Leave request not found.");
            return "error";
        }
    }

    @PostMapping("/updateLeave")
    public ResponseEntity<String> updateLeave(@RequestBody LeaveRequest leaveRequest) {
        try {
            if (leaveRequest.getId() == null) {
                return ResponseEntity.badRequest().body("Leave request ID is missing");
            }
         
            leaveRequest.setModifydate(LocalDate.now());

        
            boolean updated = leaveRequestService.updateLeave(leaveRequest);
            if (updated) {
                return ResponseEntity.ok("Leave request updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("update request not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update leave request");
        }
    }
}
