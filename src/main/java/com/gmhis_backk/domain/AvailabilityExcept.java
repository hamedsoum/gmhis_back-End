package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;



/**
 * @author pascal
 * 
 */
@Entity
@Table(name="availability_exception")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityExcept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Column(name="ended_time")
	private Time endedTime;

	@Temporal(TemporalType.DATE)
	@Column(name="exception_date")
	private Date exceptionDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_at")
	private Date lastUpdatedAt;

	@Column(name="last_updated_by")
	private Long lastUpdatedBy;

	@Column(name="started_time")
	private Time startedTime;

	@ManyToOne
	@JoinColumn(name="availability_id")
	private Availability availability;
}