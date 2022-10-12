package com.gmhis_backk.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.util.List;


/**
 * @author pascal
 * 
 */
@Entity
@Table(name="insurance_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String description;

	@Column
	private String name;
}