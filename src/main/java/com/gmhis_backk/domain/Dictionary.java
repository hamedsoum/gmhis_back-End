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
@Table(name = "dictionary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String active="Y";

	@Column
	private String entry;

	@Column(nullable = true)
	private String abbrev;

	@Column(name = "display_key", nullable = true)
	private String displayKey;

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
	@JoinColumn(name = "dictionary_category_id")
	private DictionaryCategory dictionaryCategory;

}