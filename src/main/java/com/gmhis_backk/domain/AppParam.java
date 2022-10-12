package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author mathurin
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppParam  implements Serializable{
	
	@Id
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	private String nameCompany;
	
	private String header;
	
	private String footPage;
	
	private String phone1;
	
	private String phone2;
	
	private String phone3;
	
	private String currency;
	
	private String address;
	
	private String website;
	
	private String email;
	
	private String postalCode;
	
	private String logo;
	
	private String cachet;
	
	@Transient
	private String stockEntryEmailSendTo;
	
	private String legalForm;
	
	private String slogan;
	
	private String activity;
	
	private static final long serialVersionUID = 1L;
	
	public String setNameCompany() {
		return StringUtils.capitalize(nameCompany).trim();
	}
	
	public String getNameCompany() {
		return nameCompany.trim();
	}

}
