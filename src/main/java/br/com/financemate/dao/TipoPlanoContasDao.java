/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.Stateless;


@Stateless
public class TipoPlanoContasDao extends AbstractDao<Tipoplanocontas>{
    
    public TipoPlanoContasDao() {
        super(Tipoplanocontas.class);
    }
    
    

}
