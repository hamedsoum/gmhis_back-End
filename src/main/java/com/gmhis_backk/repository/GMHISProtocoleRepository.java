package com.gmhis_backk.repository;

import com.gmhis_backk.domain.hospitalization.protocole.GMHISProtocole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GMHISProtocoleRepository extends JpaRepository<GMHISProtocole, UUID> {
    @Query(value = "select p from GMHISProtocole p where p.hospitalization.id = :hospitalizationID ORDER BY p.createdAt DESC")
    public List<GMHISProtocole> findProtocoles(@Param("hospitalizationID") UUID hospitalizationID);

}
