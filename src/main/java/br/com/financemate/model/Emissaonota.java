/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "emissaonota")
public class Emissaonota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemissaoNota")
    private Integer idemissaoNota;
    @Size(max = 100)
    @Column(name = "nome")
    private String nome;
    @Size(max = 100)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 50)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 50)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 50)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 9)
    @Column(name = "cep")
    private String cep;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Size(max = 18)
    @Column(name = "cpnj")
    private String cpnj;
    @Size(max = 30)
    @Column(name = "ie")
    private String ie;
    @JoinColumn(name = "vendas_idvendas", referencedColumnName = "idvendas")
    @ManyToOne(optional = false)
    private Vendas vendas;

    public Emissaonota() {
    }

    public Emissaonota(Integer idemissaoNota) {
        this.idemissaoNota = idemissaoNota;
    }

    public Integer getIdemissaoNota() {
        return idemissaoNota;
    }

    public void setIdemissaoNota(Integer idemissaoNota) {
        this.idemissaoNota = idemissaoNota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCpnj() {
        return cpnj;
    }

    public void setCpnj(String cpnj) {
        this.cpnj = cpnj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemissaoNota != null ? idemissaoNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Emissaonota)) {
            return false;
        }
        Emissaonota other = (Emissaonota) object;
        if ((this.idemissaoNota == null && other.idemissaoNota != null) || (this.idemissaoNota != null && !this.idemissaoNota.equals(other.idemissaoNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Emissaonota[ idemissaoNota=" + idemissaoNota + " ]";
    }
    
}
