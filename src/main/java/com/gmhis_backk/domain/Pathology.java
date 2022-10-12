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
@Table(name="pathology")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pathology implements Serializable {
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column
	private String name;

	@JsonBackReference
	@ManyToMany
	@JoinTable(
		name="examination_has_pathology"
		, joinColumns={
			@JoinColumn(name="pathology_id",referencedColumnName = "id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="examination_id",referencedColumnName = "id")
			}
		)
	private List<Examination> examinations;

//	@JsonBackReference
//	@OneToMany(mappedBy="pathology")
//	private List<Treatment> treatments;

}