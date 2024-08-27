package com.ncm.crud.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncm.crud.entity.Assets;

@Repository
public interface AssetsRapo extends JpaRepository<Assets, Integer> {
	  @Query("SELECT a.companyname FROM Assets a")
	List<String> findAllCompanyName();

    // Use the entity name and field name in the JPQL query
  @Query("SELECT a.slNo FROM Assets a")
    List<String> findAllSerialNumbers();

  @Query("SELECT a FROM Assets a WHERE a.categoryName = :category AND a.subcategoryName = :subcategory AND a.companyname = :companyName AND a.slNo = :serialNumber")
  Optional<Assets> findByCategoryAndSubcategoryAndCompanyNameAndSerialNumber(
          @Param("category") String category,
          @Param("subcategory") String subcategory,
          @Param("companyName") String companyName,
          @Param("serialNumber") String serialNumber);
  }
