package com.gmhis_backk.domain;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bill_has_insured")
public class BillHasInsured {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "bill_id")
	private Bill bill;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "insurance_id")
    private Insurance insurance;
	
	@Column(name = "insured_coverage")
	private int insuredCoverage;
	
	@Column(name = "insured_Part")
	private int insuredPart;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "insured_id")
	private Insured insured;
	
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

}
