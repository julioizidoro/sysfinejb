/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Emissaonota;
import br.com.financemate.model.Vendas;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class VendasDao extends AbstractDao<Vendas>{
    
    @PersistenceContext
    private EntityManager manager;
    
    public VendasDao() {
        super(Vendas.class);
    }
    
    public Emissaonota getEmissao(int idVendas) throws SQLException{
        Query q = manager.createQuery("select e from Emissaonota e where e.vendas.idvendas=" + idVendas);
        Emissaonota emissor = null;
        if (q.getResultList().size()>0){
            emissor = (Emissaonota) q.getResultList().get(0);
        }
        return emissor;
    }
    
    
     public Emissaonota salvar(Emissaonota emissaonota)throws SQLException{
        emissaonota = manager.merge(emissaonota);
        return emissaonota;
    }
    
}
