package com.gmhis_backk.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmhis_backk.domain.User;

/**
 * 
 * @author adjara
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT u FROM User u where id <> 0")
	Page<User> findAll(Pageable pageable);
	
	@Query(value = "SELECT u FROM User u  WHERE isActive=1 and id <> 0")
	List<User> findAllActive();
	
	@Query(value = "SELECT u FROM User u WHERE username =:username")
    User findUserByUsername(String username);

    User findUserByEmail(String email);
    
    User findUserByTel(String phone);
    
    @Query(value = "SELECT u FROM User u WHERE u.firstName like :firstName% and u.lastName like :lastName% and  u.tel like :tel% and id <> 0")
    Page<User> findAllUsersByAttributes(String firstName, String lastName, String tel, Pageable pageable);
    
    @Query(value = "SELECT u FROM User u where id= :id")
	Optional<User> findById(Long id);
    
    @Query(value = "SELECT * FROM user u "
    		+ "INNER JOIN user_role ur on u.id = ur.user_id "
    		+ "INNER join role r on r.id = ur.role_id where r.id =:roleId", nativeQuery=true)
    List<User> findUserByRole(Integer roleId);
    
    User findByCode(String code);
    
   
}
