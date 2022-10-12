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
@Table(name = "treatment_drug")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDrug implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	private String dosage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ended_date")
	private Date endedDate;

	@Lob
	private String observation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "real_started_date")
	private Date realStartedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "started_date")
	private Date startedDate;

	private String status;

	@ManyToOne
	private Article drug;

	@ManyToOne
	private Treatment treatment;

}