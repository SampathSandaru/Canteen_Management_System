package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Dto.UserPwd;
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

    public Boolean chang_password(String current_pwd,Integer id,String new_pwd){
        if(userRepository.findById(id).isPresent()){
            User user=userRepository.findById(id).get();
            if(user.getPassword().equals(current_pwd)){
                System.out.println(user.getPassword());
                user.setPassword(new_pwd);
                userRepository.save(user);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public List<User> newuser(){
        return userRepository.allnewuser();
    }

    public List<User> allnuser(){
        return userRepository.allnuser();
    }

    public void approveuser(Integer userid){
        if(userRepository.findById(userid).isPresent()){
           User user=userRepository.findById(userid).get();
           user.setApprove(1);
           userRepository.save(user);
        }
    }
}
