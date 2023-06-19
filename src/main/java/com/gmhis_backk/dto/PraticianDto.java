package com.gmhis_backk.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PraticianDto {
	private String signature;
	
	@NotNull(message = "Le champ specialité est requis")
	private Long speciliaty;
	
	@NotNull(message = "Le champ category est requis")
	private Long actCategoryID;
	
	@NotNull(message = "Le champ établissement est requis")
	private UUID facility;
	
	@NotNull(message = "Le champ nom est requis")
	@Size(min=2,message="Le champ nom doit disposer de 2 caractères")
	private String nom;
	
	@NotNull(message = "Le champ prenoms est requis")
	@Size(min=2,message="Le champ prenoms doit disposer de 2 caractères")
	private String prenoms;
	
	@NotNull(message="Le champ téléphone est requis")
	private String telephone;
	private String email;
}
