package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Insured;


@Repository
public interface InsuredRepository extends JpaRepository<Insured, Long> {
	
	
	@Query(value = "select i from Insured i where i.cardNumber = :cardNumber")
	public Insured findInsuredByCardNumber(@Param("cardNumber") String cardNumber);
	
	@Query(value = "select i from Insured i where i.active = 1")
	public List<Insured> findActiveInsureds();
	
	@Query(value = "select i from Insured i where i.active = :active")
	public Page<Insured> findByActive(@Param("active") String active , Pageable pageable);
	
	@Query(value = "select i from Insured i where i.insurance.id = :insurance")
	public Page<Insured> findInsuredByInsurance(@Param("insurance") Long insurance , Pageable pageable);
	
	@Query(value = "select i from Insured i where i.insuranceSuscriber.id = :subscriber ")
	public Page<Insured> findInsuredBySubscriber(@Param("subscriber") Long subscriber , Pageable pageable);
	
	@Query(value = "select i from Insured i where i.patient.id = :patient")
	public List<Insured> findInsuredByPatient(@Param("patient") Long patient);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from Insured  i where i.patient.id = :patient")
	public void deleteByPatientId(@Param("patient") Long patient);
	
	public List<Insured> findByCardNumber(String cardNumber);

	@Query(value = "SELECT i FROM Insured i where id= :id")
	Optional<Insured> findById(Long id);
}
