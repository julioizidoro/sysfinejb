/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;


import br.com.financemate.model.Formapagamento;
import javax.ejb.Stateless;



@Stateless
public class FormaPagamentoDao extends AbstractDao<Formapagamento>{

    public FormaPagamentoDao() {
        super(Formapagamento.class);
    }
    
}
