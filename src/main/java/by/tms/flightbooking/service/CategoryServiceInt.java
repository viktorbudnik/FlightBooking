package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Category;

import java.util.List;

public interface CategoryServiceInt {
    List<Category> findAll();
    boolean deleteCategory(Long id);
    boolean saveCategory(Category id);
}
