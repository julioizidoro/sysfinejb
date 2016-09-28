package br.com.financemate.dao;

import br.com.financemate.model.Saldo;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SaldoDao extends AbstractDao<Saldo> {
    
    @PersistenceContext
    private EntityManager manager;

    public SaldoDao() {
        super(Saldo.class);
    }

    public Float consultar(String sql) throws SQLException {
        Query q = manager.createQuery(sql);
        Float saldo = null;
        if (q.getResultList().size() > 0) {
            saldo = (Float) q.getResultList().get(0);
        } else {
            saldo = 0f;
        }
        if (saldo == null) {
            saldo = 0f;
        }
        return saldo;
    } 

}
