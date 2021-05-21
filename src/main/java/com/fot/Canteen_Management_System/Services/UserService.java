package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Integer getusercount(){
        List<User> users= (List<User>) userRepository.findAll();
        return  users.size();
    }

    public User getUserId(Integer id){
        return userRepository.findById(id).get();
    }

    public Boolean updateProfile(Integer id,User user){
        User user1=userRepository.findById(id).get();
        if(userRepository.findById(id).isPresent()) {
            user1.setEmail(user.getEmail());
            user1.setMobile(user.getMobile());
            userRepository.save(user1);
            return true;
        }else {
            return false;
        }
    }
}
