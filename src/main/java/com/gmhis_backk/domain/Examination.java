package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "examination")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Examination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=true)
	private String conclusion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "examination_type")
	private String examinationType;

	@Column(name = "facility_id", nullable = false)
	  @Type(type = "uuid-char")
	private UUID facilityId;
		    
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_id", insertable = false, updatable = false)
	private Facility facility;

	@Lob
	@Column
	private String history;

	@Lob
	@Column(name = "old_treatment")
	private String oldTreatment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;

	@ManyToOne
	@JoinColumn(name = "admission_id")
	private Admission admission;

	@Column(name = "examination_reasons")
	private String examinationReasons;
	
	@Column(name = "conclusion_exam_result")
	private String conclusionExamResult;
	
	//private String observation;

	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician pratician;

	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "examination_has_pathology", joinColumns = {
			@JoinColumn(name = "examination_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "pathology_id", referencedColumnName = "id") })
	private List<Pathology> pathologies = new ArrayList<Pathology>();
	
	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "examination_symptom", joinColumns = {
			@JoinColumn(name = "examination_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "symptom_id", referencedColumnName = "id") })
	private List<Symptom> symptoms = new ArrayList<>();
	
	@JsonBackReference(value = "examination-prescription")
	@OneToMany(mappedBy = "examination")
	private List<Prescription> prescriptions;
	

}