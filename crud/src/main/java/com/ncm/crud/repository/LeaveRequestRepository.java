package com.ncm.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ncm.crud.entity.LeaveRequest;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {







	@Query("SELECT h FROM LeaveRequest h WHERE h.startDate BETWEEN :start AND :end AND h.employeename = :employee")
	List<LeaveRequest> findByDateRangeAndEmployee(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("employee") String employee);

	 // Correct the query for finding distinct statuses
    @Query("SELECT DISTINCT l.status FROM LeaveRequest l")
    List<String> findDistinctStatuses();

	boolean existsByEmployeenameAndStartDateAndStatus(String employeeName, LocalDate startDate, String status);



}
