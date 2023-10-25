package com.gmhis_backk.domain.invoiceH.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISinvoiceHItemCreate {

    private Long actId;

    private int quantity;

    private Double totalAmount;

    private Double moderatorTicket;

    private Double cmuAmount;

    private int cmuPercent;

    private int insurancePercent;

    private Long practicianID;


}
