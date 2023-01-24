package com.gmhis_backk.controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.gmhis_backk.domain.HttpResponse;
import com.gmhis_backk.domain.User;
import com.gmhis_backk.domain.UserPrincipal;
import com.gmhis_backk.dto.ChangePasswordDto;
import com.gmhis_backk.dto.LoginDto;
import com.gmhis_backk.dto.RequestResetPasswordDto;
import com.gmhis_backk.dto.ResetPasswordDto;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.ExceptionHandling;
import com.gmhis_backk.exception.domain.*;
import com.gmhis_backk.service.CurrentUserService;
import com.gmhis_backk.service.UserService;
import com.gmhis_backk.utility.JwtTokenProvider;

import javax.mail.MessagingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiOperation;

import static com.gmhis_backk.constant.FileConstant.*;
import static com.gmhis_backk.constant.SecurityConstant.ACTION_PERFORM_DENIED_MESSAGE;
import static com.gmhis_backk.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;


/**
 * 
 * @author Mathurin
 *
 */

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling{
	    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
	    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
	    private AuthenticationManager authenticationManager;
	    private UserService userService;
	    private JwtTokenProvider jwtTokenProvider;
	    

	    @Autowired
	    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
	        this.authenticationManager = authenticationManager;
	        this.userService = userService;
	        this.jwtTokenProvider = jwtTokenProvider;
	    }
	    
	    @Autowired
	    CurrentUserService currentUserService;

	    @ApiOperation(value = "Authentification des utilisateur")
	    @PostMapping("/login")
	    public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
	        authenticate(loginDto.getUsername(), loginDto.getPassword());
	        User loginUser = userService.findUserByUsername(loginDto.getUsername());      
	        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
	        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
	        return new ResponseEntity<>(loginUser, jwtHeader, OK);
	    }

	  //  @PostMapping("/register")
	    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
	        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
	        return new ResponseEntity<>(newUser, OK);
	    }

	    @ApiOperation(value = "Ajouter un utilisater")
	    @PostMapping("/add")
	    public ResponseEntity<User> addNewUser(@RequestBody UserDto userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, ResourceNotFoundByIdException, MessagingException, InvalidInputException, ApplicationErrorException {
//	    	if(!currentUserService.checkIfCurrentUserHasAuthority("user:add")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
	    	User newUser = userService.addNewUser(userDto);
	        return new ResponseEntity<>(newUser, OK);
	    }

	    @ApiOperation(value = "Mofifier un utilisateur")
	    @PutMapping("/update/{id}")
	    public ResponseEntity<User> update(@PathVariable("id")Long id,@RequestBody UserDto userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, ResourceNotFoundByIdException,  ResourceNameAlreadyExistException,ApplicationErrorException,MessagingException, InvalidInputException {
	    	if(!currentUserService.checkIfCurrentUserHasAuthority("user:update")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
	    	User updatedUser = userService.updateUser(id, userDto);
	        return new ResponseEntity<>(updatedUser, OK);
	    }
	    
	    @ApiOperation(value = "rechercher un utilisateur par son nom utilisateur")
	    @GetMapping("/find/{username}")
	    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
	        User user = userService.findUserByUsername(username);
	        return new ResponseEntity<>(user, OK);
	    }
	    
	    @ApiOperation(value = "rechercher un utilisateur par son nom identifiant")
	    @GetMapping("/detail/{id}")
	    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
	        User user = userService.findUserById(id);
	        return new ResponseEntity<>(user, OK);
	    }

	    @ApiOperation(value = "Liste paginée de tous les utilisateur")
	    @GetMapping("/list")
	    //@PreAuthorize("hasAnyAuthority('user:list')")
	    public ResponseEntity<Map<String, Object>> getAllUsers(
	    		@RequestParam(required = false, defaultValue = "") String firstName,
				@RequestParam(required = false, defaultValue = "") String lastName,
				@RequestParam(required = false, defaultValue = "") String tel,
				@RequestParam(required = false) Integer depot,
		        @RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size,
		        @RequestParam(defaultValue = "id,desc") String[] sort) throws com.gmhis_backk.exception.domain.ApplicationErrorException {
	    	
	    	if(!currentUserService.checkIfCurrentUserHasAuthority("user:list")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
	    	
	    	  List<User> users = new ArrayList<User>();	  
	          Sort.Direction dir = sort[1].equalsIgnoreCase("asc") ? dir = Sort.Direction.ASC : Sort.Direction.DESC;
		      Pageable paging = PageRequest.of(page, size, Sort.by(dir,sort[0]));
		      
		      Page<User> pageUsers;
		      
		      pageUsers = userService.getUsers(paging);
		      
		      if (StringUtils.isNotBlank(firstName) || StringUtils.isNotBlank(lastName) || StringUtils.isNotBlank(tel)) { 
		    	  pageUsers = userService.findAllUsersByAttributes(firstName.trim(), lastName.trim(), tel.trim(), paging);
		      } 
	      

		      users = pageUsers.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("items", users);
		      response.put("currentPage", pageUsers.getNumber());
		      response.put("totalItems", pageUsers.getTotalElements());
		      response.put("totalPages", pageUsers.getTotalPages());
			  response.put("size", pageUsers.getSize());
			  response.put("firstPage", pageUsers.isFirst());
			  response.put("lastPage", pageUsers.isLast());
			  response.put("empty", pageUsers.isEmpty());

			return new ResponseEntity<>(response, OK);
	    }

//	    @ApiOperation(value = "Changer le mot de passe d'un utilisateur")
//	    @GetMapping("/resetpassword/{email}")
//	    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException {
//	        userService.resetPassword(email);
//	        return response(OK, EMAIL_SENT + email);
//	    }
	    
	    @ApiOperation(value = "Changer le mot de passe d'un utilisateur")
	    @PostMapping("/resetpassword")
	    public ResponseEntity<User> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws PasswordDoNotMatchException, com.gmhis_backk.exception.domain.InvalidInputException, com.gmhis_backk.exception.domain.ApplicationErrorException, MessagingException  {
//	    	if(!currentUserService.checkIfCurrentUserHasAuthority("user:reset_password")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
	    	User user = userService.resetPassword(resetPasswordDto);
	        return  new ResponseEntity<> (user, OK );
	    }
	    
	    
	    @ApiOperation(value = "Generer un nouveau mot de passe et l'envoyer par mail d'un utilisateur")
	    @PostMapping("/send-new-password/{username}")
	    public ResponseEntity<HttpResponse> getNewPassword(@PathVariable("username") String username) throws MessagingException, EmailNotFoundException {
	        String email = userService.setNewPassword(username);
	        return response(OK, EMAIL_SENT + email);
	    }


	    @ApiOperation(value = "obtenir la photo de profile d'un utilisateur")
	    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
	    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
	        return Files.readAllBytes(Paths.get(IMAGE_FOLDER + username + FORWARD_SLASH + fileName));
	    }

	    @ApiOperation(value = "")
	    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
	    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
	        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try (InputStream inputStream = url.openStream()) {
	            int bytesRead;
	            byte[] chunk = new byte[1024];
	            while((bytesRead = inputStream.read(chunk)) > 0) {
	                byteArrayOutputStream.write(chunk, 0, bytesRead);
	            }
	        }
	        return byteArrayOutputStream.toByteArray();
	    }

	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }

	    private HttpHeaders getJwtHeader(UserPrincipal user) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
	        return headers;
	    }

	    private void authenticate(String username, String password) {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    }

	    
	    @ApiOperation(value = "Demander à reinitialiser son mot de passe")
		@PostMapping(value = "/request-reset-password")
		public void requestResetPassword(@RequestBody RequestResetPasswordDto resetPassword) throws ApplicationErrorException, MessagingException, URISyntaxException {
			 userService.requestResetPassword(resetPassword);
			
	    }
	    
	    @ApiOperation(value = "Changer le mot de passe d'un utilisateur lors de la première connexion")
	    @PostMapping("/change-password")
	    public ResponseEntity<User> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws PasswordDoNotMatchException, InvalidInputException, ApplicationErrorException, MessagingException  {
//	    	if(!currentUserService.checkIfCurrentUserHasAuthority("user:reset_password")) throw new ApplicationErrorException(ACTION_PERFORM_DENIED_MESSAGE);
	    	User user = userService.changePassword(changePasswordDto);
	        return  new ResponseEntity<> (user, OK );
	    }
	    
	    

}
