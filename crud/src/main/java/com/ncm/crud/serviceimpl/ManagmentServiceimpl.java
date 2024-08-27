package com.ncm.crud.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.crud.entity.Managment;
import com.ncm.crud.repository.ManagmentRapo;
import com.ncm.crud.service.ManagmentService;
@Service
public class ManagmentServiceimpl implements ManagmentService  {
	@Autowired
	  private ManagmentRapo managerepo;

	@Override
	 public Managment saveAsset(Managment manage) {
        return managerepo.save(manage);
    }


	@Override
	public List<Managment> getAllItems() {
		  return managerepo.findAll();
	}

	@Override
	public void deleteItem(Long id) {
		managerepo.deleteById(id);
	}

	@Override
	public void saveAll(List<Managment> manages) {
		 managerepo.saveAll(manages);
		
	}
}
