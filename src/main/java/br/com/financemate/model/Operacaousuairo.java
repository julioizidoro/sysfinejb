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
import javax.validation.constraints.Size;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "operacaousuairo")
public class Operacaousuairo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoperacaousuairo")
    private Integer idoperacaousuairo;
    @Size(max = 50)
    @Column(name = "tipooperacao")
    private String tipooperacao;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 10)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "contasPagar_idcontasPagar", referencedColumnName = "idcontasPagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;

    public Operacaousuairo() {
    }

    public Operacaousuairo(Integer idoperacaousuairo) {
        this.idoperacaousuairo = idoperacaousuairo;
    }

    public Integer getIdoperacaousuairo() {
        return idoperacaousuairo;
    }

    public void setIdoperacaousuairo(Integer idoperacaousuairo) {
        this.idoperacaousuairo = idoperacaousuairo;
    }

    public String getTipooperacao() {
        return tipooperacao;
    }

    public void setTipooperacao(String tipooperacao) {
        this.tipooperacao = tipooperacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoperacaousuairo != null ? idoperacaousuairo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Operacaousuairo)) {
            return false;
        }
        Operacaousuairo other = (Operacaousuairo) object;
        if ((this.idoperacaousuairo == null && other.idoperacaousuairo != null) || (this.idoperacaousuairo != null && !this.idoperacaousuairo.equals(other.idoperacaousuairo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Operacaousuairo[ idoperacaousuairo=" + idoperacaousuairo + " ]";
    }
    
}
