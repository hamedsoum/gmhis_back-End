package com.gmhis_backk.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.BillHasInsured;

@Repository
public interface BillHasInsuredRepository extends JpaRepository<BillHasInsured, Long> {
	


	@Query(value = "SELECT * FROM bill_has_insured WHERE admission_id IN ( SELECT admission_id FROM examination )", nativeQuery = true)
	public Page<BillHasInsured> findBillsHasInsured(Pageable pageable);

	@Query(value = "SELECT b FROM BillHasInsured b WHERE b.insurance.id = :insurance")
	public Page<BillHasInsured> findBillsHasInsuredByInsurance(@Param("insurance") Long insurance, Pageable pageable);
	
	@Query(value = "SELECT b FROM BillHasInsured b WHERE b.createdAt BETWEEN :start AND :end")
	Page<BillHasInsured> findByDate(Date start, Date end, Pageable pageable);
	
	@Query(value = "SELECT b FROM BillHasInsured b WHERE b.insurance.id = :insurance AND b.createdAt BETWEEN :start AND :end")
	Page<BillHasInsured> findByBillhaInsuredInsuranceiDAndDate(@Param("insurance") Long insurance, Date start, Date end, Pageable pageable);
}
