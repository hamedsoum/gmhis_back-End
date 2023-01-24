package com.gmhis_backk.service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmhis_backk.domain.User;
import com.gmhis_backk.dto.ChangePasswordDto;
import com.gmhis_backk.dto.RequestResetPasswordDto;
import com.gmhis_backk.dto.ResetPasswordDto;
import com.gmhis_backk.dto.UserDto;
import com.gmhis_backk.exception.domain.*;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * 
 * @author adjara
 *
 */
public interface UserService {


    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException , MessagingException;

    Page<User> getUsers(Pageable pageable);
    
    List<User> findAllActive();
    
    Page<User> findAllUsersByAttributes( String firstName, String lastName, String tel, Pageable pageable);


    User findUserByUsername(String username);
    
    User findUserById(Long id);

    User findUserByEmail(String email);
    
    User findUserByTel(String phone);
    
    
    User addNewUser(UserDto userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, ResourceNotFoundByIdException, MessagingException, InvalidInputException;

    User updateUser(Long id,UserDto userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, ResourceNotFoundByIdException, InvalidInputException, ResourceNameAlreadyExistException,MessagingException;

   // void deleteUser(String username) throws IOException;

   // void resetPassword(String email) throws MessagingException, EmailNotFoundException;
    
    User resetPassword(ResetPasswordDto resetPasswordDto) throws PasswordDoNotMatchException, InvalidInputException, ApplicationErrorException, MessagingException;

//    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    String setNewPassword(String username ) throws MessagingException;
    
	Optional<User> findById(Long id);
	
	List<User> findUserByRole(Integer roleId);
	
	public void  requestResetPassword(RequestResetPasswordDto resetPassword) throws ApplicationErrorException, MessagingException, URISyntaxException;
	
	User changePassword(ChangePasswordDto changePasswordDto ) throws MessagingException, InvalidInputException, PasswordDoNotMatchException;
	    
	
    }
