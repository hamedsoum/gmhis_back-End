package com.gmhis_backk.repository;

import com.gmhis_backk.domain.hospitalization.request.GMHISHospitalizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GMHISHospitalizationRequestRepository extends JpaRepository<GMHISHospitalizationRequest, UUID> {
}
