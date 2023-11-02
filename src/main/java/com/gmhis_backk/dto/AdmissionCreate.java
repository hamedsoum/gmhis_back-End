package com.gmhis_backk.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonValue;
import com.gmhis_backk.constant.GMHISAdmissionType;
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
public class AdmissionCreate {

	private String type;

	private Date createdAt;

	private Long patient;

	private Long speciality;

	private Long act;

	private Long practician;

	private Double caution;

	private Boolean takeCare;

}

