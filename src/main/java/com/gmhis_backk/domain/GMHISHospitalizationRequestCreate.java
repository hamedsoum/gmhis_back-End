package com.gmhis_backk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISHospitalizationRequestCreate {
    private Long PraticianID;

    private Long PatientID;

    private String reason;

    private int dayNumber;
}
