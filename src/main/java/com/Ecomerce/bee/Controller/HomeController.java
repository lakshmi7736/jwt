package com.Ecomerce.bee.Controller;


import com.Ecomerce.bee.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/success")
    public String success(){
        return "success";
    }

//    @GetMapping("/users")
//    public List<User> getUser(){
//        System.out.println("users");
//        return userService.getUsers();
//    }
}
