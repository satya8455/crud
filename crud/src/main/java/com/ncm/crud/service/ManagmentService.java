package com.ncm.crud.service;

import java.util.List;

import com.ncm.crud.entity.Managment;

public interface ManagmentService {

	public Managment saveAsset(Managment manage);

	public List<Managment> getAllItems();

	public void deleteItem(Long id);

	public void saveAll(List<Managment> manages);

}
