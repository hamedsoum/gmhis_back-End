package com.gmhis_backk.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Adjara
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaleDeliveryPaymentDto {

    private Long id;
    
    private Long salesDeliveryId;

	private Double amount;
	
	private String cashRegisterNumber;
	
	private String paymentType;
	
	private String chequeNumber;
	
	private int bank;
	
	private String accountNumber;
	
	private String observation;
	
	private Date collectionDate;
	
	private String collectedBy;
	
	private int relaunch;
	
}
