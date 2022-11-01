package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 *
 * @author pascal
 */
@Entity
@Table(name = "analysis_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="admission_id")
	private Admission admission;
	
	@ManyToOne
	@JoinColumn(name="pratician_id")
	private User pratician;
	
	@Column(columnDefinition = "json")
	@JsonRawValue
	private String analysis; 
	
	private String otherAnalysis;
	
	private String observation;
	
	private String diagnostic;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	private String state;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "performed_at")
	private Date performedAt;

	@Column(name = "performed_by")
	private Long performedBy;

	@ManyToOne
	@JoinColumn(name ="facility_id")
	private Facility facility;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@OneToMany(mappedBy = "analysisRequest")
	private List<SampleItem> sampleItems;

}