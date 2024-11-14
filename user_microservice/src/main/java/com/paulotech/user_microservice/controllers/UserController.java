package com.paulotech.user_microservice.controllers;


import com.paulotech.user_microservice.dtos.UserRecordDTO;
import com.paulotech.user_microservice.models.UserModel;
import com.paulotech.user_microservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> saveUser(@Valid @RequestBody UserRecordDTO userRecordDTO) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }


}
