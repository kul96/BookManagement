package com.example.bookManagement.controller;

import com.example.bookManagement.entity.RegisterUserRequest;
import com.example.bookManagement.entity.Role;
import com.example.bookManagement.entity.UserSecurityDetails;
import com.example.bookManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserRequest> addUser(@RequestBody RegisterUserRequest userRequest) {
        userRequest.setRole(Role.USER); // role is optional so manually set
        UserSecurityDetails userCreated = userService.registerUser(userRequest);
        RegisterUserRequest newUser = new RegisterUserRequest(userCreated.getUsername(), "", userCreated.getRole());
        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasRole('ADMIN')") // only admin create another admin
    @PostMapping("/adminCreate")
    public ResponseEntity<RegisterUserRequest> addAdmin(@RequestBody RegisterUserRequest userRequest) {
        UserSecurityDetails userCreated = userService.registerUser(userRequest);
        RegisterUserRequest newUser = new RegisterUserRequest(userCreated.getUsername(), "", userCreated.getRole());
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegisterUserRequest>> getAllUser() {
        List<UserSecurityDetails> users = userService.getAllUser();
        List<RegisterUserRequest> userList = users.stream()
                                                  .map(user -> new RegisterUserRequest(user.getUsername(), "",
                                                                                       user.getRole()
                                                  ))
                                                  .toList();
        return ResponseEntity.ok(userList);
    }

}
