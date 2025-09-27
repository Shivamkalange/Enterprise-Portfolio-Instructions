package com.example.Enterprise_Portfolio_Instructions.Resource;


import com.example.Enterprise_Portfolio_Instructions.AuthenticationService.User;
import com.example.Enterprise_Portfolio_Instructions.Service.UserService;
import com.example.Enterprise_Portfolio_Instructions.Util.JwtUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.remote.JMXAuthenticator;

@RestController
@RequestMapping("epi/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.saveUser(user).getUsername() == null || userService.saveUser(user).getPassword() == null) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userService.saveUser(user), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        if (userService.getUserByUsername(username).isEmpty()) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

         if(user.getUsername()== null || user.getPassword()==null){
             return new ResponseEntity<>("Username or password cannot be null",HttpStatusCode.valueOf(400));
         }
         try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
                );
                if (authentication.isAuthenticated()) {
                    String token = jwtUtil.generateToken(user.getUsername());
                    return new ResponseEntity<>(token, HttpStatusCode.valueOf(200));
                } else {
                    return new ResponseEntity<>("Invalid credentials", HttpStatusCode.valueOf(401));
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Invalid credentials", HttpStatusCode.valueOf(401));
         }

    }
}
