package app.controller;

import app.entity.Role;
import app.entity.User;
import app.repository.RoleRepo;
import app.repository.UserRepo;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
////@RequestMapping("/auth")
public class MainController {
    @RequestMapping("/users")
    public String pageForRest(){
        return "admin";
    }
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/hello")
    public String getSuccessPage() {
        return "hello";
    }

    //@PostMapping( "/user")
    @RequestMapping(value = "/admin")
    public String pageAllUsers(Principal principal, Model model) {
        List<User> list = userService.findAll();
        //User user = userService.findByUsername(principal.getName());
        model.addAttribute("list", list);
        User u = userService.findByUsername(principal.getName());
        model.addAttribute("user", u);
        //model.addAttribute("user", user);
        Role role = roleRepo.findById(u.getId()).get();
//        String str = role.toString();
        model.addAttribute("role", role);
        return "admin";
        //return "secured part of web service" + user.getUsername() + " " + user.getEmail();
    }
    //@RequestMapping(value = "/delete", method = RequestMethod.POST)
    @GetMapping("/delete")
    private String deleteUser(@RequestParam Long id, Model model){
        userService.deleteById(id);
        System.out.println("User_Id : ");
        model.addAttribute("list", userService.findAll());
        return "redirect:/admin";
    }
    @RequestMapping(value = "/user")
    public String pageForSingleUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
//        Role role = (Role) user.getRoles();
//        String str = role.toString();
//        System.out.println(str);
        Role role = roleRepo.findById(user.getId()).get();
//        String str = role.toString();
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "user";
    }
    //@RequestMapping(value = "/admin", method = RequestMethod.POST)
    @PostMapping("/add")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String email,
                      Model model){
        User user = new User(username, password, email);
        userService.save(user);
//        List<User> list = userService.findAll();
//        model.addAttribute("list", list);
        return "redirect:/admin";
    }
    @GetMapping("/findOne")
    @ResponseBody
    public User findOne(long id) {
        return userService.findById(id);
    }
    @PostMapping("/save")
    public String save(User user) {
        userService.save(user);
        return "redirect:/admin";
    }
}
