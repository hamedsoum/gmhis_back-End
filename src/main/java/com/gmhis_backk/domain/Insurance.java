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
@Table(name = "insurance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insurance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column
	private String deleted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_at")
	private Date deletedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Column
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt; 

	@Column(name = "updated_by")
	private Long updatedBy;

	@ManyToOne
	@JoinColumn(name = "insurance_type_id")
	private InsuranceType insuranceType;

	@Column
	private String code;

	@Column
	private String address;

	@Column
	private String account;
}