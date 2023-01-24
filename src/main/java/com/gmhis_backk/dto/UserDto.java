package com.gmhis_backk.dto;
import java.util.List;

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
    
    private boolean active;
    
    private boolean notLocked;
    
    private boolean passwordMustBeChange;
    
    private String facilityId;

}
