/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.model.Contaspagar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ContasPagarDao extends AbstractDao<Contaspagar> {
    
    @PersistenceContext
    private EntityManager manager;

    public ContasPagarDao() {
        super(Contaspagar.class);
    }

    public List<Double> calculaSaldos(String data, int idcliente) throws SQLException {
        Double valor;
        Query query = manager.createNativeQuery("Select distinct sum(valor) as valor "
                + "From Contaspagar where dataVencimento<'" + data + "' and cliente_idcliente=" + idcliente);
        List<Double> totalContas = new ArrayList<Double>();
        if (query.getSingleResult() != null) {
            valor = (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        } else {
            totalContas.add(0.0);
        }

        query = manager.createNativeQuery("Select distinct sum(valor) as valor "
                + "From Contaspagar where dataVencimento='" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult() != null) {
            valor = (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        } else {
            totalContas.add(0.0);
        }

        query = manager.createNativeQuery("Select distinct sum(valor) as valor "
                + "From Contaspagar where dataVencimento>'" + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult() != null) {
            valor = (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        } else {
            totalContas.add(0.0);
        }

        return totalContas;
    }

    public List<Contaspagar> listaFluxo(String sql) throws SQLException {
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaFluxo = q.getResultList();
        manager.getTransaction().commit();
        return listaFluxo;
    }

    public Float pagamentoPorDia(String sql) throws SQLException {
        Query q = manager.createQuery(sql);
        Float pagamento = null;
        Double valorPagamento;
        if (q.getResultList().size() > 0) {
            valorPagamento = (Double) q.getResultList().get(0);
            if (valorPagamento == null) {
                pagamento = 0f;
            } else {
                pagamento = valorPagamento.floatValue();
            }
        }
        return pagamento;
    }

}
