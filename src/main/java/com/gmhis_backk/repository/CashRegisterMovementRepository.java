package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gmhis_backk.domain.CashRegisterMovement;

public interface CashRegisterMovementRepository extends JpaRepository<CashRegisterMovement, UUID> {
	
	@Query(value="SELECT c FROM CashRegisterMovement c WHERE c.prestationNumber  LIKE %:prestationNumber% ")
	public Page<CashRegisterMovement> getCaPageByPrestationNumber(@Param("prestationNumber") String prestationNumber, Pageable page );
	
	@Query(value="SELECT c FROM CashRegisterMovement c WHERE c.cashRegister.id =:cashRegister")
	public Page<CashRegisterMovement> getCaPageByCashRegister(@Param("cashRegister") Long cashRegister, Pageable page );
	
	@Query(value="SELECT c FROM CashRegisterMovement c WHERE c.user.id =:user ORDER BY c.date DESC")
	public Page<CashRegisterMovement> getCaPageByUser(@Param("user") Long user, Pageable page );
	
	@Query(value="SELECT c FROM CashRegisterMovement c ORDER BY c.date DESC")
	public Page<CashRegisterMovement> getCas( Pageable page );
	
	@Query(value="SELECT c FROM CashRegisterMovement c WHERE c.cashRegister.id =:cashRegister AND c.prestationNumber =:prestationNumber")
	public Page<CashRegisterMovement> getCaPageByCashRegisterAndPrestationNumber(@Param("cashRegister") Long cashRegister,@Param("prestationNumber") String prestationNumber, Pageable page );
	
}
