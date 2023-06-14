package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.dto.PraticianDto;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.exception.domain.TelephoneExistException;


/**
 * 
 * @author Soumahoro hamed
 *
 */
@Service 
@Transactional
public interface PracticianService {

	public Pratician savePractician(PraticianDto p) throws ResourceNotFoundByIdException, EmailExistException, TelephoneExistException;

	public Optional<Pratician> findPracticianById(Long id);

	public List<Pratician> findPracticians();
	
	public Page<Pratician> findAllP(Pageable pageable);
	
	public List<Pratician> findActivePracticians();
	
	public Pratician findPracticianByUser(Long user);
	
	public Page<Pratician> findByActive(String firstName, String lastName, String phoneContact, String practicianNumber, String active, Pageable pageable);
	
    public Page<Pratician> findPracticians(String firstName, String lastName, String phoneContact, String practicianNumber, Pageable pageable);
    
    public Page<Pratician> findByService(String firstName, String lastName, String phoneContact, String practicianNumber, Long service, Pageable pageable);
	
    public Page<Pratician> findBySpeciality(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Pageable pageable);
	
    public Page<Pratician> findBySpecialityAndService(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Long service, Pageable pageable);
	
    public Page<Pratician> findByActiveAndSpeciality (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long speciality, Pageable pageable);
	
    public Page<Pratician> findByActiveAndService (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long service, Pageable pageable);
	
    public Page<Pratician> findPracticiansByAllFilters (String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, 
    		Long service , String active, Pageable pageable);
    
    public List<Pratician> findActivePracticiansByService(Long service);
    
    public List<Pratician> findPracticiansBySpeciality(Long service);
    
	
}
