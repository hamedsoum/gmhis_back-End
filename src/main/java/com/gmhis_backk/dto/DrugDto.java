package com.gmhis_backk.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugDto {
	private String id;
	private String name;
	private Boolean active;
	private Double drugPrice;
	private UUID drugDci;
	private UUID drugPharmacologicalForm;
	private UUID drugTherapeuticClass;
	private String dosage;

}
