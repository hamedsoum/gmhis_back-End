package com.gmhis_backk.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Pratician;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface PracticianRepository extends JpaRepository<Pratician, Long>{

	@Query(value = "select p from Pratician p join p.user u where u.id=:user")
	public Pratician findByUser(@Param("user") Long user);
	
	@Query(value = "select p from Pratician p where active = 1")
	public List<Pratician> findActivePracticians();
	
	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% ")
    public Page<Pratician> findPracticians(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber,Pageable pageable);
	
	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.speciality.id= :speciality and p.service.id= :service and p.active= :active")
    public Page<Pratician> findPracticiansByAllFilters (@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("speciality") Long speciality, 
    		@Param("service") Long service ,@Param("active") String active, Pageable pageable);

	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.service.id= :service")
	public Page<Pratician> findByService(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("service") Long service, Pageable pageable);

	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.speciality.id= :speciality")
	public Page<Pratician> findBySpeciality(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("speciality") Long speciality, Pageable pageable);

	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.service.id= :service and p.speciality.id= :speciality")
	public Page<Pratician> findBySpecialityAndService(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("tel") String tel,
			@Param("practicianNumber") String practicianNumber, @Param("speciality") Long speciality, @Param("service") Long service, Pageable pageable);

	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.active = :active")
	public Page<Pratician> findByActive(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("active") String active, Pageable pageable);


	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.active= :active and p.speciality.id = :speciality ")
    public Page<Pratician> findByActiveAndSpeciality(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("active") String active, @Param("speciality") Long speciality, Pageable pageable);
	
	
	@Query(value = "select p from Pratician p where p.user.firstName like %:firstName%  and p.user.lastName like %:lastName% and p.user.tel like %:tel% "
			+ "and p.praticianNumber like %:practicianNumber% and p.active= :active and p.service.id = :service ")
   	public Page<Pratician> findByActiveAndService(@Param("firstName") String firstName, @Param("lastName") String lastName,
    		@Param("tel") String tel, @Param("practicianNumber") String practicianNumber, @Param("active") String active, @Param("service") Long service, Pageable pageable);

	@Query(value = "select p from Pratician p where active = 1 and service_id= :service")
	public List<Pratician> findActivePracticiansByService(@Param("service") Long service);
}
