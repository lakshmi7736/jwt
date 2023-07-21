package com.Ecomerce.bee.Config;


import com.Ecomerce.bee.Model.*;
import com.Ecomerce.bee.Repository.UserRepository;
import com.Ecomerce.bee.Security.JwtHelper;
import com.Ecomerce.bee.Service.RefreshTokenService;
import com.Ecomerce.bee.Service.RoleService;
import com.Ecomerce.bee.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import org.springframework.ui.Model;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserDetailsService userDetailsService;


    private final AuthenticationManager manager;



    private final JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/refresh")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest request){

        RefreshToken refreshToken=refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user= refreshToken.getUser();
        String token=this.helper.generateToken(user);
        return JwtResponse.builder().refreshToken(refreshToken.getRefreshToken())
                .jwtToken(token)
                .username(user.getEmail())
                .build();
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") JwtRequest request, Model model) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            doAuthenticate(email, password);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            String token = helper.generateToken(userDetails);
            System.out.println(token);
            RefreshToken refreshToken= refreshTokenService.createRefreshToken(userDetails.getUsername());

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername())
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();

            model.addAttribute("jwtResponse", response);
            return "success"; // Return the name of the success HTML template
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid credentials");
            return "authentication-login"; // Return the name of the login form HTML template
        }
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }


    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


    @PostMapping("/register")
    public User createUser(@RequestBody User user){
        return  userService.createUser(user);
    }


    @PostMapping("/registerAdmin")
    public User createAdmin(@RequestBody User user){
        return  userService.createAdmin(user);
    }

    @PostMapping("/role")
    public Role createRole(@RequestBody Role role){
        return  roleService.createNewRole(role);

    }


}