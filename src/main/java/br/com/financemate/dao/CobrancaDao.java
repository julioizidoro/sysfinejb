package br.com.financemate.dao;

import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Historicocobranca;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class CobrancaDao extends AbstractDao<Cobranca>{
    
    @PersistenceContext
    private EntityManager manager;
    
    public CobrancaDao() {
        super(Cobranca.class);
    }
    
    
    public Historicocobranca salvar(Historicocobranca historicocobranca) throws SQLException{
        historicocobranca = manager.merge(historicocobranca);
        return historicocobranca;
    }
    
}
