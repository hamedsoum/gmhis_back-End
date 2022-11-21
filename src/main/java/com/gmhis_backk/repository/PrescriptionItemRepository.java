package com.gmhis_backk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.PrescriptionItem;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, UUID> {
	
	@Query(value = "select p from PrescriptionItem p where p.prescription.id = :prescription")
	public List<PrescriptionItem> findPrescritionItemByPrescription(@Param("prescription") UUID prescription);

}
