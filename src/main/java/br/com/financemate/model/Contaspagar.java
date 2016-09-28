/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "contaspagar")
public class Contaspagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontasPagar")
    private Integer idcontasPagar;
    @Column(name = "dataLiberacao")
    @Temporal(TemporalType.DATE)
    private Date dataLiberacao;
    @Column(name = "dataEnvio")
    @Temporal(TemporalType.DATE)
    private Date dataEnvio;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Size(max = 200)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "dataAgendamento")
    @Temporal(TemporalType.DATE)
    private Date dataAgendamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Size(max = 7)
    @Column(name = "competencia")
    private String competencia;
    @Column(name = "dataCompensacao")
    @Temporal(TemporalType.DATE)
    private Date dataCompensacao;
    @Column(name = "movimentoBanco")
    private Integer movimentoBanco;
    @Size(max = 1)
    @Column(name = "contaPaga")
    private String contaPaga;
    @Size(max = 50)
    @Column(name = "formaPagamento")
    private String formaPagamento;
    @Column(name = "vendaComissao")
    private Integer vendaComissao;
    @Size(max = 100)
    @Column(name = "fornecedor")
    private String fornecedor;
    @Size(max = 30)
    @Column(name = "numeroDocumento")
    private String numeroDocumento;
    @Size(max = 1)
    @Column(name = "marcar")
    private String marcar;
    @Size(max = 50)
    @Column(name = "tipoDocumento")
    private String tipoDocumento;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @Size(max = 1)
    @Column(name = "autorizarPagamento")
    private String autorizarPagamento;
    @JoinColumn(name = "planoContas_idplanoContas", referencedColumnName = "idplanoContas")
    @ManyToOne(optional = false)
    private Planocontas planocontas;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "banco_idbanco", referencedColumnName = "idbanco")
    @ManyToOne(optional = false)
    private Banco banco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contaspagar")
    private List<Operacaousuairo> operacaousuairoList;
    @Transient
    private boolean selecionado;

    public Contaspagar() {
    }

    public Contaspagar(Integer idcontasPagar) {
        this.idcontasPagar = idcontasPagar;
    }

    public Integer getIdcontasPagar() {
        return idcontasPagar;
    }

    public void setIdcontasPagar(Integer idcontasPagar) {
        this.idcontasPagar = idcontasPagar;
    }

    public Date getDataLiberacao() {
        return dataLiberacao;
    }

    public void setDataLiberacao(Date dataLiberacao) {
        this.dataLiberacao = dataLiberacao;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getAutorizarPagamento() {
        return autorizarPagamento;
    }

    public void setAutorizarPagamento(String autorizarPagamento) {
        this.autorizarPagamento = autorizarPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

   

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public Integer getMovimentoBanco() {
        return movimentoBanco;
    }

    public void setMovimentoBanco(Integer movimentoBanco) {
        this.movimentoBanco = movimentoBanco;
    }

    public String getContaPaga() {
        return contaPaga;
    }

    public void setContaPaga(String contaPaga) {
        this.contaPaga = contaPaga;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Integer getVendaComissao() {
        return vendaComissao;
    }

    public void setVendaComissao(Integer vendaComissao) {
        this.vendaComissao = vendaComissao;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getMarcar() {
        return marcar;
    }

    public void setMarcar(String marcar) {
        this.marcar = marcar;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
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

    public List<Operacaousuairo> getOperacaousuairoList() {
        return operacaousuairoList;
    }

    public void setOperacaousuairoList(List<Operacaousuairo> operacaousuairoList) {
        this.operacaousuairoList = operacaousuairoList;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontasPagar != null ? idcontasPagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Contaspagar)) {
            return false;
        }
        Contaspagar other = (Contaspagar) object;
        if ((this.idcontasPagar == null && other.idcontasPagar != null) || (this.idcontasPagar != null && !this.idcontasPagar.equals(other.idcontasPagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Contaspagar[ idcontasPagar=" + idcontasPagar + " ]";
    }
    
}
