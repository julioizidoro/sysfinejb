package br.com.financemate.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cobranca database table.
 * 
 */


@Entity
@Table(name="cobranca")
public class Cobranca implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcobranca")
    private Integer idcobranca;
    @Size(max = 20)
    @Column(name = "fone1")
    private String fone1;
    @Size(max = 20)
    @Column(name = "fone2")
    private String fone2;
    @Column(name = "vencimentooriginal")
    @Temporal(TemporalType.DATE)
    private Date vencimentooriginal;
    @Column(name = "alterarvencimento")
    @Temporal(TemporalType.DATE)
    private Date alterarvencimento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobranca")
    private List<Historicocobranca> historicocobrancaList;

    public Cobranca() {
    }

    public Cobranca(Integer idcobranca) {
        this.idcobranca = idcobranca;
    }

    public Integer getIdcobranca() {
        return idcobranca;
    }

    public void setIdcobranca(Integer idcobranca) {
        this.idcobranca = idcobranca;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public Date getVencimentooriginal() {
        return vencimentooriginal;
    }

    public void setVencimentooriginal(Date vencimentooriginal) {
        this.vencimentooriginal = vencimentooriginal;
    }

    public Date getAlterarvencimento() {
        return alterarvencimento;
    }

    public void setAlterarvencimento(Date alterarvencimento) {
        this.alterarvencimento = alterarvencimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Historicocobranca> getHistoricocobrancaList() {
        return historicocobrancaList;
    }

    public void setHistoricocobrancaList(List<Historicocobranca> historicocobrancaList) {
        this.historicocobrancaList = historicocobrancaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcobranca != null ? idcobranca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Cobranca)) {
            return false;
        }
        Cobranca other = (Cobranca) object;
        if ((this.idcobranca == null && other.idcobranca != null) || (this.idcobranca != null && !this.idcobranca.equals(other.idcobranca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Cobranca[ idcobranca=" + idcobranca + " ]";
    }
    
}
