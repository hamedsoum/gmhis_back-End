/**
 * 
 */
package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mamadou hamed soumahoro
 *
 */

@Entity
@Table(name = "cash_register_management")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegisterManagement implements Serializable {
	private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
   @Type(type = "uuid-char")
	private UUID id;
   
   //caisse 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cash_register")
   private CashRegister cashRegister;
   
   //caissier 
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cashier")
	private User cashier;
   
   //date d'ouverture de la caisse
   @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "opening_date")
	private Date openingDate;
   
   //etat de la caisse(ouverte ou ferme) 
   @Column(name = "state")
   private Boolean state;
   
   @Column(name = "opening_balance")
   private double openingBalance;
   
   @Column(name = "cash_register_balance")
   private double cashRegisterBalance;
   
   
   @Column(name = "closing_balance")
   private double closingBalance;
   
   @Column(name = "real_closing_balance")
   private double realClosingBalance;
   
   //date d'ouverture de la caisse
   @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closing_date")
	private Date closingDate;

}
