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
public class WaitingRoomDTO {

	private Long id;
	private String name;
	private int capacity;
	private Boolean active;
}
