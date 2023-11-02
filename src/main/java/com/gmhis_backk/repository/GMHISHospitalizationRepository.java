package com.gmhis_backk.repository;

import com.gmhis_backk.domain.hospitalization.GMHISHospitalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GMHISHospitalizationRepository extends JpaRepository<GMHISHospitalization, UUID> {
}
