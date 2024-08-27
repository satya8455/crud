package com.ncm.crud.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ncm.crud.entity.Managment;

public interface ManagmentRapo  extends JpaRepository<Managment, Long> {
	// @Query("SELECT e.employee_name FROM managment e")
	//List<String> findAllEmployeeFirstNames();

}
