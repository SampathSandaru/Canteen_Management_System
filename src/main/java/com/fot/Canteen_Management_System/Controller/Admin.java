package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
//import com.fot.Canteen_Management_System.Entity.Invoices;
import com.fot.Canteen_Management_System.Entity.Item;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import com.fot.Canteen_Management_System.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class Admin {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private SendEmailService sendEmailService;

    @RequestMapping(path = "/dash",method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
            model.addAttribute("itemcount",itemService.allItemCount());
            model.addAttribute("newOrderCount",orderItemService.newOrderCount());
            model.addAttribute("usercount",userService.getusercount());
            model.addAttribute("users",users);
            model.addAttribute("summary",invoicesService.getsummary());
            return "canteenManager/dash";
        }else{
            return "redirect:/?access denied";
        }
    }

    @RequestMapping(path = "newOrder",method = RequestMethod.GET)
    public String newOrder(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        List<OrderItemDto> user_Order_item= orderItemRepository.getorder(); //join query
//        Invoices invoices=new Invoices();

        model.addAttribute("user_Order_item",user_Order_item);
//        model.addAttribute("invoices",invoices);
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
        model.addAttribute("items",items); // list  all item  (item page)
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
            return "canteenManager/cm_item";
        }else{
            return "redirect:/?access denied";
        }
    }

    @RequestMapping(path = "/invoice",method = RequestMethod.POST)
    public String invoice(@RequestParam("order_id")Integer orderid){
        invoicesService.save(orderid);
        return "redirect:/newOrder?success";
    }

    @GetMapping(path = "/admin")
    public String admin_dash(HttpSession session,Model model){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("admin")){
            model.addAttribute("items",itemService.getAllItem());
            model.addAttribute("usercount",userService.getusercount());
            model.addAttribute("itemcount",itemService.allItemCount());
            model.addAttribute("newOrderCount",orderItemService.newOrderCount());
            return "Admin/dash";
        }
    }

    @GetMapping(path = "/new_user")
    public String new_user(HttpSession session,Model model){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else{
            model.addAttribute("nesusers",userService.newuser());
            return "Admin/new_user";
        }
    }

    @GetMapping(path = "/users")
    public String users(HttpSession session,Model model){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else{
            model.addAttribute("allnuser",userService.allnuser());
            return "Admin/users";
        }
    }

    @GetMapping(path = "/add_canteen_manager")
    public String add_canteen_manager(HttpSession session,Model model){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else{
            User user=new User();
            model.addAttribute("user",user);
            return "Admin/add_canteen_manager";
        }
    }

    @RequestMapping(path = "/reg_CA",method = RequestMethod.POST)
    public String reg_new_user(@ModelAttribute("User") User user){
        int max=9999;
        int min=1000;
        int pwd = (int) ((Math.random()*(max-min))+min);
        String password=String.valueOf(pwd);

        sendEmailService.sendEmial(user.getEmail(),"Your Password : "+password,"Password");
        user.setPassword(password);
        user.setRole("canteenmanager");
        userService.save(user);
        return "redirect:/loginpage";
    }

    @RequestMapping(path = "/approveuser",method = RequestMethod.POST)
    public String approveuser(HttpSession session,@RequestParam("userid")Integer userid){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        userService.approveuser(userid);
        if(users==null){
            return "redirect:/loginpage";
        }else{
            return "redirect:/new_user";
        }
    }
}
