package com.fot.Canteen_Management_System.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Admin {

    @RequestMapping(path = "/dash",method = RequestMethod.GET)
    public String home(){
        return "canteenManager/dash";
    }

    @RequestMapping(path = "newOrder",method = RequestMethod.GET)
    public String newOrder(){
        return "canteenManager/NewOrder";
    }
}
