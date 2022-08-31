package bg.codeacademy.spring.paymentmodule.controller;

import bg.codeacademy.spring.paymentmodule.exception.BadRequestException;
import bg.codeacademy.spring.paymentmodule.exception.InvalidDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.security.AppUser;
import bg.codeacademy.spring.paymentmodule.service.ClientService;
import bg.codeacademy.spring.paymentmodule.service.EmailService;
import bg.codeacademy.spring.paymentmodule.service.LoginDetailsService;
import bg.codeacademy.spring.paymentmodule.service.TokenService;
import bg.codeacademy.spring.paymentmodule.validation.PasswordAndUsernameValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/")
public class ClientController {

    private final ClientService clientService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final LoginDetailsService loginDetailsService;
    private PasswordAndUsernameValidation validation;

    public ClientController(ClientService clientService, TokenService tokenService,
                            EmailService emailService, LoginDetailsService loginDetailsService,
                            PasswordAndUsernameValidation validation) {
        this.clientService = clientService;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.loginDetailsService = loginDetailsService;
        this.validation = validation;
    }

    @PostMapping(value = "register/{token}")
    public ResponseEntity<?> verifyClient(@PathVariable String token, @RequestBody Client newClient) {
        String user_id = tokenService.getClientIdByTokenBody(token);
        Client client = clientService.getClientByID(user_id);

        if (client.getId().equals(newClient.getId()) && client.getName().equals(newClient.getName())
                && client.getEmail().equals(newClient.getEmail()) && client.getPhoneNumber().equals(newClient.getPhoneNumber())) {

            URI url = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8081/api/v1/client-registration/" + user_id).build().toUri();
            return ResponseEntity.ok().body(url);
        } else {
            throw new InvalidDataException("Error in entered account data.");
        }
    }

    @PostMapping(value = "client-registration/{user_id}", params = {"username", "password", "passwordConfirmation"})
    public ResponseEntity<?> registerClient(@PathVariable String user_id, @RequestParam String username,
                                            @RequestParam String password,
                                            @RequestParam String passwordConfirmation) throws Exception {

        Client client = clientService.getClientByID(user_id);

        if (validation.isValidPassword(password, passwordConfirmation)
                && validation.isValidUsername(username)) {
            loginDetailsService.createLoginDetails(user_id, username, password);
            emailService.sendConfirmationEmail(client.getEmail());
            return ResponseEntity.ok("Successful registration! An activation email has been send to " + client.getEmail() + ".");
        } else {
            throw new BadRequestException("This client can't be registered!");
        }
    }

    @GetMapping("/secured")
    public AppUser securityTest(@AuthenticationPrincipal AppUser appUser) {
        System.out.println(appUser.getUsername());
        System.out.println(appUser.getPassword());
        return appUser;
    }

}
