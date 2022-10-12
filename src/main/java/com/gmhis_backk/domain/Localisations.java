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
public class Localisations implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	private String intitule;
	
	@ManyToOne
	@JoinColumn(name = "commune_id")
	private Communes commune;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Date deletedAt;
	
	public String setIntitule() {
		return StringUtils.capitalize(intitule).trim();
	}
	
	public String getIntitule() {
		return  StringUtils.capitalize(intitule).trim();
	}
}
