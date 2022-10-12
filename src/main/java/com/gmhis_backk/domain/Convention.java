package com.gmhis_backk.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author dabre
 *
 */
@Entity
@Table(name = "convention")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Convention implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active;

	@Column
	private String name;
	@Temporal(TemporalType.TIMESTAMP)

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@JsonBackReference
	@OneToMany(mappedBy = "convention")
    private List<ConventionHasAct> acts;
	
	@JsonBackReference
	@OneToMany(mappedBy = "convention")
    List<ConventionHasActCode> codes;

}
