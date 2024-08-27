package com.ncm.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Employee;
import com.ncm.crud.repository.EmpRapo;

public interface EmpService {
	Employee addEmp(Employee employee);
	 List<String> getAllEmployees();
	 Optional<Employee> getEmployeeById(Integer id);
	 void deleteEmployee(Integer id);
	 Optional<Employee> findByFirstnameAndLastname(String firstname, String lastname);
	 Object getAll();
}
