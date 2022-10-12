package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 
 * @author pascal
 * 
 */
@Entity
@Table(name = "cro_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CROType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column(columnDefinition = "text")
	private String content;

	@Column
	private String active;

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

	@ManyToOne
	@JoinColumn(name = "cro_group_id")
	private CROGroup croGroup;

}