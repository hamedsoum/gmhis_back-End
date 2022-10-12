package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "patient_vaccin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientVaccin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_vaccin")
	private Date dateVaccin;

	private String dosage;

	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name="vaccin_id")
	private Vaccin vaccin;
}