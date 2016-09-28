package br.com.financemate.dao;

import br.com.financemate.model.Planocontatipo;
import javax.ejb.Stateless;

@Stateless
public class PlanoContaTipoDao extends AbstractDao<Planocontatipo>{

    public PlanoContaTipoDao() {
        super(Planocontatipo.class);
    }

	
}
