package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

import java.util.List;


/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name="availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Availability implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_at")
	private Long createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_by")
	private Date createdBy;

	@Column
	private String day;

	@Column(name="end_time")
	private Time endTime;

	@Column(name="started_time")
	private Time startedTime;

	@Column
	private String yearweek;

	@ManyToOne
	@JoinColumn(name="pratician_id")
	private Pratician pratician;

	@OneToMany(mappedBy="availability")
	private List<AvailabilityExcept> availabilityExceptions;

}