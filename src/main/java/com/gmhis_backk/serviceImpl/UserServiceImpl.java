package com.gmhis_backk.serviceImpl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserPrincipal;
import com.gmhis_backk.dto.ChangePasswordDto;
import com.gmhis_backk.dto.RequestResetPasswordDto;
import com.gmhis_backk.dto.ResetPasswordDto;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.domain.*;
import com.gmhis_backk.repository.AuthorityRepository;
import com.gmhis_backk.repository.RoleRepository;
import com.gmhis_backk.repository.UserRepository;
import com.gmhis_backk.service.EmailService;
import com.gmhis_backk.service.EventLogService;
import com.gmhis_backk.service.LoginAttemptService;
import com.gmhis_backk.service.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gmhis_backk.constant.UserImplConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;



/**
 * 
 * @author Mathurin
 *
 */
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	private EmailService emailService;

	@Autowired
	private EventLogService eventLogService;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AuthorityRepository authorityRepo;


	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService, EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		} else {

			validateLoginAttempt(user);
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			// LOGGER.info(FOUND_USER_BY_USERNAME + username);
			return userPrincipal;
		}
	}

	@Override
	public User register(String firstName, String lastName, String username, String email)
			throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
		validateNewUsernameAndEmail(StringUtils.EMPTY, username, email, null);
		User user = new User();
		user.setUserId(generateUserId());
		String password = generatePassword();
		user.setFirstName(StringUtils.capitalize(firstName));
		user.setLastName(StringUtils.capitalize(lastName));
		user.setUsername(username);
		user.setEmail(email.trim());
		user.setJoinDate(new Date());
		user.setPassword(encodePassword(password));
		user.setActive(true);
		user.setNotLocked(true);
		//user.setRole(ROLE_USER.name());
//		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
//		LOGGER.info("New user password: " + password);
		emailService.sendNewPasswordEmail(firstName, username, password, email);
		return user;
	}

	@Override
	public User addNewUser(UserDto userDto)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException, ResourceNotFoundByIdException, MessagingException, InvalidInputException {

		validateFirstNameAndLastNameAndPassword(userDto.getFirstName(), userDto.getLastName(), null, null);

		validateNewUsernameAndEmail(EMPTY, null, userDto.getEmail(), userDto.getTel());

	
		User user = new User();
		BeanUtils.copyProperties(userDto, user,"id");
		String password = generatePassword();

		user.setUserId(generateUserId());
		String username = generateUsername(user);
		user.setJoinDate(new Date());
		user.setUsername(username);
	
		user.setPassword(encodePassword(password));
		if (userDto.getRoles().size() != 0)
			user.setRoleIds(StringUtils.join(userDto.getRoles(), ","));
		user.setNotLocked(true);
		user.setPasswordMustBeChange(true);
		user = userRepository.save(user);
		if (userDto.getRoles().size() != 0) {
			for (int i = 0; i < userDto.getRoles().size(); i++) {
				roleRepo.setUserRole(user.getId(), userDto.getRoles().get(i));
			}
		}
		
		this.setUserRoleAndAuthorities(user);
		emailService.sendNewPasswordEmail(user.getFirstName(), username, password, user.getEmail());
//		eventLogService.addEvent("creation de l'utilisateur: " + user.getFirstName() + " " + user.getLastName(),user.getClass().getSimpleName());
		return user;
	}

	@Override
	public User updateUser(Long id,UserDto userDto)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException, ResourceNotFoundByIdException, InvalidInputException, com.gmhis_backk.exception.domain.ResourceNameAlreadyExistException {

		User currentUser = userRepository.findById(id).orElse(null);
		if (currentUser==null) throw new ResourceNameAlreadyExistException(NO_USER_FOUND_BY_USERNAME);
			
//		 User currentUser = validateNewUsernameAndEmail(currentFirstName, currentLastName, newEmail, newTel);
		validateFirstNameAndLastNameAndPassword(userDto.getFirstName(), userDto.getLastName(), null, null);
		
		
		currentUser.setFirstName(StringUtils.capitalize(userDto.getFirstName()));
		currentUser.setLastName(StringUtils.capitalize(userDto.getLastName()));
		if (userDto.getRoles().size() != 0) currentUser.setRoleIds(StringUtils.join(userDto.getRoles(), ","));
		currentUser.setNotLocked(userDto.isNotLocked());
		currentUser.setPasswordMustBeChange(userDto.isPasswordMustBeChange());
		userRepository.save(currentUser);

		if (userDto.getRoles().size() != 0) {
			roleRepo.removeUserRoles(currentUser.getId());
			for (int i = 0; i < userDto.getRoles().size(); i++) {
				roleRepo.setUserRole(currentUser.getId(), userDto.getRoles().get(i));
			}
		}
		
		if(currentUser.isActive()) loginAttemptService.evictUserFromLoginAttemptCache(currentUser.getUsername());
		this.setUserRoleAndAuthorities(currentUser);
//		eventLogService.addEvent("Modification de l'utilisateur: " + currentUser.getFirstName() + " " + currentUser.getLastName(),currentUser.getClass().getSimpleName());
		return currentUser;
	}


	@Override
	public User resetPassword( ResetPasswordDto resetPasswordDto)
			throws PasswordDoNotMatchException, InvalidInputException, ApplicationErrorException, MessagingException {
		
		User user = userRepository.findByCode(resetPasswordDto.getCode());

		if (user == null) {
			throw new ApplicationErrorException("Impossible! Ce code est expiré");
		} else {

			if (resetPasswordDto.getNewPassword().compareTo(resetPasswordDto.getConfirmPassword()) != 0) {
				throw new PasswordDoNotMatchException(PASSWORD_DO_NOT_MATCH);
			}

			validateFirstNameAndLastNameAndPassword(user.getFirstName(), user.getLastName(), user.getUsername(),
					resetPasswordDto.getNewPassword());
		}
		user.setPassword(encodePassword(resetPasswordDto.getNewPassword()));
		user.setPasswordMustBeChange(false);
		user = userRepository.save(user);
//		eventLogService.addEvent("l'utilisateur: " + user.getFirstName() + " " + user.getLastName() + " a changé son mot de passe",user.getClass().getSimpleName());

		return null;
	}

