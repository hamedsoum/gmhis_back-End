package com.gmhis_backk.repository;

import com.gmhis_backk.domain.quotation.GMHISQuotation;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GMHISQuotationItemRepository extends JpaRepository<GMHISQuotationItem, UUID> {
    @Query(value = "select q from GMHISQuotationItem q where q.quotation.id =:quotationID")
    List<GMHISQuotationItem> quotationItemsByQuotation(@Param("quotationID") UUID quotationID);

}
