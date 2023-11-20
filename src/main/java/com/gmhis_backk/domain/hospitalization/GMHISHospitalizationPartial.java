package com.gmhis_backk.domain.hospitalization;

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
public class GMHISHospitalizationPartial {
    private UUID id;

    private String code;

    private GMHISName patientName;

    private GMHISName practicianName;

    private String reason;

    private String bedroom;

    private Date start;

    private Date end;

    private String protocole;

    private  String conclusion;

    private  String status;

    private GMHISName nurseName;

}
