package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.ChangePwdLog;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        String pwd=user.getPassword();
        String hash_pwd= DigestUtils.sha1Hex(pwd);
        user.setPassword(hash_pwd);
        userRepository.save(user);
    }

    public User login(String email,String password){
        String hash_pwd= DigestUtils.sha1Hex(password);
        User user=userRepository.findByEmailAndPassword(email,hash_pwd);
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

            String hash_current_pwd= DigestUtils.sha1Hex(current_pwd);
            String new_password= DigestUtils.sha1Hex(new_pwd);

            if(user.getPassword().equals(hash_current_pwd)){
                user.setPassword(new_password);
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

    public User forget_pwd(String email){
        User user= userRepository.findByemail(email);
        return user;
    }

    public Boolean reset_password(String password,Integer id) {
        String new_pwd= DigestUtils.sha1Hex(password);
        if (userRepository.findById(id).isPresent()){
            User user=userRepository.findById(id).get();
            user.setPassword(new_pwd);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public List<ChangePwdLog> pwdchnagedate(Integer id){
        System.out.println(id);
         return  userRepository.pwdchnagedate(id);
    }
}
