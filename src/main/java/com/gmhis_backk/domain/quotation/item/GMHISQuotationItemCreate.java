package com.gmhis_backk.domain.quotation.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationItemCreate {

    private Long actId;

    private int quantity;

    private Double totalAmount;

    private Double moderatorTicket;

    private Double cmuAmount;

    private int cmuPercent;

    private int insurancePercent;

    private Long practicianID;


}
