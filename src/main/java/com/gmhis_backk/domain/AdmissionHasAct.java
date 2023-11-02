package com.gmhis_backk.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.gmhis_backk.domain.admission.Admission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dabre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admission_has_act")
public class AdmissionHasAct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "act_id")
	private Act act;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User createdBy;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician practician;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "bill_id")
	private Bill bill;
	
	private int actCost;
	

}
