package br.com.financemate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "saldo")
public class Saldo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsaldo")
    private Integer idsaldo;
	@Column(name = "datasaldo")
    @Temporal(TemporalType.DATE)
    private Date datasaldo;
	@Column(name = "valor")
    private Float valor;
	@Column(name = "datainclusao")
    @Temporal(TemporalType.DATE)
    private Date datainclusao;
	@JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
	@JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
	
	
	public Saldo() {
	}


	public Integer getIdsaldo() {
		return idsaldo;
	}


	public void setIdsaldo(Integer idsaldo) {
		this.idsaldo = idsaldo;
	}


	


	public Date getDatasaldo() {
		return datasaldo;
	}


	public void setDatasaldo(Date datasaldo) {
		this.datasaldo = datasaldo;
	}


	public Date getDatainclusao() {
		return datainclusao;
	}


	public void setDatainclusao(Date datainclusao) {
		this.datainclusao = datainclusao;
	}


	public Float getValor() {
		return valor;
	}


	public void setValor(Float valor) {
		this.valor = valor;
	}


	public Banco getBanco() {
		return banco;
	}


	public void setBanco(Banco banco) {
		this.banco = banco;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idsaldo != null ? idsaldo.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        if (!(object instanceof Contaspagar)) {
	            return false;
	        }
	        Saldo other = (Saldo) object;
	        if ((this.idsaldo == null && other.idsaldo != null) || (this.idsaldo != null && !this.idsaldo.equals(other.idsaldo))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "br.com.financemate.model.Saldo[ idsaldo=" + idsaldo + " ]";
	    }
}
