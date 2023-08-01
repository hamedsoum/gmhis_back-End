package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name = "act")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Act implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column
	private String description;

	@Column
	private String name;
	
	@Column
	private String codification;
	
	@Column
	private int coefficient;
	

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="act_category_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public ActCategory actCategory;
	

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="act_code_id")
	public ActCode actCode;
	

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="act_group_id")
	public ActGroup actGroup;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="medical_analysis_speciality_id")
	public MedicalAnalysisSpecilaity medicalAnalysisSpeciality;

	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "check_up_has_act", joinColumns = {
				@JoinColumn(name = "act_id", referencedColumnName = "id") }, inverseJoinColumns = {
						@JoinColumn(name = "check_up_id", referencedColumnName = "id") })
		private List<CheckUp> checkUps = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "act")
    private List<ConventionHasAct> conventions;

}