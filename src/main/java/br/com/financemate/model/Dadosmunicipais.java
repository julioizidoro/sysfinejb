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
@Table(name = "dadosmunicipais")
public class Dadosmunicipais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddadosmunicipais")
    private Integer iddadosmunicipais;
    @Size(max = 200)
    @Column(name = "aplicativo")
    private String aplicativo;
    @Size(max = 100)
    @Column(name = "usuairo")
    private String usuairo;
    @Size(max = 50)
    @Column(name = "senha")
    private String senha;
    @Size(max = 50)
    @Column(name = "inscricaomunicipal")
    private String inscricaomunicipal;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Dadosmunicipais() {
    }

    public Dadosmunicipais(Integer iddadosmunicipais) {
        this.iddadosmunicipais = iddadosmunicipais;
    }

    public Integer getIddadosmunicipais() {
        return iddadosmunicipais;
    }

    public void setIddadosmunicipais(Integer iddadosmunicipais) {
        this.iddadosmunicipais = iddadosmunicipais;
    }

    public String getAplicativo() {
        return aplicativo;
    }

    public void setAplicativo(String aplicativo) {
        this.aplicativo = aplicativo;
    }

    public String getUsuairo() {
        return usuairo;
    }

    public void setUsuairo(String usuairo) {
        this.usuairo = usuairo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getInscricaomunicipal() {
        return inscricaomunicipal;
    }

    public void setInscricaomunicipal(String inscricaomunicipal) {
        this.inscricaomunicipal = inscricaomunicipal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddadosmunicipais != null ? iddadosmunicipais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Dadosmunicipais)) {
            return false;
        }
        Dadosmunicipais other = (Dadosmunicipais) object;
        if ((this.iddadosmunicipais == null && other.iddadosmunicipais != null) || (this.iddadosmunicipais != null && !this.iddadosmunicipais.equals(other.iddadosmunicipais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Dadosmunicipais[ iddadosmunicipais=" + iddadosmunicipais + " ]";
    }
    
}
