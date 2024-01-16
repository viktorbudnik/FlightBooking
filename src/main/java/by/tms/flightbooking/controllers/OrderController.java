package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.Order;
import by.tms.flightbooking.model.OrderProductMap;
import by.tms.flightbooking.model.Product;
import by.tms.flightbooking.model.User;
import by.tms.flightbooking.repository.OrderProductMapRepository;
import by.tms.flightbooking.repository.OrderRepository;
import by.tms.flightbooking.repository.ProductRepository;
import by.tms.flightbooking.service.OrderService;
import by.tms.flightbooking.service.ShoppingCartService;
import by.tms.flightbooking.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.SocketAddress;
import java.security.Security;
import java.time.LocalDate;
import java.util.Map;
import static by.tms.flightbooking.enums.Status.ACCEPTED;

@RequestMapping("/user")
@Controller
public class OrderController {
    @Autowired
    protected ShoppingCartService shoppingCartService;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected OrderProductMapRepository orderProductMapRepository;
    @Autowired
    protected OrderService orderService;

    public OrderController(ShoppingCartService shoppingCartService, ProductRepository productRepository, OrderRepository orderRepository, OrderProductMapRepository orderProductMapRepository, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductMapRepository = orderProductMapRepository;
        this.orderService = orderService;
    }
    @GetMapping("/cartToOrder")
    public String cartToOrder(){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<Product, Integer> mapCart = shoppingCartService.getCart();
        System.out.println("MapCart=" + mapCart);
        Order newOrder= new Order();
        Status newStatus= ACCEPTED;
        newOrder.setStatus(newStatus.toString());
        newOrder.setUser(user);
        newOrder.setGrand_total(shoppingCartService.totalPrice());
        newOrder.setDate_of_order(LocalDate.now());
        newOrder.setDescription("My order");
        System.out.println("New Order=" + newOrder);
        orderService.saveOrder(newOrder);
        System.out.println("OrderAfterSave=" + newOrder);

        for (Map.Entry<Product, Integer> entry : mapCart.entrySet()){
            OrderProductMap orderProductMap=new OrderProductMap();
            System.out.println("ID = "+entry.getKey() + "value = " + entry.getValue());
            orderProductMap.setOrder(newOrder);
            orderProductMap.setOrderid(newOrder.getId());
            orderProductMap.setProduct(entry.getKey());
            orderProductMap.setQuantity(entry.getValue());
            orderProductMap.setPrice(entry.getKey().getPrice());
            orderProductMapRepository.save(orderProductMap);
        }
        shoppingCartService.clearProducts();
        return "redirect:/index";
    }
    @GetMapping("/orders")
    public String userList(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("My order " + user + orderRepository.findById(user.getId()));
        model.addAttribute("OrdersUser", orderRepository.findByUser(user));

        return "order/ordersUser";
    }
    @GetMapping("/orders/view/{id}")
    public String userid(@PathVariable("id") Long id, Model model){
        Order order = orderService.findOrder(id).get();
        System.out.println("Order = "+order);
        model.addAttribute("OrdersProducts", orderProductMapRepository.findAllByOrderId(order.getId()));

        return "order/order";
    }
    @GetMapping("/delete/order/{id}")
    public String deleteUser(@PathVariable("id") long id){
        orderService.deleteOrder(id);
        return "redirect:/user/orders";
    }
}
