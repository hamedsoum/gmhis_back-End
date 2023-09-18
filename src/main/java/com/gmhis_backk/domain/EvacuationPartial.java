package com.gmhis_backk.domain;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvacuationPartial {
	
	private UUID id;
	 
	private String evacuationFacilityName;
	private UUID evacuationFacilityID;

	
	private Date startDate;
	
	private String service;
	private Long serviceID;
	
	private String practicianName;
	private Long practicianID;
	
	private String patientName;
	private Long patientID;
	
	private String evacuationReason;
	
	private String clinicalInformation;
	
	private String treatmentReceived;
	
	private String receptionFacilityName;
	private UUID receptionFacilityID;

}
