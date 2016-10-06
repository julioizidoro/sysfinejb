package br.com.financemate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Webservice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idwebservice")
    private Integer idwebservice;
    @Column(name = "host")
    private String host;
    @Column(name = "porta")
    private String porta;
    @Column(name = "userdb")
    private String userdb;
    @Column(name = "passworddb")
    private String passworddb;
    @Column(name = "nomedb")
    private String nomedb;
    @Column(name = "portadb")
    private String portadb;
    @Column(name = "hostdb")
    private String hostdb;
    
    
    public Webservice() {
		// TODO Auto-generated constructor stub
	}


	public Integer getIdwebservice() {
		return idwebservice;
	}


	public void setIdwebservice(Integer idwebservice) {
		this.idwebservice = idwebservice;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getPorta() {
		return porta;
	}


	public void setPorta(String porta) {
		this.porta = porta;
	}


	public String getUserdb() {
		return userdb;
	}


	public void setUserdb(String userdb) {
		this.userdb = userdb;
	}


	public String getPassworddb() {
		return passworddb;
	}


	public void setPassworddb(String passworddb) {
		this.passworddb = passworddb;
	}


	public String getNomedb() {
		return nomedb;
	}


	public void setNomedb(String nomedb) {
		this.nomedb = nomedb;
	}


	public String getPortadb() {
		return portadb;
	}


	public void setPortadb(String portadb) {
		this.portadb = portadb;
	}


	public String getHostdb() {
		return hostdb;
	}


	public void setHostdb(String hostdb) {
		this.hostdb = hostdb;
	}

    
    
}
