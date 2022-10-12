package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Mathurin
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Communes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	private String name;
	
	private String displayName;
	
	@ManyToOne
	@JoinColumn(name = "ville_id")
	private Villes ville;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Date deletedAt;
	
	public String setName() {
		return StringUtils.capitalize(name).trim();
	}
	
	public String getName() {
		return  StringUtils.capitalize(name).trim();
	}
	
}
