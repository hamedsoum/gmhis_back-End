package com.gmhis_backk.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author HAMED
 *
 */
@Entity
@Table(name = "act_code")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActCode implements Serializable{
	private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private int value;
	
	@Column
	private Boolean active;
	
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
	@OneToMany(mappedBy="actCode",fetch = FetchType.LAZY)
	private List<Act> acts;
	
	@JsonBackReference
	@OneToMany(mappedBy = "actCode")
    private List<ConventionHasActCode> conventions;
	
}
