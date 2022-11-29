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
public class AnalysisRequestDTO {

	private Long id;
	private Long admission;
//	private Long practicien;
//	private String analysis; 
//	private String otherAnalysis;
	private String observation;
	private String diagnostic;
	private List<Long> acts;
}
