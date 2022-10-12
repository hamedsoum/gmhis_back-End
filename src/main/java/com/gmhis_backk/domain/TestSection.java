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
@Table(name = "test_section")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "testSection")
	private List<Analysis> analysis;

	@OneToMany(mappedBy = "testSection")
	private List<Test> tests;

}