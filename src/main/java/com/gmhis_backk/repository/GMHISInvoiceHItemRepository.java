package com.gmhis_backk.repository;

import com.gmhis_backk.domain.invoiceH.item.GMHISInvoiceHItem;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GMHISInvoiceHItemRepository extends JpaRepository<GMHISInvoiceHItem, UUID> {
    @Query(value = "select i from GMHISInvoiceHItem i where i.invoiceH.id =:invoiceHID")
    List<GMHISInvoiceHItem> invoiceHItemsByInvoiceH(@Param("invoiceHID") UUID invoiceHID);

}
