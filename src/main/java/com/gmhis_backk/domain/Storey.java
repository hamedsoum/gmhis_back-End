package com.gmhis_backk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Storey implements Serializable{
    private static final long serialVersionUID = 1L;
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
    private Long id;
	
	private String libelle;
	
	@ManyToOne
	@JoinColumn(name = "building_id")
	private Building building;
	
}