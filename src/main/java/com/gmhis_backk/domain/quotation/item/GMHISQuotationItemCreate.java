package com.gmhis_backk.domain.quotation.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationItemCreate {

    private Long actId;

    private String actCode;

    private int actNumber;

    private int quantity;

    private Double unitPrice;

    private Double totalAmount;

    private UUID quotationID;

    private Double moderatorTicket;

    private Double cmuAmount;

    private int cmuPercent;

    private int insurancePercent;

    private Long PracticianID;


}
