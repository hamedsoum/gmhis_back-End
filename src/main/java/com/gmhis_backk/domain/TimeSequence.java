package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "time_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSequence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String active;

	@Column(name = "ended_time")
	private Time endedTime;

	@Column(name = "sequence_name")
	private String sequenceName;

	@Column(name = "started_time")
	private Time startedTime;

}