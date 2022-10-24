package com.gmhis_backk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pascal
 * 
 */
@Entity
@Table(name = "waiting_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitingRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean active;

	private int capacity;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt; 

	@Column(name = "updated_by")
	private Long updatedBy;


}