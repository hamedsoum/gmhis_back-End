package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name="country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="iso_code")
	private String isoCode;

	@Column
	private String name;

	@Column(name="short_name")
	private String shortName;

	@JsonBackReference
	@OneToMany(mappedBy="country", fetch = FetchType.LAZY)
	private List<City> cities;
//
	@JsonBackReference
	@OneToMany(mappedBy="country", fetch = FetchType.LAZY)
	private List<Region> regions;
}