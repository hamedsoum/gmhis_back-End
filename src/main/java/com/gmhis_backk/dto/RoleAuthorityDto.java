package com.gmhis_backk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author adjara
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleAuthorityDto {

	private Integer role;
	private List<Integer> authorities;
}
