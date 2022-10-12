package com.gmhis_backk.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbonnementDto {
  
    private Date debut;
	
	private Date fin;
	
	private Long hotel;
   
}   