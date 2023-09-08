package com.gmhis_backk.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank
	private String clinicalExamination;
	private String anamnesisSocioProfessional;
	private String antecedentsFamily;
	private String habits;
	private String diagnosisPresumptive;
	private String oldTreatment;
	private String conclusion;
	private Date endDate;
	private String examinationType;
	private String history;
	private Date startDate;
	private Long admission;
	private String examinationReasons;
	private Long pratician;
	private String conclusionExamResult;
	private List<Long> pathologies;
	private List<Long> symptoms;

}
