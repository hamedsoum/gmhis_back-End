package com.gmhis_backk.domain.hospitalization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISHospitalizationCreate {
    private Long patientID;

    private Long practicianID;

    private String reason;

    private String bedroom;

    private Date start;

    private Date end;

    private String protocole;

    private  String conclusion;

    private Long nurse;
}
