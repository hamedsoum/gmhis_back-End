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
	private String idcardType;
	private String idCardNumber;
	private String cnamNumber = "";
	private String correspondant;
	private String correspondantCellPhone;
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
	private Long cityOfResidence;
	private Long countryOfResidence;
	private String profession;
	private String municipality;
	private String maritalStatus;
	private String cellPhone1;
	private int age;
	private List<InsuredDTO> insurances;
	private Double solde; 
	private String motherName;
	private String motherLocality;

}
