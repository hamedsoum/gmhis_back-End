package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;


/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name="bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "bill_status")
	private String billStatus; // R: registered, C: collected
	
	@Column(name = "bill_number")
	private String billNumber;
	
	@Column(name = "total_amount")
	private int totalAmount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at")
	private Date lastUpdatedAt;
	
	@Column(name = "patient_part")
	private int patientPart;
	
	@Column(name = "part_taken_care_of")
	private int partTakenCareOf;
	
	@Column(name = "part_taken_care_of_number")
	private String partTakenCareOfNumber;
	
	@Column(name = "patient_type")
	private String patientType;
	
	@Column(name = "discount_in_cfa")
	private int discountInCfa;
	
	@Column(name = "discount_in_percentage")
	private int discountInPercentage;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "bill_type")
	private String billType;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="convention_id")
	private Convention convention;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="insured_id")
	private Insured insured;


	@ManyToOne
	@JoinColumn(name="admission_id")
	private Admission admission;
	
	

	@OneToMany(mappedBy="bill")
	private List<Payment> payments;
	
	
//	@JsonBackReference
//	@ManyToMany
//	@JoinTable(name = "admission_has_act", joinColumns = {
//			@JoinColumn(name = "bill_id", referencedColumnName = "id") }, inverseJoinColumns = {
//					@JoinColumn(name = "act_id", referencedColumnName = "id") })
//	private List<Act> acts = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy="bill")
	private List<AdmissionHasAct> acts;
//	
//	@JsonBackReference
//	@ManyToMany (mappedBy = "bills")
//	private List<Pratician> practicians;

}