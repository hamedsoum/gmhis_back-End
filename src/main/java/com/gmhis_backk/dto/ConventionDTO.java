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
public class ConventionDTO {

	private Long id;
	private String name;
	private Boolean active;
	private List<ConventionHasActDTO> acts;
	private List<ConventionHasActCodeDTO> actCodes;

}
