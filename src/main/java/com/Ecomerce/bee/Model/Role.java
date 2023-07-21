package com.Ecomerce.bee.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    private String roleName;
    private String roleDescription;
}