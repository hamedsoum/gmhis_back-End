package com.gmhis_backk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
	
	private Long examinationId;
	private String observation;
	private Long patientID;
	private List<PrescriptionItemDto> prescriptionItemsDto;

}
