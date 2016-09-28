package br.com.financemate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;


/**
 * The persistent class for the historicocobranca database table.
 * 
 */
@Entity
@Table(name="historicocobranca")
public class Historicocobranca implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhistoricocobranca")
    private Integer idhistoricocobranca;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 50)
    @Column(name = "contato")
    private String contato;
    @Size(max = 16777215)
    @Column(name = "descricao")
    private String descricao;
    @JoinColumn(name = "cobranca_idcobranca", referencedColumnName = "idcobranca")
    @ManyToOne(optional = false)
    private Cobranca cobranca;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
 
    public Historicocobranca() {
    }

    public Historicocobranca(Integer idhistoricocobranca) {
        this.idhistoricocobranca = idhistoricocobranca;
    }
    
    

    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdhistoricocobranca() {
        return idhistoricocobranca;
    }

    public void setIdhistoricocobranca(Integer idhistoricocobranca) {
        this.idhistoricocobranca = idhistoricocobranca;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash += (idhistoricocobranca != null ? idhistoricocobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Historicocobranca)) {
            return false;
        }
        Historicocobranca other = (Historicocobranca) object;
        if ((this.idhistoricocobranca == null && other.idhistoricocobranca != null) || (this.idhistoricocobranca != null && !this.idhistoricocobranca.equals(other.idhistoricocobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Historicocobranca[ idhistoricocobranca=" + idhistoricocobranca + " ]";
    }
    
}
