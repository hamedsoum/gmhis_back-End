package com.gmhis_backk.dto;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Hamed soumahoro
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long id;
  
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String tel;
    
    List<Integer> roles;  
    private String role;
    
    private String username;
    
    private boolean active;
    
    private boolean notLocked;
    
    private boolean passwordMustBeChange;
    
    private UUID facilityId;
    
    private String password;

}
