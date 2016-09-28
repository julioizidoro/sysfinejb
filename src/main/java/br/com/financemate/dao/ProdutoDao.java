/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.model.Produto;
import javax.ejb.Stateless;



@Stateless
public class ProdutoDao extends AbstractDao<Produto>{
    
    public ProdutoDao() {
        super(Produto.class);
    }
    
    
}
