package main.java.dao;

import main.java.dao.generic.GenericDAO;
import main.java.domain.Produto;

public class ProdutoDAO  extends GenericDAO<Produto,Long> implements IProdutoDAO{

    public ProdutoDAO() {
        super(Produto.class);
        
    }

}
