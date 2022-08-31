package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.model.Client;

import java.time.LocalDateTime;
import java.util.List;


public interface ClientService
{

  Client getClientByEmail(String email);

  Client getClientByID(String id);

  List<Client> getRegisteredClients();

  List<Client> getClientsForFirstEmail();

  boolean isValid(Client client, Client user);

}
