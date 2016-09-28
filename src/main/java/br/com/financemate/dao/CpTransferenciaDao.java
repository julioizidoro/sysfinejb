package br.com.financemate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.model.Cptransferencia;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;


@Stateless
public class CpTransferenciaDao extends AbstractDao<Cptransferencia>{
    
    @PersistenceContext
    private EntityManager manager;

    public CpTransferenciaDao() {
        super(Cptransferencia.class);
    }

    public Cptransferencia transferencias(String sql) throws SQLException {
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        Cptransferencia cptransferencia = (Cptransferencia) q.getResultList();
        manager.getTransaction().commit();
        return cptransferencia;
    }
}
