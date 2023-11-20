package com.gmhis_backk.service;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.Act;
import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItem;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemCreate;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemPartial;
import com.gmhis_backk.repository.GMHISQuotationItemRepository;
import com.gmhis_backk.repository.GMHISQuotationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Transactional
@Service
@Log4j2
public class GMHISQuotationItemService {


    private final ActService actService;
    private final PracticianService practicianService;

    private final GMHISQuotationItemRepository quotationItemRepository;

    private  final GMHISQuotationRepository quotationRepository;

    private final UserService userService;

    public GMHISQuotationItemService(
            final PracticianService practicianService,
            final GMHISQuotationItemRepository quotationItemRepository,
            final GMHISQuotationRepository quotationRepository,
            final UserService userService,
            final ActService actService
            ){
        this.practicianService = practicianService;
        this.quotationItemRepository = quotationItemRepository;
        this.quotationRepository = quotationRepository;
        this.userService = userService;
        this.actService = actService;
    }

    protected User getCurrentUser() {
        return this.userService.findUserByUsername(AppUtils.getUsername());
    }

    private GMHISQuotationItemPartial toPartial(GMHISQuotationItem quotationItem) {
        GMHISQuotationItemPartial quotationItemPartial = new GMHISQuotationItemPartial();
        quotationItemPartial.setId(quotationItem.getId());
        quotationItemPartial.setActCode(quotationItem.getActCode());
        quotationItemPartial.setDateOp(quotationItem.getCreatedAt());
        quotationItemPartial.setActCodeValue(quotationItem.getActCodeValue());
        quotationItemPartial.setActCoefficient(quotationItem.getActCoefficient());
        quotationItemPartial.setUnitPrice((double) (quotationItem.getActCoefficient() * quotationItem.getActCodeValue()));
        quotationItemPartial.setActID(quotationItem.getActId());

        actService.findActById(quotationItem.getActId()).ifPresent(act -> quotationItemPartial.setAct(actService.toPartial(act)));

        quotationItemPartial.setQuantity(quotationItem.getQuantity());
        quotationItemPartial.setTotalAmount(quotationItem.getTotalAmount());
        quotationItemPartial.setQuotationID(quotationItem.getQuotation().getId());
        quotationItemPartial.setModeratorTicket(quotationItem.getModeratorTicket());
        quotationItemPartial.setCmuAmount(quotationItem.getCmuAmount());
        quotationItemPartial.setCmuPercent(quotationItem.getCmuPercent());
        quotationItemPartial.setInsurancePercent(quotationItem.getInsurancePercent());

        if(quotationItem.getPracticianId() != null) {
           Pratician practician = practicianService.findPracticianById(quotationItem.getPracticianId()).orElse(null);
           if(practician != null)  {
               quotationItemPartial.setPraticianName(new GMHISName(practician.getPrenoms(), practician.getNom()));
               quotationItemPartial.setPracticianID(practician.getId());
           }
        }

        return quotationItemPartial;
    }

    public GMHISQuotationItemPartial create (GMHISQuotationItemCreate quotationItemCreate, UUID quotationID ) {
        GMHISQuotationItem quotationItem = new GMHISQuotationItem();

        if (quotationItemCreate.getPracticianID() != null) quotationItem.setPracticianId(quotationItemCreate.getPracticianID());

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        quotationItem.setCode("GMHIS-QUI-" + n);

        quotationRepository.findById(quotationID).ifPresent(quotationItem::setQuotation);

        Act act = actService.findActById(quotationItemCreate.getActId()).orElse(null);
        if(act != null) {
            quotationItem.setActCoefficient(act.getCoefficient());
            quotationItem.setActCodeId(act.getActCode().getId());
            quotationItem.setActCodeValue((double) (act.getActCode().getValue()));
            quotationItem.setActCode(act.getCodification());

        }
        quotationItem.setCreatedAt(new Date());
        quotationItem.setCreatedBy(getCurrentUser().getId());
        BeanUtils.copyProperties(quotationItemCreate,quotationItem,"id");
        return toPartial(quotationItemRepository.save(quotationItem));
    }

    public List<GMHISQuotationItemPartial> findByQuotationID(UUID quotationID) {
        List<GMHISQuotationItemPartial> quotationItemsPartial =  new ArrayList<>();;
        List<GMHISQuotationItem> quotationItems = quotationItemRepository.quotationItemsByQuotation(quotationID);
        for (GMHISQuotationItem quotationItem : quotationItems) {
            quotationItemsPartial.add(toPartial(quotationItem));
        }

        return quotationItemsPartial;
    }

    public void deleteAll(List<GMHISQuotationItemPartial> quotationItem) {
        quotationItem.forEach( item -> {
            quotationItemRepository.deleteById(item.getId());
        });
    }
}
