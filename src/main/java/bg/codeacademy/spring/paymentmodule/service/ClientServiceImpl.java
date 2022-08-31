package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.ClientDao;
import bg.codeacademy.spring.paymentmodule.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService
{
  private final ClientDao clientDao;


  public ClientServiceImpl(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  public Client getClientByEmail(String email)
  {
    return clientDao.getClientByEmail(email);
  }

  public Client getClientByID(String id)
  {
    return clientDao.getClient(id);
  }

  public List<Client> getRegisteredClients()
  {
    return clientDao.getRegisteredClients();
  }

  public List<Client> getClientsForFirstEmail(){
    return clientDao.getClientsForFirstEmail();
  }

  public boolean isValid(Client client, Client user){
    return client.getId().equals(user.getId()) && client.getName().equals(user.getName()) &&
        client.getEmail().equals(user.getEmail()) && client.getPhoneNumber().equals(user.getPhoneNumber());
  }
}
