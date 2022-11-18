package com.gmhis_backk.dto;

import java.util.Date;
import java.util.List;

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
public class ExaminationDTO {

	private Long id;
	private String conclusion;
	private Date endDate;
	private String examinationType;
//	private Long facility;
	private String history;
	private Date startDate;
	private Long admission;
	private String examinationReasons;
	private Long pratician;
	private String conclusionExamResult;
	private List<Long> pathologies;
	private List<Long> symptoms;

}
