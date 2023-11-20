package com.gmhis_backk.domain.quotation;


import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationCreate {
    private  String affection;

    private  String indication;

    private Long insuranceID;

    private Long patientID;

    private Double totalAmount;

    private Double moderatorTicket;

    private Double discount;

    private List<GMHISQuotationItemCreate> quotationItems;

    private Double cmuPart;

    private Double insurancePart;

    private Double netToPay;

}
