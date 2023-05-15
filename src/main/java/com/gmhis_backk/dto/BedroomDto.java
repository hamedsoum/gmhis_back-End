package com.gmhis_backk.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Mathurin
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedroomDto {
		
	private String libelle;
	
	private Long  storey;
	
	private Long bedroomType;
	
}
