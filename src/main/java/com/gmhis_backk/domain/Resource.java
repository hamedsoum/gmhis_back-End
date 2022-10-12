package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author adjaratou
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Integer id;
	
	@Column(nullable = false, updatable = false)
	private String name;
	
	@OneToMany(mappedBy = "resource_id", fetch = FetchType.LAZY)
	private List<Authority> authorities = new ArrayList<Authority>();
	
	@Column(nullable = false, updatable = false)
	private boolean isActive;
	
	public String setName() {
		return StringUtils.capitalize(name).trim();
	}
	
	public String getName() {
		return  StringUtils.capitalize(name).trim();
	}
	
}
