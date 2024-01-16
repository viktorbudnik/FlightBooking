package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.Product;
import by.tms.flightbooking.repository.OrderProductMapRepository;
import by.tms.flightbooking.repository.OrderRepository;
import by.tms.flightbooking.repository.ProductRepository;
import by.tms.flightbooking.repository.UserRepository;
import by.tms.flightbooking.service.OrderService;
import by.tms.flightbooking.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
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
    @Autowired
    protected UserRepository userRepository;

    public CartController(ShoppingCartService shoppingCartService, ProductRepository productRepository, OrderRepository orderRepository, OrderProductMapRepository orderProductMapRepository, OrderService orderService, UserRepository userRepository) {
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductMapRepository = orderProductMapRepository;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("products", shoppingCartService.productsInCart());
        model.addAttribute("totalPrice", shoppingCartService.totalPrice());

        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") long id) {
        Product product = productRepository.findById(id).get();
        if (product != null) {
            shoppingCartService.addProduct(product);
            logger.debug(String.format("Product with id: %s added to cart.", id));
        }
        return "redirect:/index";
    }

    @GetMapping("/cartfromcateg/add/{id}")
    public String addProdToCart(@PathVariable("id") long id) {
        Product product = productRepository.findById(id).get();
        if (product != null) {
            shoppingCartService.addProduct(product);
            logger.debug(String.format("Product with id: %s added to cart.", id));
        }
        return "redirect:/searchByCategory/" + product.getCategory().getId();
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") long id) {
        Product product = productRepository.findById(id).get();
        if (product != null) {
            shoppingCartService.removeProduct(product);
            logger.debug(String.format("Product with id: %s removed from cart.", id));
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearProductsInCart() {
        shoppingCartService.clearProducts();

        return "redirect:/cart";
    }

}
