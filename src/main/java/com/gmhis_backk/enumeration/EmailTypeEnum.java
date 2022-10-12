package com.gmhis_backk.enumeration;

/**
 * 
 * @author adjara
 *
 */
public enum EmailTypeEnum {
	article_order(1),
	stock_entry(2),
	article_sale_price_update(3),
	article_average_cost_update(4);
	
	public final Integer emailType;

    private EmailTypeEnum(Integer emailType) {
        this.emailType = emailType;
    }
    
    public Integer getEmailType() {
		return emailType;
	}
}
