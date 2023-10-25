package com.gmhis_backk.repository;

import com.gmhis_backk.domain.invoiceH.GMHISInvoiceH;
import com.gmhis_backk.domain.quotation.GMHISQuotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GMHISInvoiceHRepository extends JpaRepository<GMHISInvoiceH, UUID> {
}
