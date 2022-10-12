package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Adjara
 * 
 */
@Entity
@Table(name = "patient_prescription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientPrescription implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	   
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "facility_Id")
	private Facility facility;
	
	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;
	
	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private AppUser pratician;
	
	@Column(columnDefinition = "json")
	@JsonRawValue
	private String drugs;
	
	@Column(columnDefinition = "json")
	@JsonRawValue
	private String drugServed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="served_at")
	private Date servedAt;

	@Column(name="served_by", nullable = true)
	private Long servedBy;
	
	private String state;
	
	private String collected;

}