//	@Override
//	public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException,
//			UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
//		User user = validateNewUsernameAndEmail(username, null, null, null);
//		saveProfileImage(user, profileImage);
//		return user;
//	}

	@Override
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User findUserByUsername(String username) {
		User user = userRepository.findUserByUsername(username);
		if (user != null) {

			//mis en commentaire pour des besoins de test
			if (user.getId() != 0) 	this.setUserRoleAndAuthorities(user);
			
			String[] roles = StringUtils.split(user.getRoleIds(), ",");
			
			if ((roles.length == 1 &&  Integer.parseInt(roles[0]) != 0) || roles.length != 1) 	this.setUserRoleAndAuthorities(user);
		}

		return user;

	}

	public void setUserRoleAndAuthorities(User user) {

		List<String> lroles = roleRepo.findRolesByUserId(user.getId());
		List<String> lAuthorities = authorityRepo.findAuthoritiesByUser(user.getId());
		
		String roles = StringUtils.join(lroles, ",");
		String authorities = "aucun";
		lAuthorities.removeAll(Arrays.asList("", null));

		if (lAuthorities.size() != 0) {
			authorities = StringUtils.join(lAuthorities, ",");
			List<String>  authoritiesListwithDup = Arrays.asList(StringUtils.split(authorities, ","));
			List<String> authoritiesListwithoutDup = authoritiesListwithDup.stream().distinct().collect(Collectors.toList());
			authorities = StringUtils.join(authoritiesListwithoutDup, ",");
		}

		user.setRole(roles);
		user.setAuthorities(authorities);
		userRepository.save(user);
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public User findUserByTel(String phone) {
		return userRepository.findUserByTel(phone);
	}


	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	private String generatePassword() {
		return RandomStringUtils.randomNumeric(3) + '-' + RandomStringUtils.randomNumeric(3) + "#";
	}

	private String generateUsername(User user) {
		String[] firstNamePieces = user.getFirstName().split(" ");
		String newUserName = user.getLastName().charAt(0) + firstNamePieces[0] + RandomStringUtils.randomNumeric(3);
		User existingUser = userRepository.findUserByUsername(newUserName);
		while (existingUser != null) {
			newUserName = user.getLastName().charAt(0) + firstNamePieces[0] + RandomStringUtils.randomNumeric(3);
			existingUser = userRepository.findUserByUsername(newUserName);
		}
		return newUserName;
	}

	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail, String newPhone)
			throws UserNotFoundException, UsernameExistException, EmailExistException {

		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmail = StringUtils.isNotBlank(newEmail) ? findUserByEmail(newEmail) : null;
		User userByNewPhone = StringUtils.isNotBlank(newPhone) ? findUserByTel(newPhone) : null;

		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);
			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}
			if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			if (userByNewPhone != null && !currentUser.getId().equals(userByNewPhone.getId())) {
				throw new EmailExistException(PHONE_ALREADY_EXISTS);
			}
			return currentUser;
		} else {
			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
			}
			if (userByNewEmail != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXISTS);
			}
			if (userByNewPhone != null) {
				throw new EmailExistException(PHONE_ALREADY_EXISTS);
			}
			return null;
		}

	}

	public Boolean validateFirstNameAndLastNameAndPassword(String firstName, String lastName, String username,
			String password) throws InvalidInputException {

		ArrayList<String> forbiddenNames = new ArrayList<String>();
		forbiddenNames.add("admin");
		forbiddenNames.add("root");
		forbiddenNames.add("superviseur");
		forbiddenNames.add("manager");
		forbiddenNames.add("gerant");
		forbiddenNames.add("gestion");
		forbiddenNames.add("azerty");
		forbiddenNames.add("qwerty");

		for (int i = 0; i < forbiddenNames.size(); i++) {

			
				String firstNameToLowerCase = firstName.toLowerCase();
				if (firstNameToLowerCase.contains(forbiddenNames.get(i).toLowerCase())) {
					throw new InvalidInputException(INVALID_FIRSTNAME + forbiddenNames.get(i).toLowerCase());
				}

				String lastNameToLowerCase = lastName.toLowerCase();
				if (lastNameToLowerCase.contains(forbiddenNames.get(i).toLowerCase())) {
					throw new InvalidInputException(INVALID_LASTNAME + forbiddenNames.get(i).toLowerCase());
			    }

			if (password != null) {
				String passwordToLowerCase = password.toLowerCase();
				if (passwordToLowerCase.contains(forbiddenNames.get(i).toLowerCase())) {
					throw new InvalidInputException(INVALID_PASSWORD + forbiddenNames.get(i).toLowerCase());
				}
			}
		}

		if (password != null) {

			if (password.toLowerCase().contains(username.toLowerCase())) {
				throw new InvalidInputException(PASSWORD_MUST_NOT_CONTAINS_YOUR_USERNAME);
			}

			if (password.toLowerCase().contains(firstName.toLowerCase().trim())) {
				throw new InvalidInputException(PASSWORD_MUST_NOT_CONTAINS_YOUR_FIRSTNAME);
			}

			if (password.toLowerCase().contains(lastName.toLowerCase().trim())) {
				throw new InvalidInputException(PASSWORD_MUST_NOT_CONTAINS_YOUR_LASTNAME);
			}
		}

		return null;
	}

	@Override
	public Page<User> findAllUsersByAttributes(String firstName, String lastName, String tel, Pageable pageable) {

		return userRepository.findAllUsersByAttributes(firstName, lastName, tel, pageable);
	}

	@Override
	public List<User> findAllActive() {
		return userRepository.findAllActive();
	}

	@Override
	public String setNewPassword(String username) throws MessagingException {
		User user = userRepository.findUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME );
		} else {
			String password = generatePassword();
			user.setPassword(encodePassword(password));
			user.setPasswordMustBeChange(true);
			user = userRepository.save(user);
			emailService.sendNewPasswordEmail(user.getFirstName(), user.getUsername(), password, user.getEmail());
			eventLogService.addEvent(
					"l'utilisateur: " + user.getFirstName() + " " + user.getLastName() + " a changé son mot de passe",
					user.getClass().getSimpleName());
			return user.getEmail();
		}

	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> findUserByRole(Integer roleId) {
		
		return userRepository.findUserByRole(roleId);
	}

	
	@Override
	public void requestResetPassword(RequestResetPasswordDto resetPassword) throws ApplicationErrorException, MessagingException, URISyntaxException {
		
		User user = userRepository.findUserByEmail(resetPassword.getEmail());
		
		if (user == null) {
			throw new ApplicationErrorException(NO_USER_FOUND_BY_EMAIL);
		} else {
		
	    String code=generatedOtpCode(user);
//		URI uri = new URI("http://localhost:4200/account/reset-password/"+code);
		emailService.sendForgotEmail(resetPassword.getEmail(),code,user.getFirstName(),user.getLastName());
		
	}
 }
	
	
	public String generatedOtpCode(User user) throws MessagingException {
		String initialValue = RandomStringUtils.randomNumeric(17); 
		String finalValue = RandomStringUtils.randomNumeric(17); 
		String asciiValue = RandomStringUtils.randomAlphabetic(17);
		String code = initialValue+asciiValue+finalValue;
		user.setCode(code);
//		user.setTime(new Time(System.currentTimeMillis()));
		userRepository.save(user);
		return code;
	}

	@Override
	public User changePassword(ChangePasswordDto changePasswordDto) throws MessagingException, InvalidInputException, com.gmhis_backk.exception.domain.PasswordDoNotMatchException {
		
		User user = userRepository.findUserByUsername(changePasswordDto.getUsername());

		if (user == null) {
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + changePasswordDto.getUsername());
		} else {

			if (changePasswordDto.getNewPassword().compareTo(changePasswordDto.getConfirmPassword()) != 0) {
				throw new PasswordDoNotMatchException(PASSWORD_DO_NOT_MATCH);
			}

			validateFirstNameAndLastNameAndPassword(user.getFirstName(), user.getLastName(), user.getUsername(),
					changePasswordDto.getNewPassword());
		}
		user.setPassword(encodePassword(changePasswordDto.getNewPassword()));
		user.setPasswordMustBeChange(false);

		user = userRepository.save(user);
	
		return user;
	}

}
