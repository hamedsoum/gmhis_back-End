package com.gmhis_backk.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Hotels;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels,Long>{
   
	Hotels findByNom(String nom);
	
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.deletedAt IS null")
	List<Hotels> findAllHotel(String nom);

    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.id=:localisationId AND h.deletedAt IS null")
	List<Hotels> findByLocalisation(String nom,Long localisationId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.commune.id=:communeId AND h.deletedAt IS null")
   	List<Hotels> findByCommune(String nom,Long communeId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.commune.ville.id=:villeId AND h.deletedAt IS null")
   	List<Hotels> findByVille(String nom,Long villeId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.id=:localisationId AND h.localisation.commune.id=:communeId AND h.deletedAt IS null")
	List<Hotels> findByLocalisationAndCommune(String nom,Long localisationId,Long communeId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.id=:localisationId AND h.localisation.commune.ville.id=:villeId AND h.deletedAt IS null")
	List<Hotels> findByLocalisationAndVille(String nom,Long localisationId,Long villeId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.commune.id=:communeId AND h.localisation.commune.ville.id=:villeId AND h.deletedAt IS null")
	List<Hotels> findByCommuneAndVille(String nom,Long communeId,Long villeId);
    
    @Query("SELECT h FROM Hotels h WHERE h.nom LIKE %:nom% AND h.localisation.id=:localisationId AND h.localisation.commune.ville.id=:villeId AND h.localisation.commune.id=:communeId AND h.deletedAt IS null")
	List<Hotels> findByLocalisationAndVilleAndCommune(String nom,Long localisationId,Long villeId,Long communeId);

	@Query("SELECT h FROM Hotels h WHERE h.id=:id")
	Hotels getDetail(Long id);

	 @Query("SELECT h FROM Hotels h WHERE  h.localisation.commune.id=:communeId AND h.deletedAt IS null")
	 List<Hotels> findByCommune(Long communeId);
	 
	 @Query("SELECT h FROM Hotels h WHERE  h.deletedAt IS null")
     List<Hotels> findAllHotel();
	 
	 @Query("SELECT COUNT (*) AS number FROM Hotels")
	 Integer numberOfHotel();
	 
	 @Query("SELECT h FROM Hotels h WHERE h.user.id=:user AND  h.deletedAt IS null")
     Hotels findHotelByUser(Long user);
	 
	 @Query("SELECT h FROM Hotels h WHERE h.localisation.commune.ville.id=:villeId AND h.deletedAt IS null")
	 List<Hotels> findByVille(Long villeId);
	 

}