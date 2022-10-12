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
@Table(name = "sample_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@ManyToOne
	@JoinColumn(name = "analysis_id")
	private Analysis analysis;

	@ManyToOne
	@JoinColumn(name = "analysis_request_id")
	private AnalysisRequest analysisRequest;

	@ManyToOne
	@JoinColumn(name = "sample_type_id")
	private SampleType sampleType;
}