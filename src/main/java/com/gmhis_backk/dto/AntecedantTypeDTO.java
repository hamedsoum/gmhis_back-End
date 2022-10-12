package com.gmhis_backk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Adjara
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AntecedantTypeDTO {

	private Long id;
	private String name;
	private String description;
	private String active;

}
