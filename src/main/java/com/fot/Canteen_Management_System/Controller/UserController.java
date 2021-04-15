package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "test";
    }

    @RequestMapping(path = "/login",method = RequestMethod.GET)
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
    private String login(@ModelAttribute("user")User user, HttpServletRequest request, HttpSession session){
        User newuser=userService.login(user.getEmail(),user.getPassword());

        List<String> users= (List<String>) request.getSession().getAttribute("USER_SESSION");

        System.out.println("user : "+newuser);
        if(Objects.nonNull(newuser)){
            if (users == null)
            {
                users = new ArrayList<>();
                request.getSession().setAttribute("USER_SESSION",users);
            }
            users.add(newuser.getId().toString());
            users.add(newuser.getName());
            users.add(newuser.getEmail());

            request.getSession().setAttribute("USER_SESSION",users);

            return "redirect:/dash";
        }else{
            return "redirect:/login?error";
        }

    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/login";
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
