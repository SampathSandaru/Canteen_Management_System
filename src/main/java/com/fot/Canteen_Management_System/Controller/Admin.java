package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
//import com.fot.Canteen_Management_System.Entity.Invoices;
import com.fot.Canteen_Management_System.Entity.Item;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import com.fot.Canteen_Management_System.Services.InvoicesService;
import com.fot.Canteen_Management_System.Services.ItemService;
import com.fot.Canteen_Management_System.Services.OrderItemService;
import com.fot.Canteen_Management_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

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

    @RequestMapping(path = "/dash",method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        model.addAttribute("itemcount",itemService.allItemCount());
        model.addAttribute("newOrderCount",orderItemService.newOrderCount());
        model.addAttribute("usercount",userService.getusercount());
        model.addAttribute("users",users);

        if(users==null){
            return "redirect:/loginpage";
        }else if(users.get(3).equals("canteenmanager")){
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

//    @RequestMapping(path = "/invoice",method = RequestMethod.POST)
//    public String invoice(@ModelAttribute("invoices")Invoices invoices){
//        LocalDate date = LocalDate.now();
//        invoices.setI_date(date);
//        invoicesService.save(invoices);
//        return "redirect:/newOrder?success";
//    }

    @GetMapping(path = "/admin")
    public String admin_dash(HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else{
            return "Admin/dash";
        }
    }

    @GetMapping(path = "/new_user")
    public String new_user(HttpSession session,Model model){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        List<User> nesusers=userService.newuser();

        model.addAttribute("nesusers",nesusers);

        if(users==null){
            return "redirect:/loginpage";
        }else{
            return "Admin/new_user";
        }
    }

    @GetMapping(path = "/users")
    public String users(HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        if(users==null){
            return "redirect:/loginpage";
        }else{
            return "Admin/users";
        }
    }

    @RequestMapping(path = "/approveuser",method = RequestMethod.POST)
    public String approveuser(HttpSession session,@RequestParam("userid")Integer userid){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        userService.approveuser(userid);
        if(users==null){
            return "redirect:/loginpage";
        }else{
            return "Admin/new_user";
        }
    }
}
