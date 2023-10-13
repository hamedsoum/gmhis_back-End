package com.gmhis_backk.domain.quotation;


import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationCreate {
    private  String affection;

    private  String indication;

    private Long InsuranceID;

    private Long PatientID;

    private Double totalAmount;

    private Double moderatorTicket;

    private List<GMHISQuotationItemCreate> quotationItems;

}
