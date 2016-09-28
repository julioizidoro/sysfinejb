/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.model.Dadosmunicipais;
import javax.ejb.Stateless;



@Stateless
public class DadosMunicipaisDao extends AbstractDao<Dadosmunicipais>{

    public DadosMunicipaisDao() {
        super(Dadosmunicipais.class);
    }
    
}
