package com.ncm.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.LeaveRequest;
import com.ncm.crud.repository.LeaveRequestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;

	public void applyForLeave(LeaveRequest leaveRequest) {
		try {
			System.out.println(leaveRequest);
			leaveRequestRepository.save(leaveRequest);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			throw new RuntimeException("Failed to apply for leave", e);
		}
	}

	public List<LeaveRequest> getAllLeaveRequests() {
		// TODO Auto-generated method stub
		return leaveRequestRepository.findAll();
	}

	public List<LeaveRequest> getdataByDateRange(LocalDate start, LocalDate end, String employee) {
		// Check if the end date is after the start date
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("Start date must not be after end date.");
		}

		// Fetch data from the repository based on the date range and employee name
		return leaveRequestRepository.findByDateRangeAndEmployee(start, end, employee);
	}

	public boolean updateLeave(LeaveRequest leaveRequest) {
		Optional<LeaveRequest> existingLeaveRequest = leaveRequestRepository.findById(leaveRequest.getId());
		if (existingLeaveRequest.isPresent()) {
			LeaveRequest updatedLeaveRequest = existingLeaveRequest.get();

			// Update the properties as needed
			updatedLeaveRequest.setStatus(leaveRequest.getStatus());
			updatedLeaveRequest.setStartDate(leaveRequest.getStartDate());

			updatedLeaveRequest.setModifydate(leaveRequest.getModifydate());

			leaveRequestRepository.save(updatedLeaveRequest);
			return true;
		} else {
			return false;
		}
	}

	public LeaveRequest getLeaveRequestById(Long id) {
		Optional<LeaveRequest> leaveRequest = leaveRequestRepository.findById(id);
		if (leaveRequest.isPresent()) {
			return leaveRequest.get();
		} else {
			throw new RuntimeException("Leave request not found with id: " + id);
		}
	}
}
