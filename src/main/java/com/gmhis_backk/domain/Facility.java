package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author Hamed soumahoro
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
	 @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
	private UUID id;

	@Column
	private Boolean active;

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