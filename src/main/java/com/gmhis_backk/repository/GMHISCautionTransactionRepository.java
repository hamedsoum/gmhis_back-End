package com.gmhis_backk.repository;

import com.gmhis_backk.domain.cautionTransaction.GMHISCautionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GMHISCautionTransactionRepository extends JpaRepository<GMHISCautionTransaction, Long> {

    @Query(value = "select c from GMHISCautionTransaction c where c.patient.id =:patientID")
    public List<GMHISCautionTransaction> findCautionTransactions(@Param("patientID") Long patientID);
}
