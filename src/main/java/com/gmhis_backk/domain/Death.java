/**
 * 
 */
package com.gmhis_backk.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hamed soumahoro
 *
 */
@Getter
@Setter
@Entity
@Table(name = "death")
@AllArgsConstructor
@NoArgsConstructor
public class Death {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
	private UUID id;
	
	// GMHIS-DTH-248
	private String code;

	@Column(name="death_date")
	private Date deathDate;
	
	@Column(name="death_reason")
	private String deathReason;
	
	@Column(name="death_declaration_date")
	private Date deathDeclarationDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "death_declaration_by")
	User deathDeclarationBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatededAt;

	@Column(name="updated_by")
	private Long updatedBy;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
}
