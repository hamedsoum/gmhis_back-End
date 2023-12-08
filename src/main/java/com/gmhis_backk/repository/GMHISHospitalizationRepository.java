package com.gmhis_backk.repository;

import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.hospitalization.GMHISHospitalization;
import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GMHISHospitalizationRepository extends JpaRepository<GMHISHospitalization, UUID> {
    @Query(value = "select h from GMHISHospitalization h where h.patient.id = :patientID")
    public Page<GMHISHospitalization> findHospitalizationBy(@Param("patientID") long patientID, Pageable p);
}
