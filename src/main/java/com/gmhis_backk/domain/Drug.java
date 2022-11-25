package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hamed soumahoro
 * 
 */
@Entity
@Table(name = "drug")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drug implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	 @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
   @Type(type = "uuid-char")
	private UUID id;
	
	@Column
	private Boolean active;
	
	@Column
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;


	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "drug_price")
	private Double drugPrice;
}
