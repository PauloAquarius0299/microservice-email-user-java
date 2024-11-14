package com.paulotech.user_microservice.services;

import com.paulotech.user_microservice.models.UserModel;
import com.paulotech.user_microservice.producers.UserProducer;
import com.paulotech.user_microservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer){
        this.userRepository = userRepository;
        this.userProducer =  userProducer;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }
}
