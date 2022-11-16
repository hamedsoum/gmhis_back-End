package com.gmhis_backk.dto;


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
public class FacilityDTO {

	private Long id;
	private String name;
	private String active;
	private String dhisCode;
	private float latitude;
	private String localCode;
	private float longitude;
	private String shortName;
    private Long localityId;

}
