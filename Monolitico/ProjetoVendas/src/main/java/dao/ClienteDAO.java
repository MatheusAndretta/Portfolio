package main.java.dao;

import main.java.dao.generic.GenericDAO;
import main.java.domain.Cliente;

public class ClienteDAO extends GenericDAO<Cliente,Long> implements IClienteDAO {

    public ClienteDAO(){
        super(Cliente.class);
    }

}
