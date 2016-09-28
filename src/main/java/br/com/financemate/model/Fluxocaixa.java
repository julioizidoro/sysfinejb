/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "fluxocaixa")
public class Fluxocaixa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfluxoCaixa")
    private Integer idfluxoCaixa;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorContasPagar")
    private Float valorContasPagar;
    @Column(name = "valorContasReceber")
    private Float valorContasReceber;
    @Column(name = "saldo")
    private Float saldo;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente cliente;

    public Fluxocaixa() {
    }

    public Fluxocaixa(Integer idfluxoCaixa) {
        this.idfluxoCaixa = idfluxoCaixa;
    }

    public Integer getIdfluxoCaixa() {
        return idfluxoCaixa;
    }

    public void setIdfluxoCaixa(Integer idfluxoCaixa) {
        this.idfluxoCaixa = idfluxoCaixa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getValorContasPagar() {
        return valorContasPagar;
    }

    public void setValorContasPagar(Float valorContasPagar) {
        this.valorContasPagar = valorContasPagar;
    }

    public Float getValorContasReceber() {
        return valorContasReceber;
    }

    public void setValorContasReceber(Float valorContasReceber) {
        this.valorContasReceber = valorContasReceber;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
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
        hash += (idfluxoCaixa != null ? idfluxoCaixa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Fluxocaixa)) {
            return false;
        }
        Fluxocaixa other = (Fluxocaixa) object;
        if ((this.idfluxoCaixa == null && other.idfluxoCaixa != null) || (this.idfluxoCaixa != null && !this.idfluxoCaixa.equals(other.idfluxoCaixa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Fluxocaixa[ idfluxoCaixa=" + idfluxoCaixa + " ]";
    }
    
}
