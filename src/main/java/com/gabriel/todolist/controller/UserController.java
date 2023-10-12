package com.gabriel.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabriel.todolist.model.User;
import com.gabriel.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }
    @PostMapping
    public ResponseEntity<Object> getAllUsers(@RequestBody User user){
        var userReponse = this.userRepository.findByUsername(user.getUsername());
        if(userReponse == null) {
            var passwordHash = BCrypt.withDefaults().hashToString(12,user.getPassword().toCharArray());
            user.setPassword(passwordHash);
            var useCreated = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(useCreated);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");

    }
}
