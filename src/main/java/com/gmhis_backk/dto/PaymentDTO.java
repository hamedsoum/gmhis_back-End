package com.gmhis_backk.dto;




import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hamed soumahoro
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

	private Long id;
	private int amount;
	private Long cashRegister;
	private Long billID;
	private UUID invoiceHID;
	private Long paymentType;
	private List<PaymentHasPaymentTypeDTO> paymentsType;
	private double amountReceived;
	private double amountReturned;
}
