package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active = "Y";

	@Column
	private String avatar;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt = new Date();

	@Column(name = "created_by")
	private Long createdBy;

	@Column
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "first_access")
	private Date firstAccess;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	private Date lastLogin;

	@Column(name = "last_name")
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_password_change")
	private Date lastPasswordChange;

	@Column
	private String locked = "N";

	@Column
	private String login;

	@Column
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_expire_at")
	private Date passwordExpireAt;

	@Column(name = "password_must_change")
	private String passwordMustChange = "N";

	@Column(name = "phone_contact")
	private String phoneContact;

	@Column(name = "session_life_time")
	private int sessionLifeTime;

	@Column(name = "is_admin")
	private String isAdmin = "N";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;

	//@JsonManagedReference
	//@JsonBackReference
	@ManyToMany(mappedBy = "appUsers", fetch = FetchType.EAGER)
	private List<AppRole> roles = new ArrayList<>();

	public String getAuthorities() {
		String authorities = "";
		for (int j = 0; j < roles.size(); j++) {
			authorities = authorities.concat(roles.get(j).getName());
			if (j <= roles.size() - 2) {
				authorities = authorities.concat(",");
			}
		}
		return authorities;
	}


}