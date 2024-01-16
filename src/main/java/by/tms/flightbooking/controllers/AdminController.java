package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.OrderProductMap;
import by.tms.flightbooking.model.User;
import by.tms.flightbooking.repository.OrderProductMapRepository;
import by.tms.flightbooking.repository.OrderRepository;
import by.tms.flightbooking.repository.UserRepository;
import by.tms.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductMapRepository orderProductMapRepository;

    public AdminController(UserService userService, UserRepository userRepository, OrderRepository orderRepository, OrderProductMapRepository orderProductMapRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderProductMapRepository = orderProductMapRepository;
    }
    @GetMapping
    public String adminHome(){
        return "admin/admin";
    }
    @GetMapping("/editUsers")
    public String userList(Model model){
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("roleUsers", userService.findAll());

        return "admin/editUsers";
    }
    @GetMapping("editUsers/{id}")
    public String userId(@PathVariable("id")Long id, Model model){
        User userCard = userRepository.findById(id).get();
        model.addAttribute("userCard", userCard);

        return "admin/userCard";
    }
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/editUsers";
    }
    @GetMapping("/orders")
    public String ordersUsers(Model model) {
        System.out.println(orderRepository.findAll());
        model.addAttribute("AllOrders", orderRepository.findAll());

        return "admin/adminOrders/adminOrdersUser";
    }
    @GetMapping("/orders/view/{id}")
    public String orderId(@PathVariable("id") Long id, Model model) {
        List<OrderProductMap> allByOrderId = orderProductMapRepository.findAllByOrderId(id);
        if (!CollectionUtils.isEmpty(allByOrderId)) {
            model.addAttribute("OrdersProducts", orderProductMapRepository.findAllByOrderId(id));
        }

        return "admin/adminOrders//productOfOrder";
    }

}
