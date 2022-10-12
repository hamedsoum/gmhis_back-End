package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Adjara
 * 
 */
@Entity
@Table(name = "antecedant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Antecedant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "updated_in")
	private Long updatedIn;

	@Column
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="treated_date")
	private Date treatedDate;
	
	@Column(name="medical_histories")
	//@JsonRawValue
	private String medicalHistories ;
	
	@Column(name="surgical_histories")
	//@JsonRawValue
	private String surgicalHistories;
	
	@Column(name="family_histories")
	//@JsonRawValue
	private String familyHistories ;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

}