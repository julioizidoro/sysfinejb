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
@Table(name = "contasreceber")
public class Contasreceber implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontasReceber")
    private Integer idcontasReceber;
    @Size(max = 50)
    @Column(name = "numeroDocumento")
    private String numeroDocumento;
    @Size(max = 100)
    @Column(name = "nomeCliente")
    private String nomeCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorParcela")
    private Float valorParcela;
    @Column(name = "numeroParcela")
    private String numeroParcela;
    @Column(name = "dataVencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Column(name = "juros")
    private Float juros;
    @Column(name = "desagio")
    private Float desagio;
    @Size(max = 50)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @Column(name = "movimentoBanco")
    private Integer movimentoBanco;
    @Column(name = "venda")
    private Integer venda;
    @Column(name = "valorPago")
    private Float valorPago;
    @Column(name = "dataPagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Column(name = "vendaComissao")
    private Integer vendaComissao;
    @Size(max = 10)
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
    @Transient
    private boolean selecionado;
    @Column(name = "status")
    private String status;

    public Contasreceber() {
    }



	public Contasreceber(Integer idcontasReceber) {
        this.idcontasReceber = idcontasReceber;
    }

    public Integer getIdcontasReceber() {
        return idcontasReceber;
    }

    public void setIdcontasReceber(Integer idcontasReceber) {
        this.idcontasReceber = idcontasReceber;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Float getDesagio() {
        return desagio;
    }

    public void setDesagio(Float desagio) {
        this.desagio = desagio;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public Integer getMovimentoBanco() {
        return movimentoBanco;
    }

    public void setMovimentoBanco(Integer movimentoBanco) {
        this.movimentoBanco = movimentoBanco;
    }

    public Integer getVenda() {
        return venda;
    }

    public void setVenda(Integer venda) {
        this.venda = venda;
    }

    public Float getValorPago() {
        return valorPago;
    }

    public void setValorPago(Float valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getVendaComissao() {
        return vendaComissao;
    }

    public void setVendaComissao(Integer vendaComissao) {
        this.vendaComissao = vendaComissao;
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

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontasReceber != null ? idcontasReceber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Contasreceber)) {
            return false;
        }
        Contasreceber other = (Contasreceber) object;
        if ((this.idcontasReceber == null && other.idcontasReceber != null) || (this.idcontasReceber != null && !this.idcontasReceber.equals(other.idcontasReceber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Contasreceber[ idcontasReceber=" + idcontasReceber + " ]";
    }
    
}
