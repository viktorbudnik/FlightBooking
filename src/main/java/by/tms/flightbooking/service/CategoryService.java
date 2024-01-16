package by.tms.flightbooking.service;

import by.tms.flightbooking.model.Category;
import by.tms.flightbooking.repository.CategoryRepository;
import by.tms.flightbooking.service.CategoryServiceInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService implements CategoryServiceInt {
    protected CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public boolean deleteCategory(Long categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean saveCategory(Category category) {
        Category categoryFromDB = categoryRepository.findByName(category.getName());
        if (categoryFromDB != null) {
            return false;
        }
        categoryRepository.save(category);
        return true;
    }
}