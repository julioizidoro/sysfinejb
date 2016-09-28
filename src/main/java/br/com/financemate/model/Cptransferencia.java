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
@Table(name="cptransferencia")
public class Cptransferencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcptransferencia")
	private Integer idcptransferencia;
	@Column(name = "banco")
    private String banco;
	@Column(name = "agencia")
	private String agencia;
	@Column(name = "conta")
	private String conta;
	@Column(name = "beneficiario")
	private String beneficiario;
	@Column(name = "cpfcnpj")
	private String cpfcnpj; 
	@JoinColumn(name = "contasPagar_idcontasPagar", referencedColumnName = "idcontasPagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;
	
	public Cptransferencia() {
	}

	public Cptransferencia(Integer idcptransferencia) {
		super();
		this.idcptransferencia = idcptransferencia;
	}

	public Integer getIdcptransferencia() {
		return idcptransferencia;
	}

	public void setIdcptransferencia(Integer idcptransferencia) {
		this.idcptransferencia = idcptransferencia;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public Contaspagar getContaspagar() {
		return contaspagar;
	}

	public void setContaspagar(Contaspagar contaspagar) {
		this.contaspagar = contaspagar;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcptransferencia != null ? idcptransferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Cptransferencia)) {
            return false;
        }
        Cptransferencia other = (Cptransferencia) object;
        if ((this.idcptransferencia == null && other.idcptransferencia != null) || (this.idcptransferencia != null && !this.idcptransferencia.equals(other.idcptransferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Cptransferencia[ idcptransferencia=" + idcptransferencia + " ]";
    }

	
}
