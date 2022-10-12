package com.gmhis_backk.domain;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author adjara
 *
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

/**
 * 
 * @author Mathurin
 *
 */
public class EventLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, updatable = false)
	private String event;
	
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	private String userName;
	
	@Column(nullable = false, updatable = false)
	private String category;
	
	@Column(nullable = false, updatable = false)
	private Date date;
	
	public String getEvent() {
		return StringUtils.capitalize(event);
	}
	
	public String getUserName() {
		return StringUtils.capitalize(userName);
	}
	
	public String getCategory() {
		return StringUtils.capitalize(category);
	}
}
