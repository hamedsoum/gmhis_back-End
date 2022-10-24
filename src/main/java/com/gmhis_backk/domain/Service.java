package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "waiting_room_id")
	private WaitingRoom waitingRoom;

	@Column(name = "facility_id", nullable = true)
	private Long facilityId;

	@OneToMany(mappedBy = "service")
	private List<Admission> admissions;

	@ManyToOne
	@JoinColumn(name = "facility_id", insertable = false, updatable = false, nullable = true)
	private Facility facility;



}