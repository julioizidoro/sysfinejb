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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "outroslancamentos")
public class Outroslancamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoutroslancamentos")
    private Integer idoutroslancamentos;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorEntrada")
    private Float valorEntrada;
    @Column(name = "valorSaida")
    private Float valorSaida;
    @Column(name = "dataRegistro")
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;
    @Column(name = "dataCompensacao")
    @Temporal(TemporalType.DATE)
    private Date dataCompensacao;
    @Size(max = 50)
    @Column(name = "tipoDocumento")
    private String tipoDocumento;
    @Size(max = 10)
    @Column(name = "compentencia")
    private String compentencia;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "planoContas_idplanoContas", referencedColumnName = "idplanoContas")
    @ManyToOne(optional = false)
    private Planocontas planocontas;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
    @Column(name = "idcontaspagar")
    private int idcontaspagar;
    @Column(name = "idcontasreceber")
    private int idcontasreceber;
    @Transient
    private boolean selecionado;
    @Column(name = "saldo")
    private Float saldo;
    @Column(name = "conciliacao")
    private String conciliacao;
    @Transient
    private Boolean conciliada;
    

    public Outroslancamentos() {
    }

    public Outroslancamentos(Integer idoutroslancamentos) {
        this.idoutroslancamentos = idoutroslancamentos;
    }

   

    public Integer getIdoutroslancamentos() {
		return idoutroslancamentos;
	}

	public void setIdoutroslancamentos(Integer idoutroslancamentos) {
		this.idoutroslancamentos = idoutroslancamentos;
	}

	public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Float valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Float getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(Float valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCompentencia() {
        return compentencia;
    }

    public void setCompentencia(String compentencia) {
        this.compentencia = compentencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public int getIdcontaspagar() {
        return idcontaspagar;
    }

    public void setIdcontaspagar(int idcontaspagar) {
        this.idcontaspagar = idcontaspagar;
    }

    public int getIdcontasreceber() {
        return idcontasreceber;
    }

    public void setIdcontasreceber(int idcontasreceber) {
        this.idcontasreceber = idcontasreceber;
    }
    
    

    public String getConciliacao() {
		return conciliacao;
	}

	public void setConciliacao(String conciliacao) {
		this.conciliacao = conciliacao;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}
	
	

	public Boolean getConciliada() {
		return conciliada;
	}

	public void setConciliada(Boolean conciliada) {
		this.conciliada = conciliada;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idoutroslancamentos != null ? idoutroslancamentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Outroslancamentos)) {
            return false;
        }
        Outroslancamentos other = (Outroslancamentos) object;
        if ((this.idoutroslancamentos == null && other.idoutroslancamentos != null) || (this.idoutroslancamentos != null && !this.idoutroslancamentos.equals(other.idoutroslancamentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Movimentobanco[ idmovimentoBanco=" + idoutroslancamentos + " ]";
    }
    
}
