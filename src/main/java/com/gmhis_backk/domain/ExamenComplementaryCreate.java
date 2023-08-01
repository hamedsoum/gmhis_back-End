package com.gmhis_backk.domain;

import com.gmhis_backk.constant.ExamenComplementaryTypeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExamenComplementaryCreate {
	private Long actID;
	private ExamenComplementaryTypeEnum examen;
	private Boolean active;
}
