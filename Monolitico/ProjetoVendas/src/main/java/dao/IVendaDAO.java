package main.java.dao;

import main.java.dao.generic.IGenericDAO;
import main.java.domain.Venda;

public interface IVendaDAO extends IGenericDAO<Venda,Long>{

    public void finalizarVenda(Venda venda);

    public void cancelarVenda(Venda venda);

    public Venda consultarComCollection(Long id);
}
