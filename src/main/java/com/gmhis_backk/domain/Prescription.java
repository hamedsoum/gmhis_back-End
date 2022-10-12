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
@Table(name = "prescription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prescription_date")
	private Date prescriptionDate;

	@Column(name = "prescription_status")
	private String prescriptionStatus;

	@ManyToOne
	@JoinColumn(name = "examination_id")
	private Examination examination;

	@OneToMany(mappedBy = "prescription")
	private List<PrescriptionItem> prescriptionItems;
}