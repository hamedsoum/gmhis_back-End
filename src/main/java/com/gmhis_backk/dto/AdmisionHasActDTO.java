package com.gmhis_backk.dto;

import java.util.Date;

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
public class AdmisionHasActDTO {

	 private long admission;
	 private long act;
	 private long practician;
}
