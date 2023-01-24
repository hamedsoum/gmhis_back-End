package com.gmhis_backk.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
	private Long id;
	private String lastName;
	private String firstName;
//	private String maidenName = "";
	private String idcardType;
	private String idCardNumber;
	private String cnamNumber = "";
	private String correspondant;
	private String correspondantCellPhone;
//	private String patientExternalId;
	private Date birthDate;
	private String email;
	private String address;
	private String emergencyContact;
	private String emergencyContact2;
	private String gender;
	private String civility;
	private String numberOfChildren;
	private Long country;
	private Long cityId;
//	private String motherLastName;
//	private String motherFirstName;
//	private String motherProfession;
	private String profession;
	private String maritalStatus;
	private String cellPhone1;
//	private String cellPhone2;
//	private String fatherName;
	private int age;
	private double height;
	private double weight;
	private List<InsuredDTO> insurances;

}
