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
 * @author pascal
 * 
 */
@Entity
@Table(name="district")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="dhis_code")
	private String dhisCode;

	@Column
	private String name;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;

	@OneToMany(mappedBy="district")
	private List<Locality> localities;

}