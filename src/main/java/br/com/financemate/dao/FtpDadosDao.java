package br.com.financemate.dao;

import br.com.financemate.model.Ftpdados;
import javax.ejb.Stateless;


@Stateless
public class FtpDadosDao extends AbstractDao<Ftpdados>{

    public FtpDadosDao() {
        super(Ftpdados.class);
    }
    
}

