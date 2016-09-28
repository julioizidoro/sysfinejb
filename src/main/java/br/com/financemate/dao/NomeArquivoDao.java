/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.model.Nomearquivo;
import javax.ejb.Stateless;

@Stateless
public class NomeArquivoDao extends AbstractDao<Nomearquivo>{
    
    public NomeArquivoDao() {
        super(Nomearquivo.class);
    }
    
    
}
