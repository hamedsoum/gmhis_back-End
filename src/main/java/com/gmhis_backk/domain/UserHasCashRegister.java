package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name = "user_has_cash_register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHasCashRegister implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active = "Y";
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser user;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "cash_register_id")
	private CashRegister cashRegister;

}