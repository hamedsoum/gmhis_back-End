package com.gmhis_backk.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.AppUtils;
import com.gmhis_backk.domain.ActCategory;
import com.gmhis_backk.domain.Facility;
import com.gmhis_backk.domain.Pratician;
import com.gmhis_backk.domain.Role;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.FacilityDTO;
import com.gmhis_backk.dto.PraticianDto;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.domain.EmailExistException;
import com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException;
import com.gmhis_backk.exception.domain.ResourceNotFoundByIdException;
import com.gmhis_backk.exception.domain.TelephoneExistException;
import com.gmhis_backk.exception.domain.UsernameExistException;
import com.gmhis_backk.repository.PracticianRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.ActCategoryService;
import com.gmhis_backk.service.FacilityService;
import com.gmhis_backk.service.PracticianService;
import com.gmhis_backk.service.RoleService;
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
	private ActCategoryService actCategoryService;
	@Autowired
	private PracticianRepository repo;
	@Autowired
	private FacilityService facilityService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired 
	private UserRepository userRepository;
	
	public Optional<Pratician> findPracticianByUser (Long user) {
		return repo.findByUser(user);
	}
	
	User getCurrentUser() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}
	
	protected User getCurrentUserId() {
		return this.userRepository.findUserByUsername(AppUtils.getUsername());
	}

	@Override
	public Pratician savePractician(PraticianDto praticianDto) throws ResourceNotFoundByIdException, EmailExistException, TelephoneExistException {
		Pratician pratician = new Pratician();
		
		Pratician praticienByUser = repo.findByUser(praticianDto.getUserID()).orElse(null);
		if(praticienByUser != null) throw new EmailExistException("Ce Practicien a déjà été associé a un utisateur");
		
		Pratician praticienByEmail = repo.findByEmail(praticianDto.getEmail()).orElse(null);
		if(praticienByEmail != null) throw new EmailExistException("Cet email est déjà utilisé");
		
		Pratician praticienByTelephone = repo.findByTelephone(praticianDto.getTelephone()).orElse(null);
		if(praticienByTelephone != null) throw new EmailExistException("Ce numéro de téléphone est déjà utilisé");
		
		BeanUtils.copyProperties(praticianDto, pratician,"id");	
		
		User user = userService.findById(praticianDto.getUserID()).orElse(null);
		if(user == null) {throw new ResourceNotFoundByIdException("Utilisateur inexistant");}
		pratician.setUser(user);
		
		ActCategory actCategory = actCategoryService.findById(praticianDto.getActCategoryID()).orElse(null);
		if(actCategory == null) {throw new ResourceNotFoundByIdException("category d'act inexistant");}
		pratician.setActCategory(actCategory);
		
		Facility facility = facilityService.findFacilityById(UUID.fromString(this.getCurrentUser().getFacilityId())).orElse(null);
		if(facility == null) {throw new ResourceNotFoundByIdException("Centre de sante inexistant");}
		pratician.setFacility(facility);
		pratician.setCreatedAt(new Date());
		pratician.setActive(true);
		pratician.setPraticianNumber(this.generateSerialNumber());
		
		
		return repo.save(pratician); 
	}
	
	public Pratician update(Long id,PraticianDto praticianDto) throws ResourceNameAlreadyExistException, ResourceNotFoundByIdException, EmailExistException {
		Pratician updatepractician = repo.findById(id).orElse(null);
		
		if (updatepractician == null) {
			 throw new ResourceNotFoundByIdException("Practician inexistant");
		} 
		BeanUtils.copyProperties(praticianDto, updatepractician,"id");
		updatepractician.setUpdatedAt(new Date());
		updatepractician.setUpdatedBy(getCurrentUserId().getId());
		return repo.save(updatepractician);
	}
	
	private Boolean generateAccess(Pratician pratician) throws UsernameExistException {
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
		Page<Pratician> practicians = repo.findAllPracticians(UUID.fromString(this.getCurrentUser().getFacilityId()), pageable);
		//practicians0
		return practicians;
	}
	
	@Override
	public List<Pratician> findActivePracticians() {
		return repo.findActivePracticians(UUID.fromString(this.getCurrentUser().getFacilityId()));
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
