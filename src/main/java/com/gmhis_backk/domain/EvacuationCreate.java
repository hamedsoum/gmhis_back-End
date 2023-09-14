package com.gmhis_backk.domain;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvacuationCreate {
	
	private UUID evacuationFacilityID;
	private Date startDate;
	private Long serviceID;
	private Long PracticianID;
	private Long PatientID;
	private String evacuationReason;
	private String clinicalInformation;
	private String treatmentReceived;
	private UUID receptionFacilityID;
}
