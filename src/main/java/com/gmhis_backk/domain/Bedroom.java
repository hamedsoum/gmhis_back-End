package com.gmhis_backk.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
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
public class Bedroom implements Serializable{
    private static final long serialVersionUID = 1L;
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
    private Long id;
	
	private String libelle;

	@ManyToOne
	@JoinColumn(name = "storey_id")
	private Storey storey;
	
	@ManyToOne
	@JoinColumn(name = "bedroom_type_id")
	private BedroomType bedroomType;
	
}