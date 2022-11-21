package com.gmhis_backk.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItemDto {
	private Long id;

	private String dosage;

	private short quantity;
	
	private UUID drug;
	
    private Boolean collected;
	
	private String duration;

}
