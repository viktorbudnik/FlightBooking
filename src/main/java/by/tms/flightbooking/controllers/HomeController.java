package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.Category;
import by.tms.flightbooking.repository.CategoryRepository;
import by.tms.flightbooking.service.CategoryService;
import by.tms.flightbooking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected ProductService productService;

    public HomeController(CategoryService categoryService, CategoryRepository categoryRepository, ProductService productService) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }
    @GetMapping(value = {"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productsCount", productService.productCount());
        model.addAttribute("products", productService.findAll());

        return "index";
    }
    @GetMapping(value = {"/about"})
    public String about(){
        return "about";
    }
    @RequestMapping("/searchByCategory/{id}")
    public String homePost(@PathVariable("id") long categoryId, Model model) {
        Category category = categoryRepository.findById(categoryId).get();
        model.addAttribute("productCategory", productService.findAllByCategoryId(categoryId));
        model.addAttribute("productsCount", productService.productCount());
        model.addAttribute("category", category);
        return "productFromCategory";
    }

}
