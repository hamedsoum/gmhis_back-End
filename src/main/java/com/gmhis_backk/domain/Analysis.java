package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author pascal
 * 
 */
@Entity
@Table(name="analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Analysis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@ManyToOne
	private Test test;

	@ManyToOne
	@JoinColumn(name="test_section_id")
	private TestSection testSection;

	@OneToMany(mappedBy="analysis")
	private List<SampleItem> sampleItems;

	@OneToMany(mappedBy="analysis")
	private List<TestResult> testResults;

}