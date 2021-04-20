package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Entity.Item;
import com.fot.Canteen_Management_System.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Admin {

    @Autowired
    private ItemService itemService;

    @RequestMapping(path = "/dash",method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
//            System.out.println(users.get(3));
            return "canteenManager/dash";
        }else{
            return "redirect:/?access denied";
        }
    }

    @RequestMapping(path = "newOrder",method = RequestMethod.GET)
    public String newOrder(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
            return "canteenManager/NewOrder";
        }else{
            return "redirect:/?access denied";
        }
    }

    @RequestMapping(path = "canteenmanager_item",method = RequestMethod.GET)
    public String canteenmanager_item(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        Item item=new Item();
        List<Item> items=itemService.getAllItem();

        model.addAttribute("cm_item",item);
        model.addAttribute("items",items); // list  all item
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
            return "canteenManager/cm_item";
        }else{
            return "redirect:/?access denied";
        }
    }
}
