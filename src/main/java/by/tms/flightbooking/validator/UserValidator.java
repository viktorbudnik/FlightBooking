package by.tms.flightbooking.validator;

import by.tms.flightbooking.model.User;
import by.tms.flightbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

@Component

public class UserValidator implements Validator {
    @Autowired
    protected UserRepository userRepository;

    @Override
    public boolean supports(Class<?> Class1) {
        return User.class.equals(Class1);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");
        if (user.getUsername().length() < 4) {
            errors.rejectValue("username", "register.error.username.less_4");
        }
        if (user.getUsername().length() > 20) {
            errors.rejectValue("username", "register.error.username.over_20");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "register.error.duplicated.username");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "register.error.duplicated.email");
        }
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "register.error.password.less_6");
        }
        if (user.getPassword().length() > 20) {
            errors.rejectValue("password", "register.error.password.over_20");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "register.error.diff_password");
        }
    }
}
