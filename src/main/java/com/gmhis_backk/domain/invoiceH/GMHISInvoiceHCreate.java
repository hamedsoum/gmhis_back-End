package com.gmhis_backk.domain.invoiceH;


import com.gmhis_backk.domain.invoiceH.item.GMHISinvoiceHItemCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISInvoiceHCreate {
    private  String affection;

    private  String indication;

    private Long insuranceID;

    private Long patientID;

    private Double totalAmount;

    private Double moderatorTicket;

    private List<GMHISinvoiceHItemCreate> invoiceHItems;

    private Double cmuPart;

    private Double insurancePart;

    private Double netToPay;

    private Double discount;

}
