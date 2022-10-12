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
@Table(name="speciality")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;

	private String deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deleted_at")
	private Date deletedAt;

	@Column(name="deleted_by")
	private Long deletedBy;

	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt; 

	@Column(name = "updated_by")
	private Long updatedBy;

	@JsonBackReference
	@OneToMany(mappedBy="speciality")
	private List<Pratician> praticians;

}