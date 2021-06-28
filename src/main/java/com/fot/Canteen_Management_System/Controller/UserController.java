package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Dto.UserPwd;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import com.fot.Canteen_Management_System.Repository.UserRepository;
import com.fot.Canteen_Management_System.Services.ItemService;
import com.fot.Canteen_Management_System.Services.OrderItemService;
import com.fot.Canteen_Management_System.Services.SendEmailService;
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
import java.util.Optional;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendEmailService sendEmailService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(path = "/loginpage",method = RequestMethod.GET)
    public String login(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "loginNew";
    }

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String register(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "register1";
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
        if(Objects.nonNull(newuser)) {
            if (users == null) {
                users = new ArrayList<>();
                request.getSession().setAttribute("USER_SESSION", users);
            }
            users.add(newuser.getId().toString());
            users.add(newuser.getName());
            users.add(newuser.getEmail());
            users.add(newuser.getRole());

            request.getSession().setAttribute("USER_SESSION", users);

            if (newuser.getRole().equals("canteenmanager") && newuser.getApprove() == 1) {
                return "redirect:/dash";
            } else if (newuser.getRole().equals("user") && newuser.getApprove() == 1) {
                return "redirect:/item";
            }else if(newuser.getRole().equals("admin") && newuser.getApprove()==1){
                return "redirect:/admin";
            }else{
                return "redirect:/loginpage?notapproved";
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

        if(users==null){
            return "redirect:/loginpage";
        }else{
            model.addAttribute("users",users);
            return "User/user_dash";
        }
    }


    @GetMapping("/profile")
    public String profile(HttpSession session,Model model){
        List<String> user= (List<String>) session.getAttribute("USER_SESSION");

        if(user==null){
            return "redirect:/loginpage";
        }else {
            Integer userId=Integer.parseInt(user.get(0));
            User user1=userService.getUserId(userId);

            List<UserPwd> user2=userRepository.pwdchnagedate(userId);
            model.addAttribute("pwdchagedate",user2);
            model.addAttribute("users",user);
            model.addAttribute("users_obj",user1);
            return "User/user_profile";
        }
    }

    @RequestMapping(path = "/update_profile",method = RequestMethod.POST)
    public String update_profile(@ModelAttribute("User")User user,HttpSession session){
        List<String> user1= (List<String>) session.getAttribute("USER_SESSION");

        Integer userId=Integer.parseInt(user1.get(0));
        if(userService.updateProfile(userId,user)){
            return "redirect:/profile?success";
        }else{
            return "redirect:/profile?error";
        }
    }

    @RequestMapping(path = "/chang_pwd",method = RequestMethod.POST)
    public String chang_pwd(HttpSession session,@RequestParam("current_pwd")String current_pwd,@RequestParam("new_pwd")String new_pwd,@RequestParam("confim_pwd")String confirm_pwd){
        List<String> user1= (List<String>) session.getAttribute("USER_SESSION");

        Integer id=Integer.parseInt(user1.get(0));
       if(new_pwd.equals(confirm_pwd)){
           if(userService.chang_password(current_pwd,id,new_pwd)){
               return "redirect:/profile?chngpwdsuccess";
           }else{
               return "redirect:/profile?incorect_current_pwd";
           }
       }else{
           return "redirect:/profile?dosentmachpwd";
       }
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

    @GetMapping(path = "/email")
    public String email(){
        return "email1";
    }

    @GetMapping(path = "/reset_code")
    public String reset_code(){
        return "reset_code1";
    }

    @GetMapping(path = "/reset_pwd")
    public String reset_pwd(){
        return "reset_pwd1";
    }

    @RequestMapping(path = "/forget_pwd_email",method = RequestMethod.POST)
    public String codepage(HttpSession session,@RequestParam("email")String email){
        User user=userService.forget_pwd(email);
        if(Objects.nonNull(user)){
            int max=9999;
            int min=1000;
            int code = (int) ((Math.random()*(max-min))+min);
            String s_cod=String.valueOf(code);
            session.setAttribute("reset_code",s_cod);
            session.setAttribute("id",user.getId());
            sendEmailService.sendEmial(email,"Your Password Reset Code : "+code,"Reset Code");
            return "redirect:/reset_code?success";
        }else{
            return "redirect:/email?error";
        }
    }

    @RequestMapping(path = "/pwd_reset_code",method = RequestMethod.POST)
    public String pwd_reset_code(HttpSession session,@RequestParam("resetcode")String code){
        if(session.getAttribute("reset_code").equals(code)){
            System.out.println("OK "+session.getAttribute("reset_code"));
            return "redirect:/reset_pwd";
        }else{
            System.out.println("erro "+session.getAttribute("reset_code"));
            return "redirect:/reset_code?error";
        }
    }

    @RequestMapping(path = "/reset_pwd",method = RequestMethod.POST)
    public String reset_pwd(HttpSession session,@RequestParam("confirm_pwd")String confirm_pwd,@RequestParam("pwd")String pwd){
        Integer id= (Integer) session.getAttribute("id");
        if(pwd.equals(confirm_pwd)){
            if(userService.reset_password(pwd,id)){
                return "redirect:/loginpage";
            }else{
                return "redirect:/reset_code";
            }
        }else
            return "redirect:/reset_pwd?error_matchPwd";
    }
}
