package com.gmhis_backk.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class deathCreate {
		
	private Date deathDate;

	private String deathReason;
	
	private Date deathDeclarationDate;
	
	private Long deathDeclarationByID;
	
	private Long patientID;
}
