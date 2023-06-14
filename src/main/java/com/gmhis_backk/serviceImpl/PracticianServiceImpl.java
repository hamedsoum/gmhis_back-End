package com.gmhis_backk.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.Role;
import com.gmhis_backk.domain.Speciality;
import com.gmhis_backk.dto.PraticianDto;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.InvalidInputException;
import com.gmhis_backk.exception.domain.NotAnImageFileException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.exception.domain.TelephoneExistException;
import com.gmhis_backk.exception.domain.UserNotFoundException;
import com.gmhis_backk.exception.domain.UsernameExistException;
import com.gmhis_backk.repository.PracticianRepository;
import com.gmhis_backk.service.FacilityService;
import com.gmhis_backk.service.PracticianService;
import com.gmhis_backk.service.RoleService;
import com.gmhis_backk.service.SpecialityService;
import com.gmhis_backk.service.UserService;


/**
 * 
 * @author Dabre
 *
 */
@Service
@Transactional
public class PracticianServiceImpl implements PracticianService{

	@Autowired
	private PracticianRepository repo;
	@Autowired
	private SpecialityService specialityService;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	public Pratician findPracticianByUser (Long user) {
		return repo.findByUser(user);
	}

	@Override
	public Pratician savePractician(PraticianDto praticianDto) throws ResourceNotFoundByIdException, EmailExistException, TelephoneExistException {
		Pratician pratician = new Pratician();
		
		Pratician praticienByEmail = repo.findByEmail(praticianDto.getEmail()).orElse(null);
		
		if(praticienByEmail != null) {
			throw new EmailExistException("Cet email est déjà utilisé");
		}
		
		Pratician praticienByTelephone = repo.findByTelephone(praticianDto.getTelephone()).orElse(null);
		if(praticienByTelephone != null) {
			throw new TelephoneExistException("Ce numéro de téléphone est déjà utilisé");
		}
		
		BeanUtils.copyProperties(praticianDto, pratician,"id");
		
		Speciality speciality = specialityService.findById(praticianDto.getSpeciliaty()).orElse(null);
		
		if(speciality == null) {
			throw new ResourceNotFoundByIdException("Spécialité inexistante");
		}
		
		
		pratician.setSpeciality(speciality);
		
		Facility facility = facilityService.findFacilityById(praticianDto.getFacility()).orElse(null);
		
		if(facility == null) {
			throw new ResourceNotFoundByIdException("Centre inexistant");
		}
		
		
		pratician.setFacility(facility);
		pratician.setCreatedAt(new Date());
		pratician.setPraticianNumber(this.generateSerialNumber());
		
		this.generateAccess(pratician);
		
		return repo.save(pratician); 
	}
	
	private Boolean generateAccess(Pratician pratician) {
		try {
			UserDto userDto = new UserDto();
			Role role = roleService.findByRoleName("practicien");
			List<Integer> roles = new ArrayList<>();
			roles.add(role.getId());
			userDto.setLastName(pratician.getPrenoms());
			userDto.setFirstName(pratician.getNom());
			userDto.setFacilityId(pratician.getFacility().getId());
			userDto.setEmail(pratician.getEmail());
			userDto.setPassword(pratician.getNom());
			userDto.setUsername(pratician.getNom());
			userDto.setRole("practicien");
			userDto.setRoles(roles);
			userDto.setTel(pratician.getTelephone());
			userDto.setActive(true);
			userService.addNewUser(userDto);
			return true;
		} catch (UserNotFoundException | UsernameExistException | EmailExistException | IOException
				| NotAnImageFileException | ResourceNotFoundByIdException | MessagingException
				| InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private String generateSerialNumber() {
		String serialNumber = RandomStringUtils.random(8, true, true);
		
		Pratician pratician  = repo.findByPraticianNumber(serialNumber).orElse(null);	
		if(pratician != null) {
			return this.generateSerialNumber();
		}
		
		return serialNumber;
	}

	@Override
	public Optional<Pratician> findPracticianById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<Pratician> findPracticians() {
		return repo.findAll();
	}

	@Override
	public Page<Pratician> findAllP(Pageable pageable) {
		Page<Pratician> practicians = repo.findAll(pageable);
		//practicians0
		return practicians;
	}
	
	@Override
	public List<Pratician> findActivePracticians() {
		return repo.findActivePracticians();
	}


	@Override
	public Page<Pratician> findByActive(String firstName, String lastName, String phoneContact, String practicianNumber, String active, Pageable pageable){
		return repo.findByActive(firstName,  lastName,  phoneContact, practicianNumber, active, pageable);	
	}
	
	
	@Override
	public Page<Pratician> findPracticians(String firstName, String lastName, String phoneContact, String practicianNumber, Pageable pageable) {
		return repo.findPracticians(firstName, lastName, phoneContact, practicianNumber, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByService(String firstName, String lastName, String phoneContact, String practicianNumber, Long service, Pageable pageable) {
		return repo.findByService(firstName, lastName, phoneContact, practicianNumber, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findBySpeciality(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Pageable pageable) {
		return repo.findByService(firstName, lastName, phoneContact, practicianNumber, speciality, pageable);
	}
	
	
	@Override
	public Page<Pratician> findBySpecialityAndService(String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Long service, Pageable pageable) {
		return repo.findBySpecialityAndService(firstName, lastName, phoneContact, practicianNumber, speciality, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByActiveAndSpeciality (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long speciality, Pageable pageable){
		return repo.findByActiveAndSpeciality(firstName, lastName, phoneContact, practicianNumber, active, speciality, pageable);
	}
	
	
	@Override
	public Page<Pratician> findByActiveAndService (String firstName, String lastName, String phoneContact, String practicianNumber, String active, Long service, Pageable pageable){
		return repo.findByActiveAndSpeciality(firstName, lastName, phoneContact, practicianNumber, active, service, pageable);
	}
	
	
	@Override
	public Page<Pratician> findPracticiansByAllFilters (String firstName, String lastName, String phoneContact, String practicianNumber, Long speciality, Long service, String active, Pageable pageable){
		return repo.findPracticiansByAllFilters(firstName, lastName, phoneContact, practicianNumber, speciality, service, active, pageable);
	}
	
	@Override
	public List<Pratician> findActivePracticiansByService(Long service){
		return repo.findActivePracticiansByService(service);
	}
	
	private List<Map<String, Object>> getMapFromPracticiansList(List<Pratician> practicians) {
		List<Map<String, Object>> practiciansList = new ArrayList<>();
		practicians.stream().forEach(practicianResponseDto -> {
			
		});
		return practiciansList;
	}
	
	public List<Pratician> findPracticiansBySpeciality(Long specialityId){
		List<Pratician> praticians  = repo.findPracticiansBySpeciality(specialityId);
		return praticians;
	}
	
}
