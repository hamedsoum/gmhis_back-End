package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

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
@Table(name="vaccin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;

	private String deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deleted_at")
	private Date deletedAt;

	@Column(name="deleted_by")
	private Long deletedBy;

	private String name;

	@Column(name="vaccin_type")
	private String vaccinType;

	@OneToMany(mappedBy="vaccin")
	private List<PatientVaccin> patientVaccins;

}