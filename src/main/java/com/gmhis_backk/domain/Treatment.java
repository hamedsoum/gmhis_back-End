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
@Table(name = "treatment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treatment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ended_date")
	private Date endedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at")
	private Date lastUpdatedAt;

	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	@Lob
	private String observation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "started_date")
	private Date startedDate;

	@Column(name = "treatment_status")
	private String treatmentStatus;

	@ManyToOne
	@JoinColumn(name = "pathology_id")
	private Pathology pathology;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician pratician;

	@OneToMany(mappedBy = "treatment")
	private List<TreatmentDrug> treatmentDrugs;

}