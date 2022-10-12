package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.util.List;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "insured")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insured implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active;

	@Column
	private int coverage;
	
	@Column(name="card_number")
	private String cardNumber;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column
	private String deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_at",nullable = true)
	private Date deletedAt;

	@Column(name = "deleted_by",nullable = true)
	private Long deletedBy;

	@Column(name = "is_principal_insured")
	private String isPrincipalInsured = "Y"; //Y or N

	@Column(name = "principal_insured_affiliation",nullable = true)
	private String principalInsuredAffiliation;
	
	@Column(name = "principal_insured_name",nullable = true)
	private String principalInsuredName;

	@Column(name = "principal_insured_contact",nullable = true)
	private String principalInsuredContact;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="insurance_id")
	private Insurance insurance;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "insurance_suscriber_id")
	private InsuranceSuscriber insuranceSuscriber;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = true)
	private Patient patient;
	
	
	
}