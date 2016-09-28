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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "arquivocontaspagar")
public class Arquivocontaspagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idarquivoContasPagar")
    private Integer idarquivoContasPagar;
    @Size(max = 100)
    @Column(name = "nomeArquivo01")
    private String nomeArquivo01;
    @Lob
    @Column(name = "arquivo01")
    private byte[] arquivo01;
    @Size(max = 100)
    @Column(name = "nomeArquivo02")
    private String nomeArquivo02;
    @Lob
    @Column(name = "arquivo02")
    private byte[] arquivo02;
    @JoinColumn(name = "contasPagar_idcontasPagar", referencedColumnName = "idcontasPagar")
    @ManyToOne(optional = false)
    private Contaspagar contaspagar;

    public Arquivocontaspagar() {
    }

    public Arquivocontaspagar(Integer idarquivoContasPagar) {
        this.idarquivoContasPagar = idarquivoContasPagar;
    }

    public Integer getIdarquivoContasPagar() {
        return idarquivoContasPagar;
    }

    public void setIdarquivoContasPagar(Integer idarquivoContasPagar) {
        this.idarquivoContasPagar = idarquivoContasPagar;
    }

    public String getNomeArquivo01() {
        return nomeArquivo01;
    }

    public void setNomeArquivo01(String nomeArquivo01) {
        this.nomeArquivo01 = nomeArquivo01;
    }

    public byte[] getArquivo01() {
        return arquivo01;
    }

    public void setArquivo01(byte[] arquivo01) {
        this.arquivo01 = arquivo01;
    }

    public String getNomeArquivo02() {
        return nomeArquivo02;
    }

    public void setNomeArquivo02(String nomeArquivo02) {
        this.nomeArquivo02 = nomeArquivo02;
    }

    public byte[] getArquivo02() {
        return arquivo02;
    }

    public void setArquivo02(byte[] arquivo02) {
        this.arquivo02 = arquivo02;
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
        hash += (idarquivoContasPagar != null ? idarquivoContasPagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Arquivocontaspagar)) {
            return false;
        }
        Arquivocontaspagar other = (Arquivocontaspagar) object;
        if ((this.idarquivoContasPagar == null && other.idarquivoContasPagar != null) || (this.idarquivoContasPagar != null && !this.idarquivoContasPagar.equals(other.idarquivoContasPagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Arquivocontaspagar[ idarquivoContasPagar=" + idarquivoContasPagar + " ]";
    }
    
}
