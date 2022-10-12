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
@Table(name="symptom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Symptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_at")
	private Date lastUpdatedAt;

	@Column(name="last_updated_by")
	private Long lastUpdatedBy;

	private String name;

//	@JsonBackReference
//	@ManyToMany(mappedBy = "symptoms")
//	private List<Examination> examinations = new ArrayList<>();
	
//	private List<ExaminationSymptom> examinationSymptoms;

}