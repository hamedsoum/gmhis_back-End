package com.gmhis_backk.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashierCreate {
	private UUID id;
	private Long userID;
	private Boolean active;
}
