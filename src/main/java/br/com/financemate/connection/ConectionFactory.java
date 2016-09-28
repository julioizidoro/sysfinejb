 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.swing.JOptionPane;

/**
 *
 * @author Wolverine
 */

@ApplicationScoped
public class ConectionFactory {

	@PersistenceContext
        private static EntityManager manager;
    

    public static EntityManager getConnection() {
    	EntityManagerFactory emf = null;
        manager = null;
        emf = Persistence.createEntityManagerFactory("sysfinPU");
        manager = emf.createEntityManager();
        if (!manager.isOpen()) {
        	JOptionPane.showMessageDialog(null, "Conex�o fechada");
        }
        return manager;
    }
    
    public static Connection getConexao(){
    	Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://191.191.20.138:8082/sysfin", "root", "jfhmaster123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return conexao;
    }
}
