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
@Table(name = "test_panel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPanel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@JoinTable(name = "test_has_test_panel", joinColumns = {
			@JoinColumn(name = "test_panel_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "test_id", referencedColumnName = "id") })
	private List<Test> tests;

}