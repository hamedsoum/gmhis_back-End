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
@Table(name="sample_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy="sampleType")
	private List<SampleItem> sampleItems;
	
}