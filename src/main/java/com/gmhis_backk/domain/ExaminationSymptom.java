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
 * @author pascal
 * 
 */
@Entity
@Table(name="examination_symptom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationSymptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column
	private String observation;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="examination_id")
	private Examination examination;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="symptom_id")
	private Symptom symptom;

}