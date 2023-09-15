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
	
	private Date startDate;
	
	private String service;
	
	private String practicianName;
	
	private String patientName;
	
	private String evacuationReason;
	
	private String clinicalInformation;
	
	private String treatmentReceived;
	
	private String receptionFacilityName;
}
