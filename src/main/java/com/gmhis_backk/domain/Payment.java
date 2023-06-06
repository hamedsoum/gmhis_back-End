package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;



/**
 * @author pascal
 * 
 */
@Entity
@Table(name="payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private int amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;
	
	@ManyToOne
	@JoinColumn(name = "cash_register_id")
	private CashRegister cashRegister;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="paiement_date")
	private Date paiementDate;

	@ManyToOne
	@JoinColumn(name="bill_id")
	private Bill bill;

	@ManyToOne
	@JoinColumn(name="payment_type_id")
	private PaymentType paymentType;

	@Column(name="amount_received")
	private double amountReceived;
	
	@Column(name="amount_returned")
	private double amountReturned;
}