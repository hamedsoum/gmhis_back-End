package com.gmhis_backk.domain;


import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author Adjara
 * 
 */
@Entity
@Table(name="article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "default_dosage")
	private String defaultDosage;

	@Column
	private String name;
	
	@Column(name = "selling_price")
	private int sellingPrice;
	
	@Column(name = "qty_in_stock")
	private int quantityInStock;
	
	@Column(name = "alert_qty")
	private int alertQuantity; 
	
	@Column(name = "ppv")
	private int ppv; 
	
	@Column
	private String generic;
	
	@Column
	private String princeps;
	
	@Column
	private Integer condQty;
	
	@Column
	private Integer buyingPrice;
	
	@Column
	private Integer valQty;
	
	@Column
	private String reference;
	
	
	@Column(name = "article_supplier_id")
	private Long supplier;
	
	
	@Column
	private String active = "Y";
	
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
	
	// @JsonBackReference
	@ManyToMany
	@JoinTable(name = "drug_has_dcn", joinColumns = {
				@JoinColumn(name = "drug_id", referencedColumnName = "id") }, inverseJoinColumns = {
						@JoinColumn(name = "dcn_id", referencedColumnName = "id") })
	private List<DrugCommonName> dcns= new ArrayList<>();
	
	@Column(name="drug_lab_id")
	private Long laboratory;
	
	@Column(name="dcu_id")
	private Long conditioningUnit;

	@Column(name="pharma_form_id")
	private Long pharmacologicalForm;

	@Column(name="pharma_class_id")
	private Long pharmaceuticClass;
	
	@Column(name="thera_class_id")
	private Long therapeuticClass;

	@Column(name="article_group_id")
	private Long articleGroup;
	
	@Column(name="location")
	private Long articleLocation;
}