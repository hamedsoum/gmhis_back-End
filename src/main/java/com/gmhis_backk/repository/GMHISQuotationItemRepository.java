package com.gmhis_backk.repository;

import com.gmhis_backk.domain.quotation.GMHISQuotation;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GMHISQuotationItemRepository extends JpaRepository<GMHISQuotationItem, UUID> {
}
