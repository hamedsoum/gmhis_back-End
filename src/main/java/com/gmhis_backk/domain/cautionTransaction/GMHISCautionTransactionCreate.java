package com.gmhis_backk.domain.cautionTransaction;

import com.gmhis_backk.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GMHISCautionTransactionCreate {

    private String libelle;

    private String action;

    private Double amount;

    private Long patientID;
}
