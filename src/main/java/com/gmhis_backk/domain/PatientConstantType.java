package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "patient_constant_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientConstantType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column
	private String description;

	@Column
	private String name;

	@Column(name = "short_name")
	private String shortName;

//	@Column(name = "result_type")
//	private ResultType resultType;

	@Column(name = "significant_digits")
	private int significantDigits = 0; // available it is numeric value

	@ManyToOne
	@JoinColumn(name = "patient_constant_domain_id")
	private PatientConstantDomain patientConstantDomain;

	@Column(name = "unit_of_mesure")
	private String unitOfMesure;

	@OneToMany(mappedBy = "patientConstantType")
    private List<PatientConstantValues> options;
	
}