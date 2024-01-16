package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartServiceInt {
    void addProduct(Product product);
    void removeProduct(Product product);
    void clearProducts();
    Map<Product, Integer> productsInCart();
    BigDecimal totalPrice();
}
