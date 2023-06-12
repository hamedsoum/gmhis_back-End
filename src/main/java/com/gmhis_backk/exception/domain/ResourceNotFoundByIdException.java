package com.gmhis_backk.exception.domain;

/**
 * 
 * @author adjara
 *
 */
public class ResourceNotFoundByIdException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundByIdException(String message) {
		super(message);
	}
}
