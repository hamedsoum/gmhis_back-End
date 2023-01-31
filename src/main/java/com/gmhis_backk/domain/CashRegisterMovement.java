/**
 * 
 */
package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mamadouhamedsoumahoro
 *
 */

@Entity
@Table(name = "cash_register_movement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegisterMovement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
	
	private UUID id;
	
	private String libelle;
	
	private double debit;
	
	
	private double credit;
	
	// numero de la prestation
	@Column(name = "prestation_number")
	private String prestationNumber;
	
	 //caisse 
		@JsonBackReference
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="cash_register")
	   private CashRegister cashRegister;
		
}
