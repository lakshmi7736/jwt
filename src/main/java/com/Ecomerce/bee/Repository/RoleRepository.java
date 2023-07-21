package com.Ecomerce.bee.Repository;


import com.Ecomerce.bee.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByRoleName(String roleName);
}
