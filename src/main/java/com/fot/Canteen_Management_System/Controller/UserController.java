package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public String login(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String register(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "register";
    }

    @RequestMapping(path = "/reg_user",method = RequestMethod.POST)
    public String reg_new_user(@ModelAttribute("User") User user){
        userService.save(user);
        return "login";
    }

    @PostMapping("/login")
    private String login(@ModelAttribute("user")User user, HttpServletRequest request){
        User newuser=userService.login(user.getEmail(),user.getPassword());
        System.out.println("user : "+newuser);
        if(Objects.nonNull(newuser)){
            return "redirect:/dash";
        }else{
            return "redirect:/?error";
        }

    }

    @RequestMapping(path = "item",method = RequestMethod.GET)
    public String home(){
        return "User/item";
    }

    @RequestMapping(path = "userdash",method = RequestMethod.GET)
    public String userdash(){
        return "User/user_dash";
    }
}
