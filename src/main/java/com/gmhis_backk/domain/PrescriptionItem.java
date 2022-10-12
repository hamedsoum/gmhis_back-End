package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "prescription_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String dosage;

	private short quantity;

	@ManyToOne
	@JoinColumn(name = "drug_id")
	private Article drug;

	@ManyToOne
	@JoinColumn(name = "prescription_id")
	private Prescription prescription;

}