package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.invoiceH.item.GMHISInvoiceHItem;
import com.gmhis_backk.domain.invoiceH.item.GMHISInvoiceHItemPartial;
import com.gmhis_backk.domain.invoiceH.item.GMHISinvoiceHItemCreate;
import com.gmhis_backk.repository.GMHISInvoiceHItemRepository;
import com.gmhis_backk.repository.GMHISInvoiceHRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@Log4j2
public class GMHISInvoiceHItemService {


    private final ActService actService;
    private final PracticianService practicianService;

    private final GMHISInvoiceHItemRepository invoiceHItemRepository;

    private  final GMHISInvoiceHRepository invoiceRepository;

    private final UserService userService;

    public GMHISInvoiceHItemService(
            final PracticianService practicianService,
            final GMHISInvoiceHItemRepository invoiceHItemRepository,
            final GMHISInvoiceHRepository invoiceHRepository,
            final UserService userService,
            final ActService actService
            ){
        this.practicianService = practicianService;
        this.invoiceHItemRepository = invoiceHItemRepository;
        this.invoiceRepository = invoiceHRepository;
        this.userService = userService;
        this.actService = actService;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    private GMHISInvoiceHItemPartial toPartial(GMHISInvoiceHItem quotationItem) {
        GMHISInvoiceHItemPartial invoiceHItemPartial = new GMHISInvoiceHItemPartial();
        invoiceHItemPartial.setActCode(quotationItem.getActCode());
        invoiceHItemPartial.setDateOp(quotationItem.getCreatedAt());
        invoiceHItemPartial.setActCodeValue(quotationItem.getActCodeValue());
        invoiceHItemPartial.setActCoefficient(quotationItem.getActCoefficient());
        invoiceHItemPartial.setUnitPrice((double) (quotationItem.getActCoefficient() * quotationItem.getActCodeValue()));
        invoiceHItemPartial.setActID(quotationItem.getActId());

        actService.findActById(quotationItem.getActId()).ifPresent(act -> invoiceHItemPartial.setAct(actService.toPartial(act)));

        invoiceHItemPartial.setQuantity(quotationItem.getQuantity());
        invoiceHItemPartial.setTotalAmount(quotationItem.getTotalAmount());
        invoiceHItemPartial.setQuotationID(quotationItem.getInvoiceH().getId());
        invoiceHItemPartial.setModeratorTicket(quotationItem.getModeratorTicket());
        invoiceHItemPartial.setCmuAmount(quotationItem.getCmuAmount());
        invoiceHItemPartial.setCmuPercent(quotationItem.getCmuPercent());
        invoiceHItemPartial.setInsurancePercent(quotationItem.getInsurancePercent());

        if(quotationItem.getPracticianId() != null) {
           Pratician practician = practicianService.findPracticianById(quotationItem.getPracticianId()).orElse(null);
           if(practician != null)  {
               invoiceHItemPartial.setPraticianName(new GMHISName(practician.getPrenoms(), practician.getNom()));
               invoiceHItemPartial.setPracticianID(practician.getId());
           }
        }

        return invoiceHItemPartial;
    }

    public GMHISInvoiceHItemPartial create (GMHISinvoiceHItemCreate invoiceHItemCreate, UUID invoiceHID ) {
        GMHISInvoiceHItem invoiceItem = new GMHISInvoiceHItem();
        if (invoiceHItemCreate.getPracticianID() != null) invoiceItem.setPracticianId(invoiceHItemCreate.getPracticianID());

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        invoiceItem.setCode("GMHIS-IVH-" + n);

        invoiceRepository.findById(invoiceHID).ifPresent(invoiceItem::setInvoiceH);

        Act act = actService.findActById(invoiceHItemCreate.getActId()).orElse(null);
        if(act != null) {
            invoiceItem.setActCoefficient(act.getCoefficient());
            invoiceItem.setActCodeId(act.getActCode().getId());
            invoiceItem.setActCodeValue((double) (act.getActCode().getValue()));
            invoiceItem.setActCode(act.getCodification());

        }
        invoiceItem.setCreatedAt(new Date());
        invoiceItem.setCreatedBy(getCurrentUser().getId());
        BeanUtils.copyProperties(invoiceHItemCreate,invoiceItem,"id");
        return toPartial(invoiceHItemRepository.save(invoiceItem));
    }

    public List<GMHISInvoiceHItemPartial> findByQuotationID(UUID invoiceHID) {
        List<GMHISInvoiceHItemPartial> invoiceHItemsPartial =  new ArrayList<>();;
        List<GMHISInvoiceHItem> invoiceHItems = invoiceHItemRepository.invoiceHItemsByInvoiceH(invoiceHID);

        for (GMHISInvoiceHItem invoiceHItem : invoiceHItems) {
            invoiceHItemsPartial.add(toPartial(invoiceHItem));
        }

        return invoiceHItemsPartial;
    }
}
