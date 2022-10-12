package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Currency;

/**
 *
 * @author Mathurin
 *
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	@Query("SELECT c FROM Currency c")
	List<Currency> listAll();
}
