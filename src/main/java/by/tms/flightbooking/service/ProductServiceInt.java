package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Product;

import java.util.List;

public interface ProductServiceInt {
    List<Product> findAll();
    boolean deleteProduct(Long id);
    boolean saveProduct(Product id);
    void updateProduct(Long id, Product product);
    long productCount();
    List<Product> findAllByCategoryId(long categoryId);
    Product findById(long id);
    long count();
}
