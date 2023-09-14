package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evacuation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evacuation implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
	private UUID id;

	@Column(name="evacuation_facility")
	private Facility evacuationFacility;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Date startDate;
	
	@ManyToOne
	private ActCategory service;
	
	private Pratician practician;
	
	private Patient patient;
	
	@Column(name="evacuation_reason")
	private String evacuationReason;
	
	@Column(name="clinical_information")
	private String clinicalInformation;
	
	@Column(name="treatment_received")
	private String treatmentReceived;
	
	@Column(name="reception_facility")
	private Facility receptionFacility;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatededAt;

	@Column(name="updated_by")
	private Long updatedBy;
}
