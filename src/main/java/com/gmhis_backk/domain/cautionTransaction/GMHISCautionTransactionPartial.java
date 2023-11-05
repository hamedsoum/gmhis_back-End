package com.gmhis_backk.domain.cautionTransaction;

import com.gmhis_backk.domain.GMHISName;
import com.gmhis_backk.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GMHISCautionTransactionPartial {

    private Long id;

    private String libelle;

    private String action;

    private Double amount;

    private GMHISName patientName;
    private Long patientID;
    private Double patientAccountBalance;

    LocalDateTime date;
}
