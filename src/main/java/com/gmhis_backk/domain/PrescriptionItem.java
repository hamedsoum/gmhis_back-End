package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "prescription_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
	private UUID id;

	private String dosage;

	private short quantity;

	@ManyToOne
	@JoinColumn(name = "drug_id")
	private Drug drug;
	
//	 @Column(name = "drug_id", nullable = true)
//		private String drugId;
//		    
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "drug_id", insertable = false, updatable = false)
//	private Drug d;


	@JsonManagedReference(value = "prescription-prescriptionItem")
	@ManyToOne
	@JoinColumn(name = "prescription_id")
	private Prescription prescription;
	
	private Boolean collected;
	
	private String duration;


}