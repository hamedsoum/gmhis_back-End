package com.gmhis_backk.domain.quotation.item;

import com.gmhis_backk.domain.GMHISName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationItemPartial {

    private Long actID;

    private Double actCodeValue;

    private String actCode;

    private String actName;

    private int actCoefficient;

    private int quantity;

    private Double unitPrice;

    private Double totalAmount;

    private UUID quotationID;

    private Double moderatorTicket;

    private Double cmuAmount;

    private int cmuPercent;

    private int insurancePercent;

    private GMHISName praticianName;

    private  Long practicianID;

    private Date dateOp;
}
