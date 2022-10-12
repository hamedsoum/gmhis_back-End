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
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Personnes implements Serializable{
    private static final long serialVersionUID = 1L;
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
    private Long id;
    
    private String currentNomPrenoms;
    
	private String nom;
	
	private String prenoms;
	
	private Date dateDeNaissance;
	
	private String lieuDeNaissance;
	
	private String lieuDeResidence;
	
	private String nationalite;
	
//	private String email;
	
	private String telephone;
	
	private Date dateArrivee;
	
	private Date dateDepart;
	
//	private String lieuDeDestination;
	
	private Integer nombreEnfantAccompagnant;
	
	private String photo;	
	
	private Date dateDelivrancePiece;
	
	private String numeroPiece;
	
	@ManyToOne
	@JoinColumn(name="hotel_id")
	private Hotels hotel;
	
	private Boolean recherche;
	
	@ManyToOne
	@JoinColumn(name="pays_id")
	private Pays pays;
	
	@ManyToOne
	@JoinColumn(name="villes_id")
	private Villes ville;
	
	@ManyToOne
	@JoinColumn(name="type_piece_id")
	private TypePieces typePiece;
	
//	private String addresse;
	
	private Boolean consulter;
	
	private Date dateConsultation;
	
	private Boolean accompagne;
	
	private Boolean accompagnant;
	
	@ManyToOne
	@JoinColumn(name="personne_id")
	private Personnes personne;
	
	private String nomMere;
	
	private String nomPere;
    
	private String domicileHabituel;
	
	private String  numeroChambre;
	
	private String profession;
	
    private Date createdAt;
    
    private Date updatedAt;
    
    private Date deletedAt;

}