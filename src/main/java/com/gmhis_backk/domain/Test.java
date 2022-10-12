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
@Table(name = "test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "test")
	private List<Analysis> analysis;

	@OneToMany(mappedBy = "test")
	private List<ResultLimit> resultLimits;

	@ManyToOne
	@JoinColumn(name = "test_section_id")
	private TestSection testSection;

	@ManyToOne
	@JoinColumn(name = "unit_of_mesure_id")
	private UnitOfMeasure unitOfMesure;

	@ManyToMany(mappedBy = "tests")
	private List<TestPanel> testPanels;

}