package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	 @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
   @Type(type = "uuid-char")
	private UUID id;

	
	@Column(name="analysis_number")
	private String analysisNumber;
	
	
	@ManyToOne
	@JoinColumn(name="admission_id")
	private Admission admission;
	
	@ManyToOne
	@JoinColumn(name="pratician_id")
	private User pratician;
		
	private String observation;
	
	private String diagnostic;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	private char state;
	
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