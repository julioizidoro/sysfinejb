package br.com.financemate.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cobrancaparcelas")
public class Cobrancaparcelas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Basic(optional = false)
	 @Column(name = "idcobrancaparcelas")
	 private Integer idcobrancaparcelas;
	 @JoinColumn(name = "contasReceber_idcontasReceber", referencedColumnName = "idcontasReceber")
	 @ManyToOne(optional = false)
	 private Contasreceber contasreceber;
	 @JoinColumn(name = "cobranca_idcobranca", referencedColumnName = "idcobranca")
	 @ManyToOne(optional = false)
	 private Cobranca cobranca;
	public Integer getIdcobrancaparcelas() {
		return idcobrancaparcelas;
	}
	public void setIdcobrancaparcelas(Integer idcobrancaparcelas) {
		this.idcobrancaparcelas = idcobrancaparcelas;
	}
	public Contasreceber getContasreceber() {
		return contasreceber;
	}
	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
	}
	public Cobranca getCobranca() {
		return cobranca;
	}
	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	 
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobrancaparcelas != null ? idcobrancaparcelas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cobrancaparcelas)) {
            return false;
        }
        Cobrancaparcelas other = (Cobrancaparcelas) object;
        if ((this.idcobrancaparcelas == null && other.idcobrancaparcelas != null) || (this.idcobrancaparcelas != null && !this.idcobrancaparcelas.equals(other.idcobrancaparcelas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Cobrancaparcelas[ idcobrancaparcelas=" + idcobrancaparcelas + " ]";
    }
}
