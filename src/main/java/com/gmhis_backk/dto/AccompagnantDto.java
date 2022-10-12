package com.gmhis_backk.dto;
import java.util.Date;

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
@Setter
@Getter
public class AccompagnantDto {

    private Long id;
     
	private String nom;
	
	private String prenoms;
	
	private Date dateNaissance;
	
	private Integer type;
	
	
}
