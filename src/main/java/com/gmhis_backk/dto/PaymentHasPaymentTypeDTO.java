/**
 * 
 */
package com.gmhis_backk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mamadouhamedsoumahoro
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHasPaymentTypeDTO {

	private String id;
	private double amount;
	private Long paymentID ;
	private Long paymentTypeID;

}
