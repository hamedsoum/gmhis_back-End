package com.gmhis_backk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Role;


/**
 * 
 * @author adjara
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	
	Role findByName(String name);
	
	@Query(value = "SELECT r FROM Role r  WHERE isActive=1 and id <> 0")
	List<Role> findAllActive();
	
	@Query(value = "SELECT name FROM role r LEFT JOIN user_role ur on r.id=ur.role_id WHERE user_id= :userId", nativeQuery = true)
    List<String> findRolesByUserId(Long userId);
	
	@Query(value = "SELECT r FROM Role r WHERE r.name like :name% And isActive = :isActive AND id <> 0 ")
	Page<Role> findByNameAndIsActive(String name, Boolean isActive, Pageable pageable);
	
	@Query(value = "SELECT r FROM Role r WHERE r.name like :name% AND id <> 0 ")
	Page<Role> findByName(String name, Pageable pageable);
	
	@Modifying
	@Query(value = "INSERT INTO user_role (user_id, role_id, is_active) VALUES (:userId, :roleId, 1)", nativeQuery = true)
	void setUserRole(Long userId, Integer roleId);
	
	@Modifying
	@Query(value = "DELETE FROM user_role where user_id= :userId", nativeQuery = true)
	void removeUserRoles(Long userId);
	
	@Query(value = "SELECT r FROM Role r where id= :id")
	Optional<Role> findById(int id);
	
	@Query(value = "SELECT r FROM role r LEFT JOIN user_role ur on r.id=ur.role_id WHERE user_id= :userId", nativeQuery = true)
    List<Role> findByUser(Long userId);
}
