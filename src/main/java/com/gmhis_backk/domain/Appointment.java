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
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active;

	@Temporal(TemporalType.DATE)
	@Column(name = "appointment_date")
	private Date appointmentDate;

	@Column(name = "appointment_status")
	private String appointmentStatus;

	@Column(name = "ended_time")
	private Time endedTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "real_appointment_date")
	private Date realAppointmentDate;

	@Column(name = "real_ended_time")
	private Time realEndedTime;

	@Column(name = "real_started_time")
	private Time realStartedTime;

	@Column(name = "started_time")
	private Time startedTime;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "pratician_id")
	private Pratician pratician;

}