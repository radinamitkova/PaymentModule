package bg.codeacademy.spring.paymentmodule.validation;

public interface PasswordAndUsernameValidation {
    boolean isValidPassword(String password, String confirmPassword);

    boolean isValidUsername(String username);
}
