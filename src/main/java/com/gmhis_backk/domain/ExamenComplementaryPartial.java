package com.gmhis_backk.domain;

import java.util.UUID;

import com.gmhis_backk.constant.ExamenComplementaryTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExamenComplementaryPartial {
	private UUID id;
	
	private String name;
	
	private Long actID;
	
	private Boolean Active;
	
	private ExamenComplementaryTypeEnum examenComplementaryType;

}
