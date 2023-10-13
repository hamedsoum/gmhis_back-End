package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.quotation.GMHISQuotation;
import com.gmhis_backk.domain.quotation.GMHISQuotationCreate;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItem;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemCreate;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemPartial;
import com.gmhis_backk.repository.GMHISQuotationItemRepository;
import com.gmhis_backk.repository.GMHISQuotationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
@Log4j2
public class GMHISQuotationItemService {


    private final PracticianService practicianService;

    private final GMHISQuotationItemRepository quotationItemRepository;

    private  final GMHISQuotationRepository quotationRepository;

    private final UserService userService;

    public GMHISQuotationItemService(
            final PracticianService practicianService,
            final GMHISQuotationItemRepository quotationItemRepository,
            final GMHISQuotationRepository quotationRepository,
            final UserService userService
            ){
        this.practicianService = practicianService;
        this.quotationItemRepository = quotationItemRepository;
        this.quotationRepository = quotationRepository;
        this.userService = userService;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    private GMHISQuotationItemPartial toPartial(GMHISQuotationItem quotationItem) {
        GMHISQuotationItemPartial quotationItemPartial = new GMHISQuotationItemPartial();

        quotationItemPartial.setActID(quotationItem.getActId());
        quotationItemPartial.setActNumber(quotationItem.getActNumber());
        quotationItemPartial.setActCode(quotationItem.getActCode());
        quotationItemPartial.setQuantity(quotationItem.getQuantity());
        quotationItemPartial.setUnitPrice(quotationItem.getUnitPrice());
        quotationItemPartial.setTotalAmount(quotationItem.getTotalAmount());
        quotationItemPartial.setQuotationID(quotationItem.getQuotation().getId());
        quotationItemPartial.setModeratorTicket(quotationItem.getModeratorTicket());
        quotationItemPartial.setCmuAmount(quotationItem.getCmuAmount());
        quotationItemPartial.setCmuPercent(quotationItem.getCmuPercent());
        quotationItemPartial.setInsurancePercent(quotationItem.getInsurancePercent());
        quotationItemPartial.setPraticianName(new GMHISName(quotationItem.getPractician().getPrenoms(), quotationItem.getPractician().getNom()));
        quotationItemPartial.setPracticianID(quotationItem.getPractician().getId());

        return quotationItemPartial;
    }

    public GMHISQuotationItemPartial create (GMHISQuotationItemCreate quotationItemCreate) {
        GMHISQuotationItem quotationItem = new GMHISQuotationItem();

        Pratician practician = practicianService.findPracticianById(quotationItemCreate.getPracticianID()).orElse(null);
        if (practician != null) quotationItem.setPractician(practician);
        quotationItem.setCode("GMHIS-QUI-112233");
        GMHISQuotation quotation = quotationRepository.findById(quotationItemCreate.getQuotationID()).orElse(null);
        if (quotation != null) quotationItem.setQuotation(quotation);

        quotationItem.setCreatedAt(new Date());
        quotationItem.setCreatedBy(getCurrentUser().getId());
        BeanUtils.copyProperties(quotationItemCreate,quotationItem,"id");
        log.info("actNumber {}",quotationItem.getActNumber());
        return toPartial(quotationItemRepository.save(quotationItem));
    }
}
