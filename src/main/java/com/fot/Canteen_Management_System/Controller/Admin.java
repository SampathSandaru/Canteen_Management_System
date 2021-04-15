package com.fot.Canteen_Management_System.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Admin {

    @RequestMapping(path = "/dash",method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/login";
        }else{
            return "canteenManager/dash";
        }
    }

    @RequestMapping(path = "newOrder",method = RequestMethod.GET)
    public String newOrder(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        model.addAttribute("users",users);

        return "canteenManager/NewOrder";
    }
}
