package bg.codeacademy.spring.paymentmodule.controller;

import bg.codeacademy.spring.paymentmodule.exception.BadRequestException;
import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import bg.codeacademy.spring.paymentmodule.model.DutyHistoryDto;
import bg.codeacademy.spring.paymentmodule.security.AppUser;
import bg.codeacademy.spring.paymentmodule.security.LoggedInUser;
import bg.codeacademy.spring.paymentmodule.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LoginController
{
  private final DutyAccountService  dutyAccountService;
  private final DutyHistoryService  historyService;
  private final LoginDetailsService loginService;

  public LoginController(DutyAccountService dutyAccountService, DutyHistoryService historyService, LoginDetailsService loginService)
  {
    this.dutyAccountService = dutyAccountService;
    this.historyService = historyService;
    this.loginService = loginService;
  }

  @GetMapping(value = {"/loginPage", "", "/"})
  public String viewLoginPage()
  {
    return "login";
  }

  @GetMapping("/api/v1/login")
  public ResponseEntity<?> loginPage(@LoggedInUser AppUser appUser)  {

    if(!appUser.getLoginDetails().isActive()){
      throw new BadRequestException("User still not activated!");
    }

    DutyAccount dutyAccount = dutyAccountService
        .getDutyAccountByClientId(appUser.getClient().getId());

    List<DutyHistoryDto> history = historyService
        .getAllDutyHistoryByAccountId(dutyAccount.getId())
        .stream().map(DutyHistoryDto::of)
        .collect(Collectors.toList());

    URI paymentUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/api/v1/payment")
        .build().toUri();

    Map<String, Object> homeInfo = new HashMap<>();
    homeInfo.put("name", appUser.getClient().getName());
    homeInfo.put("dutyBalance", dutyAccount.getDuty());
    homeInfo.put("currency", dutyAccount.getCurrency().toString());
    homeInfo.put("paymentsHistory", history);
    homeInfo.put("paymentLink", paymentUri);

    return ResponseEntity.ok().body(homeInfo);
  }

  @PostMapping("/api/v1/login/{token}")
  public ResponseEntity<?> loginActivation(@LoggedInUser AppUser appUser, @PathVariable String token){
    loginService.activateLoginDetails(appUser.getClient().getId(), token);

    URI redirectUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/api/v1/login")
        .build().toUri();

    return ResponseEntity.status(HttpStatus.FOUND)
        .location(redirectUri).build();
  }

  @GetMapping("/api/v1/perform_logout")
  public String logoutTest(@LoggedInUser AppUser appUser){
    return appUser.getClient().getName();
  }
}
