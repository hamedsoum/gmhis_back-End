package com.gmhis_backk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GMHISHospitalizationRequestCreate {
    private Long examinationID;

    private Long admissionID;

    private Long patientID;

    private String reason;

    private int dayNumber;

    private Date startDate;
}
