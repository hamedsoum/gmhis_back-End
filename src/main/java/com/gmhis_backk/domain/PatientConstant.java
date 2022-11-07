package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Pascal
 * 
 */
@Entity
@Table(name = "patient_constant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientConstant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private String observation = "";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "taken_at")
	private Date takenAt;

	@Column(name = "taken_by")
	private Long takenBy;

	@Column(columnDefinition = "json")
	@JsonRawValue
	private String value;

	@ManyToOne
	private Patient patient;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "patient_constant_type_id")
	private PatientConstantType constant;

}