package com.gmhis_backk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientConstantDomainDto {
	private Long id;
	private String name;
	private Boolean active;
	private String description;

}
