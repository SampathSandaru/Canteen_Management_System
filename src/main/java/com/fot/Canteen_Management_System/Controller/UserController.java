package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import com.fot.Canteen_Management_System.Services.ItemService;
import com.fot.Canteen_Management_System.Services.OrderItemService;
import com.fot.Canteen_Management_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/")
    public String index(){
        return "test";
    }

    @RequestMapping(path = "/loginpage",method = RequestMethod.GET)
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
        return "redirect:/loginpage";
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
            users.add(newuser.getRole());

            request.getSession().setAttribute("USER_SESSION",users);

            if(newuser.getRole().equals("canteenmanager")){
                return "redirect:/dash";
            }else if(newuser.getRole().equals("user")){
                return "redirect:/userdash";
            }else{
                return "redirect:/";
            }

        }else{
            return "redirect:/loginpage?incorrectDetail";
        }

    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/loginpage";
    }

    @RequestMapping(path = "item",method = RequestMethod.GET)
    public String home(Model model,HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        OrderItem orderItem=new OrderItem();

        if(users==null){
            return "redirect:/loginpage";
        }else{
            model.addAttribute("items",itemService.getuserItem());
            model.addAttribute("users",users);
            model.addAttribute("order_item",orderItem);
            return "User/item";
        }
    }

    @RequestMapping(path = "OrderHistory",method = RequestMethod.GET)
    public String OrderHistory(Model model,HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        Integer user_id=Integer.parseInt(users.get(0));

        List<OrderItemDto> user_Order_item= orderItemRepository.getorderHistory(user_id); //join query

        if(users==null){
            return "redirect:/loginpage";
        }else{
            model.addAttribute("user_Order_item",user_Order_item);
            model.addAttribute("items",itemService.getuserItem());
            model.addAttribute("users",users);
//            model.addAttribute("order_item",orderItem);
            return "User/OrderHistory";
        }
    }

    @GetMapping("userdash")
    public String userdash(Model model,HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");

        model.addAttribute("users",users);
        return "User/user_dash";
    }

    @RequestMapping(path = "/orderitem",method = RequestMethod.POST)
    public String orderitem(@ModelAttribute("OrderItem")OrderItem orderItem, HttpSession session){
        List<String> users= (List<String>) session.getAttribute("USER_SESSION");
        orderItem.setU_id(Integer.parseInt(users.get(0)));
        LocalDate date = LocalDate.now();

        float tot=orderItem.getQuantity()*orderItem.getPrice();
        orderItem.setPrice(tot);
        orderItem.setOrder_time(date);
        itemService.reduce(orderItem.getQuantity(),orderItem.getItem_id());

        orderItemService.save(orderItem);

        return "redirect:/item?ordersuccess";
    }
}
