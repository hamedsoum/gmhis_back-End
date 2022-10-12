package com.gmhis_backk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Adjara
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmailType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private int id;
	
	@NotNull
	@Size(min = 2, max=50, message = "La longueur du nom doit être comprise  entre 2  et 50 charactères ")
	private String name;
	
	@NotNull(message = "le champs actif est requis")
	private Boolean isActive;

}
