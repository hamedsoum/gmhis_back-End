package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hamed soumahoro
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admission")
public class Admission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "take_care")
	private Boolean takeCare ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "take_care_at")
	private Date takeCareAt;

	@Column(name = "take_care_by")
	private Long takeCareBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "admission_end_date")
	private Date admissionEndDate;

	@Column(name = "admission_number")
	private String admissionNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "admission_start_date")
	private Date admissionStartDate;

	@Column(name = "admission_status")
	private String admissionStatus; // R: registered( non yet billed) , Bill: Billed

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "is_appointment")
	private String isAppointment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "next_appointment")
	private Date nextAppointment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "bail", nullable=true)
	private Double caution;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "act_id")
	private Act act;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician practician;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="service_id")
	private Service service;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="act_category_id")
	private ActCategory speciality;
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "admission_has_act", joinColumns = {
			@JoinColumn(name = "admission_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "act_id", referencedColumnName = "id") })
	private List<Act> acts = new ArrayList<>();
	
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "admission_has_act", joinColumns = {
			@JoinColumn(name = "admission_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "pratician_id", referencedColumnName = "id") })
	private List<Pratician> practicians = new ArrayList<>();
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "admission_has_act", joinColumns = {
			@JoinColumn(name = "admission_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "bill_id", referencedColumnName = "id") })
	private List<Bill> bills = new ArrayList<>();
//
//	
	@JsonBackReference
	@OneToMany(mappedBy = "admission")
	private List<AnalysisRequest> analysisRequests;

	@JsonBackReference
	@OneToMany(mappedBy = "admission")
	private List<Examination> examinations;
	
	
	 
	@Column(name = "facility_id", nullable = true)
	private String facilityId;
	    
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_id", insertable = false, updatable = false)
	private Facility facility;
	

}