package by.tms.flightbooking.controllers;

import by.tms.flightbooking.model.Product;
import by.tms.flightbooking.repository.ProductRepository;
import by.tms.flightbooking.service.CategoryService;
import by.tms.flightbooking.service.ProductService;
import by.tms.flightbooking.validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/product")

public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    protected ProductService productService;
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected ProductValidator productValidator;

    public ProductController(ProductService productService, CategoryService categoryService, ProductRepository productRepository, ProductValidator productValidator) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "admin/product/product";
    }

    @GetMapping("/new")
    public String newProducts(Model model) {
        model.addAttribute("newProductForm", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product/newProduct";
    }

    @PostMapping("/new")
    public String createNewProduct(@ModelAttribute("newProductForm") Product newProductForm, BindingResult bindingResult, Model model) {
        productValidator.validate(newProductForm, bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "admin/product/newProduct";
        }
        if (!productService.saveProduct(newProductForm)) {
            model.addAttribute("productNameError", "This name is already taken");
            return "admin/product/newProduct";
        }
        logger.debug(String.format("Product with id: %s created.", newProductForm.getId()));
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        logger.debug(String.format("Product with id: %s has been deleted.", id));
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        if (!productRepository.existsById(id)) {
            return "redirect:admin/product";
        }
        model.addAttribute("editProduct", productRepository.findById(id).get());
        model.addAttribute("categories", categoryService.findAll());

        return "admin/product/editProduct";
    }

    @PostMapping("/edit/{id}")
    @Transactional
    public String updateProduct(@PathVariable("id") long id, @ModelAttribute("editProduct") Product editProduct, BindingResult bindingResult) {
        productValidator.validate(editProduct, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "admin/product/editProduct";
        }
        productService.updateProduct(id, editProduct);
        logger.debug(String.format("Product with id: %s has been update.", id));
        return "redirect:/admin/product";
    }
}
