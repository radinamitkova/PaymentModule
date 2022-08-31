package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.Client;

import java.util.List;

public interface ClientDao
{
  List<Client> getAll();

  List<Client> getUnregisteredClients();

  void createClient(Client client);

  Client getClient(String id);

  void updateClient(Client client);

  void deleteClient(String id);

  Client getClientByEmail(String email);

  List<Client> getRegisteredClients();

  List<Client> getClientsForFirstEmail();

}
