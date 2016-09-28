package br.com.financemate.dao;

import br.com.financemate.model.Operacaousuairo;
import javax.ejb.Stateless;


@Stateless
public class OperacaoUsuarioDao extends AbstractDao<Operacaousuairo>{

    public OperacaoUsuarioDao() {
        super(Operacaousuairo.class);
    }

	
}
