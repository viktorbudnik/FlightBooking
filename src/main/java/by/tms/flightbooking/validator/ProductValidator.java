package by.tms.flightbooking.validator;

import by.tms.flightbooking.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> Class1) {
        return Product.class.equals(Class1);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Product product = (Product) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.not_empty");

        if (product.getName().length() <= 1) {
            errors.rejectValue("name", "product.error.name.less_2");
        }
        if (product.getName().length() > 20) {
            errors.rejectValue("name", "product.error.name.over_20");
        }
        if (product.getPrice().signum() != 1) {
            errors.rejectValue("price", "product.error.price");
        }
    }
}
