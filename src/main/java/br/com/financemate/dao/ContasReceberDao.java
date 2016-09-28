/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contasreceber;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




@Stateless
public class ContasReceberDao extends AbstractDao<Contasreceber>{
    
    @PersistenceContext
    private EntityManager manager;

    public ContasReceberDao() {
        super(Contasreceber.class);
    }
    
    
    public Contasreceber consultarVendaFornecedor(int idVenda) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select c from Contasreceber c where c.vendaComissao=" + idVenda);
        Contasreceber conta = null;
        if (q.getResultList().size()>0){
            conta =  (Contasreceber) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        return conta;
    }
    
    public List<Double> calculaSaldos(String data, int idcliente) throws SQLException {
        Double valor;
        Query query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento<'" + data + "' and cliente_idcliente=" + idcliente);
        List<Double> totalContas = new ArrayList<Double>();
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento='" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento>'" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        return totalContas;
    }
    
    
    public Contasreceber consultar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Contasreceber contasreceber = null;
        if (q.getResultList().size()>0){
        	contasreceber = (Contasreceber) q.getResultList().get(0);
        }
        return contasreceber;
    } 
    
     
    public Float recebimentoPorDia(String sql) throws SQLException{
        Query q = manager.createQuery(sql);
        Float recebimento = null;
        Double valorRecebimento;
        if (q.getResultList().size()>0){
        	valorRecebimento = (Double) q.getResultList().get(0);
        	if (valorRecebimento == null) {
				recebimento = 0f;
			}else{
				recebimento = valorRecebimento.floatValue();
			}
        }
        return recebimento;
    } 
    
    public List<Contasreceber> listaFluxo(String sql) throws SQLException{
    	manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contasreceber> listaFluxo= q.getResultList();
        manager.getTransaction().commit();
        return listaFluxo;
    }
    
    
   
}
