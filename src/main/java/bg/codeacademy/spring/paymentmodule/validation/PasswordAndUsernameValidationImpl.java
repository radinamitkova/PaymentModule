package bg.codeacademy.spring.paymentmodule.validation;

import bg.codeacademy.spring.paymentmodule.dao.LoginDetailsDaoImpl;
import bg.codeacademy.spring.paymentmodule.exception.ElementAlreadyExistsException;
import bg.codeacademy.spring.paymentmodule.exception.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class PasswordAndUsernameValidationImpl implements PasswordAndUsernameValidation {

    private final LoginDetailsDaoImpl loginDetailsDao;

    public PasswordAndUsernameValidationImpl(LoginDetailsDaoImpl loginDetailsDao) {
        this.loginDetailsDao = loginDetailsDao;
    }

    @Override
    public boolean isValidPassword(String password, String confirmPassword) {

        StringBuilder passwordExceptions = new StringBuilder();
        boolean isValid = true;

        if (!(password.equals(confirmPassword))) {
            passwordExceptions.append("Passwords don't match! ");
            isValid = false;

        }
        if (password.length() < 8) {
            passwordExceptions.append("Password must be more than 8 symbols! ");
            isValid = false;

        }
        if (!(password.matches(".*\\d.*"))) {
            passwordExceptions.append("Password must contain digit! ");
            isValid = false;

        }
        if (!(password.matches(".*[@_-].*"))) {
            passwordExceptions.append("Password must have at least one special symbol among ['@', '_', '-']  ");
            isValid = false;

        }
        if (!(password.matches(".*[A-Z].*"))) {
            passwordExceptions.append("Password must contain uppercase letters! ");
            isValid = false;

        }
        if (!(password.matches(".*[a-z].*"))) {
            passwordExceptions.append("Password must contain lowercase letters! ");
            isValid = false;

        }
        if (!isValid) {
            throw new InvalidDataException(passwordExceptions.toString());

        }

        return true;
    }

    @Override
    public boolean isValidUsername(String username) {

        if (this.loginDetailsDao.getByUsername(username) != null) {
            throw new ElementAlreadyExistsException("Username already exists!");
        }
        //--- username: Lowercase latin letters, digits, dot and dash allowed only!
        if (!(username.matches("[\\w.-]{3,30}"))) {
            throw new InvalidDataException("You must enter valid username!");
        }

        return true;
    }
}
