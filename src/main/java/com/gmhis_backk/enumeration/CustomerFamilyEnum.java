package com.gmhis_backk.enumeration;

/**
 * 
 * @author adjara
 *
 */
public enum CustomerFamilyEnum {
	super_dealer(1),
	grossiste(2),
	demi_grossiste(3),
	showroom(4);
	
	public final Integer customerFamily;

    private CustomerFamilyEnum(Integer family) {
        this.customerFamily = family;
    }
    
    public Integer getCustomerFamily() {
		return customerFamily;
	}
}
