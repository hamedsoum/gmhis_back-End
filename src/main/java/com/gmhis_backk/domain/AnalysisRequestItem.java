package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hamed soumahoro
 * 
 */
@Entity
@Table(name = "analysis_request_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisRequestItem implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(generator = "uuid2")
 @GenericGenerator(name = "uuid2", strategy = "uuid2")
 @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
 @Type(type = "uuid-char")
 
	private UUID id;
 
 	private boolean state;
 	
 	@ManyToOne
	@JoinColumn(name="act_id")
	private Act act; 
 	
 	@ManyToOne
	@JoinColumn(name="perform_by")
	private User pratician;
 	
 	
 	@ManyToOne
	@JoinColumn(name="analysis_request_id")
	private AnalysisRequest analysisRequest; 
 	
 	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

}
