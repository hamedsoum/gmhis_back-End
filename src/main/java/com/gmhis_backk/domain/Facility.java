package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locality_id", insertable = false, updatable = false)
	private Locality locality;
	
	@Column(name = "facility_type_id")
	private String facilityTypeId;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_type_id", insertable = false, updatable = false)
	private FacilityType facilityType;
	
	
	@Column(name = "facility_category_id")
	private String facilityCategoryId;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_category_id", insertable = false, updatable = false)
	private FaciityCategory facilityCategory;
	
	@Column(name = "logo_id")
	private String logoId;
	
	private String address;
	
	private String contact;
}