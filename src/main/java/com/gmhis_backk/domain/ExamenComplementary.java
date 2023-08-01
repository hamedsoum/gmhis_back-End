/**
 * 
 */
package com.gmhis_backk.domain;

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

import com.gmhis_backk.constant.ExamenComplementaryTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hamed Soumahoro
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name="examen_complementary")
public class ExamenComplementary {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="act_id")
	private  Act act;
	
	@Column(name="examen_complementary_type")
	private ExamenComplementaryTypeEnum examenComplementaryType;
	
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
	
    @Type(type = "uuid-char")
	private UUID facilityID;
	
	private Boolean active;
	
}
