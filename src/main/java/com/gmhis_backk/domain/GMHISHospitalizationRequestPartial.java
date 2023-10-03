package com.gmhis_backk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISHospitalizationRequestPartial {
    private UUID id;

    private String code;

    private GMHISName praticianName;

    private GMHISName patientName;

    private String reason;

    private int dayNumber;
}
