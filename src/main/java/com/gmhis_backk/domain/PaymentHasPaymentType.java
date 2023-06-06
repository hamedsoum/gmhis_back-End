/**
 * 
 */
package com.gmhis_backk.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mamadouhamedsoumahoro
 *
 */
@Entity
@Table(name = "payment_has_Payment_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHasPaymentType {

	@Id
	 @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
	private UUID id;
	
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name="payment_id")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name="payment_type_id")
	private PaymentType paymentType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;
}
