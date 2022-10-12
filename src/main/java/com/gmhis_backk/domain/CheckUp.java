package com.gmhis_backk.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author dabre
 *
 */
@Entity
@Table(name = "check_up")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckUp implements Serializable {
	
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
	
   // @JsonBackReference
	@ManyToMany
	@JoinTable(name = "check_up_has_act", joinColumns = {
			@JoinColumn(name = "check_up_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "act_id", referencedColumnName = "id") })
	private List<Act> acts= new ArrayList<>();

}
