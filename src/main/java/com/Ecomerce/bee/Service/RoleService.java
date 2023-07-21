package com.Ecomerce.bee.Service;


import com.Ecomerce.bee.Model.Role;
import com.Ecomerce.bee.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Role createNewRole(Role role){
        return roleRepository.save(role);
    }
}
