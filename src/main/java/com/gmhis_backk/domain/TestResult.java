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
@Table(name="test_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="analysis_id")
	private Analysis analysis;

}