package com.gmhis_backk.domain;


import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Pascal
 * 
 */
@Entity
@Table(name = "patient_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "current_address")
	private String currentAddress; // Y or N

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
}