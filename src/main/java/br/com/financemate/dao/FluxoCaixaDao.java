/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.model.Fluxocaixa;
import javax.ejb.Stateless;



@Stateless
public class FluxoCaixaDao extends AbstractDao<Fluxocaixa>{

    public FluxoCaixaDao() {
        super(Fluxocaixa.class);
    }
    
    
    
}