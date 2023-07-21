package com.Ecomerce.bee.Service;


import com.Ecomerce.bee.Model.Role;
import com.Ecomerce.bee.Model.User;
import com.Ecomerce.bee.Repository.RoleRepository;
import com.Ecomerce.bee.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {
    @Autowired

    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getUsers(){

        return userRepository.findAll();
    }


    public User createUser(User user){
        Role role= roleRepository.findById("USER").get();
        Set<Role> roleSet =new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    public User createAdmin(User user){
        Role role= roleRepository.findById("ADMIN").get();
        Set<Role> roleSet =new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

}
