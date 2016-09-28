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
@Table(name = "nomearquivo")
public class Nomearquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnomearquivo")
    private Integer idnomearquivo;
    @Size(max = 200)
    @Column(name = "nomearquivo01")
    private String nomearquivo01;
    @Size(max = 200)
    @Column(name = "caminho")
    private String caminho;
    @JoinColumn(name = "contasPagar_idcontasPagar", referencedColumnName = "idcontasPagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;

    public Nomearquivo() {
    }

    public Nomearquivo(Integer idnomearquivo) {
        this.idnomearquivo = idnomearquivo;
    }

    public Integer getIdnomearquivo() {
        return idnomearquivo;
    }

    public void setIdnomearquivo(Integer idnomearquivo) {
        this.idnomearquivo = idnomearquivo;
    }

    public String getNomearquivo01() {
        return nomearquivo01;
    }

    public void setNomearquivo01(String nomearquivo01) {
        this.nomearquivo01 = nomearquivo01;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
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
        hash += (idnomearquivo != null ? idnomearquivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Nomearquivo)) {
            return false;
        }
        Nomearquivo other = (Nomearquivo) object;
        if ((this.idnomearquivo == null && other.idnomearquivo != null) || (this.idnomearquivo != null && !this.idnomearquivo.equals(other.idnomearquivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Nomearquivo[ idnomearquivo=" + idnomearquivo + " ]";
    }
    
}
