/**
 * 
 */
package com.gmhis_backk.domain;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hamed Soumahoro
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeathPartial {
	
	private UUID id;
	
	private String code;
	
	private Date deathDate;

	private String deathReason;
	
	private Date deathDeclarationDate;
	
	private String deathDeclaratedByName;
	private Long deathDeclarationByID;
	
	private String patientFirstName;
	private String patientLastName;
	private Long patientID;

}
