package com.gmhis_backk.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmhis_backk.domain.PaymentHasPaymentType;

public interface PaymentHasPaymentTypeRepository extends JpaRepository<PaymentHasPaymentType, UUID> {
	
	@Query(value = "SELECT p FROM PaymentHasPaymentType p")
	Page<PaymentHasPaymentType>findAllPaymentHasPaymentType(Pageable pageable);
	
	@Query(value = "SELECT p FROM PaymentHasPaymentType p where p.id= :id")
	Optional<PaymentHasPaymentType> findById(String id);

}
