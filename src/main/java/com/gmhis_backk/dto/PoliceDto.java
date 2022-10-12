package com.gmhis_backk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Mathurin
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PoliceDto {

    private String nom;
	
	private Long localisation;
	
	private String telephone;
	
	private String email;
	
}
