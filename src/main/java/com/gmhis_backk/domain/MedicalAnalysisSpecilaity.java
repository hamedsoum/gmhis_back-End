package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="medical_analysis_speciality")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalAnalysisSpecilaity implements Serializable{

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
}
