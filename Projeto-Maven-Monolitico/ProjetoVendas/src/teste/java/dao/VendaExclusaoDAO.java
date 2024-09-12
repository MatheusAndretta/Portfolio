package teste.java.dao;

import main.java.dao.IVendaDAO;
import main.java.dao.generic.GenericDAO;
import main.java.domain.Venda;

public class VendaExclusaoDAO extends GenericDAO<Venda,Long> implements IVendaDAO{

    public VendaExclusaoDAO(Class<Venda> persistenteClass) {
        super(Venda.class);
       
    }

    @Override
    public void finalizarVenda(Venda venda) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public void cancelarVenda(Venda venda) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public Venda consultarComCollection(Long id) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

}
