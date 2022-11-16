package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hamed soumahoro
 * 
 */
@Entity
@Table(name="examination_has_pathology")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationHasPathology implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Long createdBy;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="examination_id")
	private Examination examination;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="pathology_id")
	private Pathology pathology;
}
