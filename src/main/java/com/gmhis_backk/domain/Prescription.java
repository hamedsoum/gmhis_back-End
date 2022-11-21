package com.gmhis_backk.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Hamed soumahoro
 * 
 */
@Entity
@Table(name = "prescription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	 @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "uuid2")
   @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
   @Type(type = "uuid-char")
	private UUID id;
	
	@Column(name = "prescription_number")
	private String prescriptionNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prescription_date")
	private Date prescriptionDate;

	@Column(name = "prescription_status")
	private String prescriptionStatus;
	
	@Column(name = "observation")
	private String observation;

	@JsonManagedReference(value = "examination-prescription")
	@ManyToOne
	@JoinColumn(name = "examination_id")
	private Examination examination;

	@OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY)
	private List<PrescriptionItem> prescriptionItems;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_by")
	private Long updateBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_at")
	private Date update_at;
}