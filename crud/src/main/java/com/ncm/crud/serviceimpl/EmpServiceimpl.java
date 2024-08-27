package com.ncm.crud.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Employee;
import com.ncm.crud.repository.EmpRapo;
import com.ncm.crud.service.EmpService;
@Service
public class EmpServiceimpl implements EmpService {
	 @Autowired
	    private EmpRapo repo;

	@Override
	 public Employee addEmp(Employee employee) {
        return repo.save(employee);
    }

	@Override
	 public List<String> getAllEmployees() {
        return repo.findAllEmployeeFirstNames();
    }

	@Override
  public Optional<Employee> getEmployeeById(Integer id) {    	
        return repo.findById(id);
    }
	@Override
	public void deleteEmployee(Integer id) { 
        repo.deleteById(id);
    }

	@Override
	public Optional<Employee> findByFirstnameAndLastname(String firstname, String lastname) {
        return repo.findByFirstnameAndLastname(firstname, lastname);
    }

	@Override
	public Object getAll() {
	
		return repo.findAll();
	}
	 
	 
	 
	 
}
