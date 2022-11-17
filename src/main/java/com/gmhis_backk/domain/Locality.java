package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Hamed soumahoro
 * 
 */
@Entity
@Table(name = "locality")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column
	private float latitude;

	@Column
	private float longitude;

	@Column
	private String name;
	@Column
	private String township;

	@Column(name = "zip_code", nullable = true)
	private String zipCode;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "district_id", nullable = true)
	private District district;

}