package com.gmhis_backk.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvacuationPartial {

	private Facility evacuationFacility;
	
	private Date startDate;
	
	private ActCategory service;
	
	private Pratician practician;
	
	private Patient patient;
	
	private String evacuationReason;
	
	private String clinicalInformation;
	
	private String treatmentReceived;
	
	private Facility receptionFacility;
}
