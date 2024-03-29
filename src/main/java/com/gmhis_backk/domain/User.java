package com.gmhis_backk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
	
    private String userId;
    
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private String email;
    
    private String tel;
    
//    private String profileImageUrl;
    
    private Date lastLoginDate;
    
    private Date lastLoginDateDisplay;
    
    private Date joinDate;
    
    private String role; //ROLE_USER, ROLE_ADMIN
    
    private String roleIds;
    
    private String authorities; // user:read, user:edit, admin:delete
    
    private boolean isActive;
    
    private boolean isNotLocked;
    
    private boolean passwordMustBeChange;
    
    @Column(name = "facility_id")
	private String facilityId;
    
  
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facility_id", insertable = false, updatable = false)
	private Facility facility;
    
    @Column
	private String login;

    
    private String code;
    
      
    public String getEmail() {
		return StringUtils.capitalize(email).trim();
	}
    
    public String getTel() {
		return StringUtils.capitalize(tel).trim();
	}
    
    public String setEmail() {
		return email.trim();
	}
    
    public String setTel() {
		return tel.trim();
	}
    
    public String getFirstName() {
		return StringUtils.capitalize(firstName);
	}
    
    public String getLastName() {
		return StringUtils.capitalize(lastName);
	}

}
