/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.model.Planocontas;
import javax.ejb.Stateless;


@Stateless
public class PlanoContasDao extends AbstractDao<Planocontas>{
    
    public PlanoContasDao() {
        super(Planocontas.class);
    }
    
    
}
