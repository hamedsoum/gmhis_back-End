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
@Table(name = "facility")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facility implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "dhis_code")
	private String dhisCode;

	@Column
	private float latitude;

	@Column(name = "local_code")
	private String localCode;

	@Column
	private float longitude;

	@Column
	private String name;

	@Column(name = "short_name")
	private String shortName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "locality_id")
	private Long localityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locality_id", insertable = false, updatable = false)
	private Locality locality;
}