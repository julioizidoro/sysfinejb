package br.com.financemate.dao;

import br.com.financemate.model.Cobranca;
import javax.ejb.Stateless;


@Stateless
public class CobrancaDao extends AbstractDao<Cobranca>{
    
    public CobrancaDao() {
        super(Cobranca.class);
    }
    
    
    
    
}
