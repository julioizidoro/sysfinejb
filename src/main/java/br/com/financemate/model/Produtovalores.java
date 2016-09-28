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
import javax.persistence.Table;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "produtovalores")
public class Produtovalores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutoValores")
    private Integer idprodutoValores;
    @Column(name = "codigoValor")
    private Integer codigoValor;
    @Column(name = "codigoProduto")
    private Integer codigoProduto;

    public Produtovalores() {
    }

    public Produtovalores(Integer idprodutoValores) {
        this.idprodutoValores = idprodutoValores;
    }

    public Integer getIdprodutoValores() {
        return idprodutoValores;
    }

    public void setIdprodutoValores(Integer idprodutoValores) {
        this.idprodutoValores = idprodutoValores;
    }

    public Integer getCodigoValor() {
        return codigoValor;
    }

    public void setCodigoValor(Integer codigoValor) {
        this.codigoValor = codigoValor;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutoValores != null ? idprodutoValores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Produtovalores)) {
            return false;
        }
        Produtovalores other = (Produtovalores) object;
        if ((this.idprodutoValores == null && other.idprodutoValores != null) || (this.idprodutoValores != null && !this.idprodutoValores.equals(other.idprodutoValores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Produtovalores[ idprodutoValores=" + idprodutoValores + " ]";
    }
    
}
