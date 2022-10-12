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
 * @author Pascal
 * 
 */
@Entity
@Table(name = "pratician")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pratician implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active;

//	@JoinColumn(name = "signature", nullable = true)
//	@OneToOne
//	private GmhisFiles signature;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at", nullable = true)
	private Date lastUpdatedAt;

	@Column(name = "pratician_number")
	private String praticianNumber;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "user_id", nullable = true)
	private AppUser user = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	
	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "created_by")
	private Long createdBy;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "speciality_id")
	private Speciality speciality;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;
	
//	@JsonBackReference
//	@ManyToMany
//	@JoinTable(name = "admission_has_act", joinColumns = {
//				@JoinColumn(name = "pratician_id", referencedColumnName = "id") }, inverseJoinColumns = {
//						@JoinColumn(name = "admission_id", referencedColumnName = "id") })
//		private List<Admission> admissions = new ArrayList<>();
	

}