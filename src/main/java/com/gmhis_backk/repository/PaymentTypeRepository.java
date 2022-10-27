package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.PaymentType;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

public PaymentType findByName(String name);
	
	@Query(value = "select p from PaymentType p where name = :name and value = :value")
	public PaymentType findPaymentTypeByNameAndValue(String name, int value);
 
	@Query(value = "select p from PaymentType p where active = 1")
	public List<PaymentType> findActivePaymentTypes();

	public Page<PaymentType> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Query(value = "select p from PaymentType p where p.name like %:name% and p.active = :active")
	public Page<PaymentType> findByActive(@Param("name") String name,
			@Param("active") Boolean active, Pageable p);
}
