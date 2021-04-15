package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public User login(String email,String password){
        User user=userRepository.findByEmailAndPassword(email,password);
        return user;
    }
}
