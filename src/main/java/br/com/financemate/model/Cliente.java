/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.model;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcliente")
    private Integer idcliente;
    @Size(max = 100)
    @Column(name = "razaoSocial")
    private String razaoSocial;
    @Size(max = 100)
    @Column(name = "nomeFantasia")
    private String nomeFantasia;
    @Size(max = 18)
    @Column(name = "cnpj")
    private String cnpj;
    @Size(max = 20)
    @Column(name = "tipoLogradouro")
    private String tipoLogradouro;
    @Size(max = 100)
    @Column(name = "logradouro")
    private String logradouro;
    @Size(max = 20)
    @Column(name = "numero")
    private String numero;
    @Size(max = 50)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 50)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 50)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Size(max = 9)
    @Column(name = "cep")
    private String cep;
    @Size(max = 14)
    @Column(name = "foneFixo")
    private String foneFixo;
    @Size(max = 100)
    @Column(name = "administrador")
    private String administrador;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Column(name = "contaReceita")
    private Integer contaReceita;
    @Column(name = "contaRecebimento")
    private Integer contaRecebimento;
    @Column(name = "codigosystm")
    private Integer codigosystm;
    @Size(max = 15)
    @Column(name = "visualizacao")
    private String visualizacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Banco> bancoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Produto> produtoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Contasreceber> contasreceberList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Outroslancamentos> movimentobancoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Contaspagar> contaspagarList;
    @JoinColumn(name = "tipoplanocontas_idtipoplanocontas", referencedColumnName = "idtipoplanocontas")
    @ManyToOne(optional = false)
    private Tipoplanocontas tipoplanocontas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Vendas> vendasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Dadosmunicipais> dadosmunicipaisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Fluxocaixa> fluxocaixaList;
    @Transient
    private boolean selecionado;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFoneFixo() {
        return foneFixo;
    }

    public void setFoneFixo(String foneFixo) {
        this.foneFixo = foneFixo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getContaReceita() {
        return contaReceita;
    }

    public void setContaReceita(Integer contaReceita) {
        this.contaReceita = contaReceita;
    }

    public Integer getContaRecebimento() {
        return contaRecebimento;
    }

    public void setContaRecebimento(Integer contaRecebimento) {
        this.contaRecebimento = contaRecebimento;
    }

    public Integer getCodigosystm() {
        return codigosystm;
    }

    public void setCodigosystm(Integer codigosystm) {
        this.codigosystm = codigosystm;
    }

    public String getVisualizacao() {
        return visualizacao;
    }

    public void setVisualizacao(String visualizacao) {
        this.visualizacao = visualizacao;
    }

    public List<Banco> getBancoList() {
        return bancoList;
    }

    public void setBancoList(List<Banco> bancoList) {
        this.bancoList = bancoList;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    public List<Contasreceber> getContasreceberList() {
        return contasreceberList;
    }

    public void setContasreceberList(List<Contasreceber> contasreceberList) {
        this.contasreceberList = contasreceberList;
    }

    public List<Outroslancamentos> getMovimentobancoList() {
        return movimentobancoList;
    }

    public void setMovimentobancoList(List<Outroslancamentos> movimentobancoList) {
        this.movimentobancoList = movimentobancoList;
    }

    public List<Contaspagar> getContaspagarList() {
        return contaspagarList;
    }

    public void setContaspagarList(List<Contaspagar> contaspagarList) {
        this.contaspagarList = contaspagarList;
    }

    public Tipoplanocontas getTipoplanocontas() {
        return tipoplanocontas;
    }

    public void setTipoplanocontas(Tipoplanocontas tipoplanocontas) {
        this.tipoplanocontas = tipoplanocontas;
    }

    public List<Vendas> getVendasList() {
        return vendasList;
    }

    public void setVendasList(List<Vendas> vendasList) {
        this.vendasList = vendasList;
    }

    public List<Dadosmunicipais> getDadosmunicipaisList() {
        return dadosmunicipaisList;
    }

    public void setDadosmunicipaisList(List<Dadosmunicipais> dadosmunicipaisList) {
        this.dadosmunicipaisList = dadosmunicipaisList;
    }

    public List<Fluxocaixa> getFluxocaixaList() {
        return fluxocaixaList;
    }

    public void setFluxocaixaList(List<Fluxocaixa> fluxocaixaList) {
        this.fluxocaixaList = fluxocaixaList;
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
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Cliente[ idcliente=" + idcliente + " ]";
    }

}
