package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name = "patient_document")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	   
	private String type; 
	  
	private String custom_text;  

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "doc_id")
	private Long docId;
	
	@ManyToOne
	@JoinColumn(name = "facility_Id")
	private Facility facility;
	
	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;
	
	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private AppUser pratician;
	
	@ManyToOne
	@JoinColumn(name = "doc_id", insertable = false, updatable = false)
	private CheckUp checkUp;
	
	@ManyToOne
	@JoinColumn(name = "doc_id", insertable = false, updatable = false)
	private CertificatType certificate;
	
	@ManyToOne
	@JoinColumn(name = "doc_id", insertable = false, updatable = false)
	private CROType cro;
	
	@ManyToOne
	@JoinColumn(name = "doc_id", insertable = false, updatable = false)
	private CourierType courier;

}
