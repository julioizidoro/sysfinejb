package br.com.financemate.model;

import javax.persistence.Table;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "vendascontasreceber")
public class Vendascontasreceber implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Basic(optional = false)
	 @Column(name = "idvendascontasreceber")
	 private Integer idvendascontasreceber;
	 @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
	 @ManyToOne(optional = false)
	 private Vendas vendas;
	 @JoinColumn(name = "contasReceber_idcontasReceber", referencedColumnName = "idcontasReceber")
	 @ManyToOne(optional = false)
	 private Contasreceber contasreceber;
	public Integer getIdvendascontasreceber() {
		return idvendascontasreceber;
	}
	public void setIdvendascontasreceber(Integer idvendascontasreceber) {
		this.idvendascontasreceber = idvendascontasreceber;
	}
	public Vendas getVendas() {
		return vendas;
	}
	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	public Contasreceber getContasreceber() {
		return contasreceber;
	}
	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
	}
	 
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendascontasreceber != null ? idvendascontasreceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vendascontasreceber)) {
            return false;
        }
        Vendascontasreceber other = (Vendascontasreceber) object;
        if ((this.idvendascontasreceber == null && other.idvendascontasreceber != null) || (this.idvendascontasreceber != null && !this.idvendascontasreceber.equals(other.idvendascontasreceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Vendascontasreceber[ idvendascontasreceber=" + idvendascontasreceber + " ]";
    }
	 

}
