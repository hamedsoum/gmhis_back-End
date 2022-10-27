package com.gmhis_backk.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.UserHasCashRegister;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface UserHasCashRegisterRepository extends JpaRepository<UserHasCashRegister, Long> {
	
	@Query(value = "select c from UserHasCashRegister c join c.user u where u.id=:user and c.active = 'Y'")
	public UserHasCashRegister findByUser(@Param("user") Long user);
	
	
	@Query(value = "select c from UserHasCashRegister c  where c.user.id=:user and c.cashRegister.id= :cashRegister ")
	public UserHasCashRegister findByUserAndCashRegister(Long user, Long cashRegister);
	
	@Query(value = "select c from UserHasCashRegister c where active = 'Y'")
	public List<UserHasCashRegister> findActiveCashiers();
	
	@Modifying
	@Transactional
	@Query(value = "update UserHasCashRegister c set c.active = 'N' where c.user.id = :userId")
	public void desactivateAllByUser(Long userId);
	
	@Query(value = "select c from UserHasCashRegister c where c.user.firstName like %:firstName%  and c.user.lastName like %:lastName% and c.user.tel like %:tel%")
	public Page<UserHasCashRegister> findCashiers(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("tel") String tel, Pageable p);
	
	@Query(value = "select c from UserHasCashRegister c where c.user.firstName like %:firstName%  and c.user.lastName like %:lastName% and c.user.tel like %:tel% and c.active = :active")
	public Page<UserHasCashRegister> findByActive(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("tel") String tel, @Param("active") String active, Pageable p);
	
	@Query(value = "select c from UserHasCashRegister c where c.user.firstName like %:firstName%  and c.user.lastName like %:lastName% and c.user.tel like %:tel% and c.cashRegister.id = :cashRegister")
	public Page<UserHasCashRegister> findByCashRegister(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("tel") String tel, @Param("cashRegister") Long cashRegister, Pageable p);
	
	
	@Query(value = "select c from UserHasCashRegister c where c.user.firstName like %:firstName%  and c.user.lastName like %:lastName% and c.user.tel like %:tel% and c.active = :active and c.cashRegister.id = :cashRegister")
		public Page<UserHasCashRegister> findCashierByAllFilters (@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("tel") String tel, @Param("active") String active, @Param("cashRegister") Long cashRegister, Pageable pageable);
			
}
