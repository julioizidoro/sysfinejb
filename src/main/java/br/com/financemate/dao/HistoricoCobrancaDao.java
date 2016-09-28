package br.com.financemate.dao;

import br.com.financemate.model.Historicocobranca;
import javax.ejb.Stateless;


@Stateless
public class HistoricoCobrancaDao extends AbstractDao<Historicocobranca>{
    
    public HistoricoCobrancaDao() {
        super(Historicocobranca.class);
    }
    
    
}