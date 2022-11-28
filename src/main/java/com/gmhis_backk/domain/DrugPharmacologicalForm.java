package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name="drug_pharmacological_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugPharmacologicalForm implements Serializable {

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@JsonBackReference
	@OneToMany(mappedBy="pharmacologicalForm",fetch = FetchType.LAZY)
	private List<Article> drugs;

}
