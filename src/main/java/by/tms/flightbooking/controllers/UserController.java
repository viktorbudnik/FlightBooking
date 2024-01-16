package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.User;
import by.tms.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    protected UserService userService;
    @GetMapping String userHome(Model model){
        return "user/userHome";
    }
    @GetMapping("/update")
    public String editProduct(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userUpdate", user);
        return "user/updateProfile";
    }
    @PostMapping("/update")
    @Transactional
    public String updateProduct(@ModelAttribute("userUpdate") User user){
        Long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        userService.updateUser(id, user);
        return "redirect:/user";
    }
}
