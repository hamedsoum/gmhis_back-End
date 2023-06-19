package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pascal
 * 
 */
@Entity
@Table(name = "pratician")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pratician implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Boolean active;
	
	@JoinColumn(unique=true,name = "signature", nullable = true)
	private String signature;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at", nullable = true)
	private Date lastUpdatedAt;

	@Column(name = "pratician_number")
	private String praticianNumber;

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "user_id", nullable = true)
	private User user = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	
	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "created_by")
	private Long createdBy;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "speciality_id")
	private Speciality speciality;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_id")
	private Facility facility;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "act_category_id")
	private ActCategory actCategory;

	@Column(name="nom")
	@NotNull(message="Le champ nom est requis")
	private String nom;
	
	@Column(name="prenoms")
	@NotNull(message="Le champ prénoms est requis")
	private String prenoms;
	
	@NotNull(message="Le champ telephone est requis")
	@Column(name="telephone",unique=true)
	private String telephone;
	
	@Column(name="email",unique=true)
	private String email;
	
}