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

	private String id;
	private String name;
	private Boolean active;
	private String dhisCode;
	private float latitude;
	private String localCode;
	private float longitude;
	private String shortName;
    private Long localityId;
    private String facilityCategoryId;
    private String facilityTypeId;
    private String logoId;
    private String address;
    private String contact;
    private String email;
}
