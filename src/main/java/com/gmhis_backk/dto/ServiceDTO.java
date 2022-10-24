package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
	
	private Long id;
	private String name;
	private Boolean active;
	private Long waitingRoom;
	private Long facilityId;

}
