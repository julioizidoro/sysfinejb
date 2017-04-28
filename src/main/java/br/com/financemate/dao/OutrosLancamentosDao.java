/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.model.Movimentobanco;
import br.com.financemate.util.Formatacao;

import java.sql.SQLException;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Stateless
public class OutrosLancamentosDao extends AbstractDao<Movimentobanco>{
    
    @PersistenceContext
    private EntityManager manager;

    public OutrosLancamentosDao() {
        super(Movimentobanco.class);
    }
    
    
    
    public float gerarSaldoInicial(Date dataInicio)throws SQLException {
    	float saldo= 0.0f;
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT distinct sum(valorSaida) as totalsaida FROM movimentobanco" +
        									" where dataVencimento<'" + Formatacao.ConvercaoDataSql(dataInicio) + "'");
        Double totalsaida = (Double) q.getResultList().get(0);
        q = manager.createNativeQuery("SELECT distinct sum(valorEntrada) as totalentrada FROM movimentobanco" +
				" where dataVencimento<'" + Formatacao.ConvercaoDataSql(dataInicio) + "'");
        Double totalentrada = (Double) q.getResultList().get(0);
        saldo = totalentrada.floatValue() - totalsaida.floatValue();
    	return saldo;
    }
    
    
    public Float saldoInicialTelaConsulta(String sql) throws SQLException{
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        Float saldo = 0f;
        Double total = 0D;
        if (q.getResultList().size()>0){
           total  = (Double) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        saldo = total.floatValue();
        return saldo;
    }
    
    
}
