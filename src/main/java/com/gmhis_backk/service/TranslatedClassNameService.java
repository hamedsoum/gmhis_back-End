package com.gmhis_backk.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;


/**
 * 
 * @author adjara
 *
 */
@Service
public class TranslatedClassNameService {
	
	public HashMap<String, String> getClassNameInFrench (){
		
		HashMap<String, String> tableNames = new HashMap<>();
		tableNames.put("Role", "role utilisateur");
		tableNames.put("User", "utilisateur");
		tableNames.put("RoleAuthorities", "permission");
		tableNames.put("Depot", "depot");
		tableNames.put("SupplierFamily", "famille fournisseur");
		tableNames.put("AppParam", "parametres application");
		tableNames.put("TransportVehicle", "véhicule de transport");
		tableNames.put("Bank", "Banque");
		tableNames.put("ArticleFamily", "famille article");
		tableNames.put("ArticleSubFamily", "sous famille article");
		tableNames.put("CustomerFamily", "famille client");
		tableNames.put("Article", "article");
		tableNames.put("Supplier", "fournisseur");
		tableNames.put("Customer", "client");
		tableNames.put("Planning", "plannification");
		tableNames.put("SupplierOrder", "commande fournisseur");
		tableNames.put("SupplierOrderLoading", "chargement");
		tableNames.put("Driver", "chauffeur");
		tableNames.put("FreightForwarder", "transitaire");
		tableNames.put("Transfer", "transfert");
		tableNames.put("BillOfLadingContainer", "conteneur du chargement");
		tableNames.put("StockEntry", "entrée en stock");
		tableNames.put("BillOfLading", "bon de chargement");
		tableNames.put("Port", "Port");
		tableNames.put("Shipper", "expéditeur");
		tableNames.put("Carrier", "transporteur");
		tableNames.put("ContainerCost", " cout de conteneur");
		tableNames.put("SupplierPayment", " payement fournisseur");
		tableNames.put("StockOutputReason", "motif sortie de stock");
		tableNames.put("StockOutput", "sortie de stock");
		tableNames.put("Inventory", "inventaire depot");
		tableNames.put("InventoryCustomer", "inventaire client");
		tableNames.put("InventoryPlanning", "plannifications d'inventaires");
		tableNames.put("Estimate", "devis");
		tableNames.put("Promotion", "promotion");
		tableNames.put("CustomerOrder", "commande client");
		tableNames.put("Assets", "Avoirs");
		tableNames.put("SalesDelivery", "vente client / livraison");
		tableNames.put("Fundraising", "collecte de fonds");
		tableNames.put("FinancialCharge", " charge ");
		tableNames.put("Invoice", " facture ");
		tableNames.put("Expense", " dépense ");
		tableNames.put("EntryType", " Type d'entrée");
		tableNames.put("Entry", "Entrée");
		tableNames.put("BranchOffice", "succursale");
		tableNames.put("Year", "Année");
		tableNames.put("SupplierPurchase", "Achat fournisseur");
		tableNames.put("TechnicianAss", "Technicien SAV");
		return tableNames;
		
	}
}
