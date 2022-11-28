package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.util.List;

/**
 * @author Hamed soumahoro
 * 
 */
@Entity
@Table(name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "cnam_number")
	private String cnamNumber;
	
	@Column(name = "cell_phone_1", nullable = true)
	private String cellPhone1;

	@Column(name = "cell_phone_2", nullable = true)
	private String cellPhone2;

	@Column
	private String correspondant;

	@Column(name = "correspondant_cell_phone")
	private String correspondantCellPhone;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "death_date", nullable = true)
	private Date deathDate;

	@Column
	private String deleted = "N";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_at")
	private Date deletedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Column(name = "email_stop", nullable = true)
	private String emailStop;

	@Column(name = "emergency_contact")
	private String emergencyContact;

	@Column(name = "emergency_contact2")
	private String emergencyContact2;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "father_name", nullable = true)
	private String fatherName;

	@Column(name = "father_profession", nullable = true)
	private String fatherProfession;

	@Column
	private String gender;

	@Column(name = "idcard_number")
	private String idCardNumber;

	@Column(name = "idcard_type")
	private String idcardType;

	@Column(name = "mother_profession")
	private String motherProfession;

	@Column(name = "patient_external_id")
	private String patientExternalId;

	@Column(name = "patient_status")
	private String patientStatus;

	@Column(name = "sms_stop", nullable = true)
	private String smsStop;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "maiden_name", nullable = true)
	private String maidenName;

	@Column(nullable = true)
	private String civility;

	@Column
	private String profession;

	@Column(nullable = true)
	private String email;

	@Column(name = "mother_last_name")
	private String motherLastName;

	@Column(name = "mother_first_name")
	private String motherFirstName;

	@Column(name = "mother_maiden_name", nullable = true)
	private String motherMaidenName;

	@Column(name = "number_of_children")
	private String numberOfChildren;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", nullable = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public City city;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = true)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public Country country;

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<PatientAddress> patientAddresses;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "is_assured")
	private Boolean isAssured;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Insured> insurances;
	
	@Column
	private int age;
	
	
	@Column
	private Double height;
	
	
	@Column
	private Double weight;

}