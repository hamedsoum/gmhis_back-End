package com.gmhis_backk.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Cashier;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, UUID> {
	
	
	@Query("SELECT c FROM Cashier c WHERE c.user.facilityId =:facility")
	Page<Cashier> findAll(@Param("facility") String facility,Pageable pageable);
}
