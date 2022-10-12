package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Pascal
 * 
 */
@Entity
@Table(name = "site_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteInformation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "site_name")
	private String siteName;

	@Column(name = "acronym")
	private String acronym;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "site_code")
	private String siteCode;

	@Column(name = "site_sub_code")
	private String siteSubCode = "01"; // for sub site

	@Column(name = "logo")
	private Long logo;

	@Column(name = "color_1")
	private String color1;

	@Column(name = "color_2")
	private String color2;

	@Column(name = "color_3")
	private String color3;

	@Column(name = "page_header")
	private String pageHeader;

	@Column(name = "page_footer")
	private String pageFooter;

	@Column(name = "motto")
	private String motto; // devise

	@Column(name = "site_contact")
	private String siteContact;

	@Column(name = "site_email")
	private String siteEmail;

	@Column(name = "site_address")
	private String siteAddress;

	@Column(name = "header_info_1")
	private String headerInfo1;

	@Column(name = "header_info_2")
	private String headerInfo2;

	@Column(name = "header_info_3")
	private String headerInfo3;

	@Column(name = "patient_code_prefix")
	private String patientPrefix = "PT";
	
	@Column(name = "patient_code_int_length")
	private int patientCodeIntLength=6;

	@Column(name = "active")
	private String active = "N";

	@Column(name = "site_id")
	private Long siteId;

	@Column(name = "configuration_name")
	private String configurationName;

}