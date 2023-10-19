package com.gmhis_backk.domain.quotation;

import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.quotation.item.GMHISQuotationItemCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISQuotationPartial {

    private UUID id;

    private String code;

    private String quotationNumber;

    private String InsuranceName;

    private  String affection;

    private  String indication;

    private Long InsuranceID;

    private GMHISName patientName;

    private Long patientID;

    private Double totalAmount;

    private Double moderatorTicket;

    private String status;

    private List<GMHISQuotationItemCreate> quotationItems;

    private Date dateOp;

}


