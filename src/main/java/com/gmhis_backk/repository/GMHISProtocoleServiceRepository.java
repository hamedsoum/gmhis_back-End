package com.gmhis_backk.repository;

import com.gmhis_backk.domain.hospitalization.protocole.service.GMHISProtocoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GMHISProtocoleServiceRepository extends JpaRepository<GMHISProtocoleService, UUID> {
    @Query(value = "select s from GMHISProtocoleService s where s.protocole.id = :hospitalizationID ORDER BY s.createdAt DESC")
    public List<GMHISProtocoleService> findServices(@Param("hospitalizationID") UUID hospitalizationID);
}
