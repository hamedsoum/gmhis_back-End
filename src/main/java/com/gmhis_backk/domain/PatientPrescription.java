package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gmhis_backk.domain.admission.Admission;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed soumahoro
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
	 @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
   @Type(type = "uuid-char")
	private UUID id;
	
	@Column(name = "prescription_number")
	private String prescriptionNumber;
	   
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_id", insertable = false, updatable = false)
	private Facility facility;
	
	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;
	
	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician pratician;
	
	@ManyToOne
	@JoinColumn(name = "examination_id")
	private Examination examination;
	
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
