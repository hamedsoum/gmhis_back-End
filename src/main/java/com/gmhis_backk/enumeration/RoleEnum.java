package com.gmhis_backk.enumeration;

/**
 * 
 * @author adjara
 *
 */
public enum RoleEnum {
	super_admin(0),
	admin(1),
	Responsable_achat_fournisseur(2),
	chef_magasinier(3),
	magasinier(4),
	Responsable_commercial(5),
	commercial(6),
	Gerant_depot(7),
	inventoriste(8),
	collecteur_fond(9),
	caissier(10),
	modification_speciale(30);

	public final Integer role;

    private RoleEnum(Integer role) {
        this.role = role;
    }
    
    public Integer getRole() {
		return role;
	}
}
