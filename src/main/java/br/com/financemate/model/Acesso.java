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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Greici
 */
@Entity
@Table(name = "acesso")
public class Acesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacesso")
    private Integer idacesso;
    @Column(name = "cadastro")
    private Boolean cadastro;
    @Column(name = "cliente")
    private Boolean cliente;
    @Column(name = "acliente")
    private Boolean acliente;
    @Column(name = "ecliente")
    private Boolean ecliente;
    @Column(name = "icliente")
    private Boolean icliente;
    @Column(name = "banco")
    private Boolean banco;
    @Column(name = "ibanco")
    private Boolean ibanco;
    @Column(name = "abanco")
    private Boolean abanco;
    @Column(name = "ebanco")
    private Boolean ebanco;
    @Column(name = "planocontas")
    private Boolean planocontas;
    @Column(name = "iplanocontas")
    private Boolean iplanocontas;
    @Column(name = "aplanocontas")
    private Boolean aplanocontas;
    @Column(name = "eplanocontas")
    private Boolean eplanocontas;
    @Column(name = "usuario")
    private Boolean usuario;
    @Column(name = "iusuario")
    private Boolean iusuario;
    @Column(name = "ausuario")
    private Boolean ausuario;
    @Column(name = "eusuario")
    private Boolean eusuario;
    @Column(name = "produto")
    private Boolean produto;
    @Column(name = "iproduto")
    private Boolean iproduto;
    @Column(name = "aproduto")
    private Boolean aproduto;
    @Column(name = "eproduto")
    private Boolean eproduto;
    @Column(name = "contaspagar")
    private Boolean contaspagar;
    @Column(name = "icontaspagar")
    private Boolean icontaspagar;
    @Column(name = "acontaspargar")
    private Boolean acontaspargar;
    @Column(name = "econtaspargar")
    private Boolean econtaspargar;
    @Column(name = "arqcontaspargar")
    private Boolean arqcontaspargar;
    @Column(name = "lcontaspargar")
    private Boolean lcontaspargar;
    @Column(name = "ucontaspargar")
    private Boolean ucontaspargar;
    @Column(name = "contasreceber")
    private Boolean contasreceber;
    @Column(name = "rpagamentos")
    private Boolean rpagamentos;
    @Column(name = "autcontaspargar")
    private Boolean autcontaspargar;
    @Column(name = "icontasreceber")
    private Boolean icontasreceber;
    @Column(name = "acontasreceber")
    private Boolean acontasreceber;
    @Column(name = "econtasreceber")
    private Boolean econtasreceber;
    @Column(name = "rcontasreceber")
    private Boolean rcontasreceber;
    @Column(name = "outroslancamentos")
    private Boolean outroslancamentos;
    @Column(name = "ioutroslancamentos")
    private Boolean ioutroslancamentos;
    @Column(name = "aoutroslancamentos")
    private Boolean aoutroslancamentos;
    @Column(name = "eoutroslancamentos")
    private Boolean eoutroslancamentos;
    @Column(name = "rvendas")
    private Boolean rvendas;
    @Column(name = "rconciliacao")
    private Boolean rconciliacao;
    @Column(name = "rreceber")
    private Boolean rreceber;
    @Column(name = "rpagar")
    private Boolean rpagar;
    @Column(name = "rfluxo")
    private Boolean rfluxo;
    @Column(name = "vendas")
    private Boolean vendas;
    @Column(name = "ivendas")
    private Boolean ivendas;
    @Column(name = "evendas")
    private Boolean evendas;
    @Column(name = "avendas")
    private Boolean avendas;
    @Column(name = "pvendas")
    private Boolean pvendas;
    @Column(name = "cvendas")
    private Boolean cvendas;
    @Column(name = "fvendas")
    private Boolean fvendas;
    @Column(name = "tipoplanocontas")
    private Boolean tipoplanocontas;
    @Column(name = "itipoplanocontas")
    private Boolean itipoplanocontas;
    @Column(name = "atipoplanocontas")
    private Boolean atipoplanocontas;
    @Column(name = "etipoplanocontas")
    private Boolean etipoplanocontas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acesso")
    private List<Tipoacesso> tipoacessoList;

    public Acesso() {
    }

    public Acesso(Integer idacesso) {
        this.idacesso = idacesso;
    }

    public Integer getIdacesso() {
        return idacesso;
    }

    public void setIdacesso(Integer idacesso) {
        this.idacesso = idacesso;
    }

    public Boolean getCadastro() {
        return cadastro;
    }

    public void setCadastro(Boolean cadastro) {
        this.cadastro = cadastro;
    }

    public Boolean getCliente() {
        return cliente;
    }

    public void setCliente(Boolean cliente) {
        this.cliente = cliente;
    }

    public Boolean getAcliente() {
        return acliente;
    }

    public void setAcliente(Boolean acliente) {
        this.acliente = acliente;
    }

    public Boolean getEcliente() {
        return ecliente;
    }

    public void setEcliente(Boolean ecliente) {
        this.ecliente = ecliente;
    }

    public Boolean getIcliente() {
        return icliente;
    }

    public void setIcliente(Boolean icliente) {
        this.icliente = icliente;
    }

    public Boolean getBanco() {
        return banco;
    }

    public void setBanco(Boolean banco) {
        this.banco = banco;
    }

    public Boolean getIbanco() {
        return ibanco;
    }

    public void setIbanco(Boolean ibanco) {
        this.ibanco = ibanco;
    }

    public Boolean getAbanco() {
        return abanco;
    }

    public void setAbanco(Boolean abanco) {
        this.abanco = abanco;
    }

    public Boolean getEbanco() {
        return ebanco;
    }

    public void setEbanco(Boolean ebanco) {
        this.ebanco = ebanco;
    }

    public Boolean getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Boolean planocontas) {
        this.planocontas = planocontas;
    }

    public Boolean getIplanocontas() {
        return iplanocontas;
    }

    public void setIplanocontas(Boolean iplanocontas) {
        this.iplanocontas = iplanocontas;
    }

    public Boolean getAplanocontas() {
        return aplanocontas;
    }

    public void setAplanocontas(Boolean aplanocontas) {
        this.aplanocontas = aplanocontas;
    }

    public Boolean getEplanocontas() {
        return eplanocontas;
    }

    public void setEplanocontas(Boolean eplanocontas) {
        this.eplanocontas = eplanocontas;
    }

    public Boolean getUsuario() {
        return usuario;
    }

    public void setUsuario(Boolean usuario) {
        this.usuario = usuario;
    }

    public Boolean getIusuario() {
        return iusuario;
    }

    public void setIusuario(Boolean iusuario) {
        this.iusuario = iusuario;
    }

    public Boolean getAusuario() {
        return ausuario;
    }

    public void setAusuario(Boolean ausuario) {
        this.ausuario = ausuario;
    }

    public Boolean getEusuario() {
        return eusuario;
    }

    public void setEusuario(Boolean eusuario) {
        this.eusuario = eusuario;
    }

    public Boolean getProduto() {
        return produto;
    }

    public void setProduto(Boolean produto) {
        this.produto = produto;
    }

    public Boolean getIproduto() {
        return iproduto;
    }

    public void setIproduto(Boolean iproduto) {
        this.iproduto = iproduto;
    }

    public Boolean getAproduto() {
        return aproduto;
    }

    public void setAproduto(Boolean aproduto) {
        this.aproduto = aproduto;
    }

    public Boolean getEproduto() {
        return eproduto;
    }

    public void setEproduto(Boolean eproduto) {
        this.eproduto = eproduto;
    }

    public Boolean getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Boolean contaspagar) {
        this.contaspagar = contaspagar;
    }

    public Boolean getIcontaspagar() {
        return icontaspagar;
    }

    public void setIcontaspagar(Boolean icontaspagar) {
        this.icontaspagar = icontaspagar;
    }

    public Boolean getAcontaspargar() {
        return acontaspargar;
    }

    public void setAcontaspargar(Boolean acontaspargar) {
        this.acontaspargar = acontaspargar;
    }

    public Boolean getEcontaspargar() {
        return econtaspargar;
    }

    public void setEcontaspargar(Boolean econtaspargar) {
        this.econtaspargar = econtaspargar;
    }

    public Boolean getArqcontaspargar() {
        return arqcontaspargar;
    }

    public void setArqcontaspargar(Boolean arqcontaspargar) {
        this.arqcontaspargar = arqcontaspargar;
    }

    public Boolean getLcontaspargar() {
        return lcontaspargar;
    }

    public void setLcontaspargar(Boolean lcontaspargar) {
        this.lcontaspargar = lcontaspargar;
    }

    public Boolean getUcontaspargar() {
        return ucontaspargar;
    }

    public void setUcontaspargar(Boolean ucontaspargar) {
        this.ucontaspargar = ucontaspargar;
    }

    public Boolean getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Boolean contasreceber) {
        this.contasreceber = contasreceber;
    }

    public Boolean getRpagamentos() {
        return rpagamentos;
    }

    public void setRpagamentos(Boolean rpagamentos) {
        this.rpagamentos = rpagamentos;
    }

    public Boolean getAutcontaspargar() {
        return autcontaspargar;
    }

    public void setAutcontaspargar(Boolean autcontaspargar) {
        this.autcontaspargar = autcontaspargar;
    }

    public Boolean getIcontasreceber() {
        return icontasreceber;
    }

    public void setIcontasreceber(Boolean icontasreceber) {
        this.icontasreceber = icontasreceber;
    }

    public Boolean getAcontasreceber() {
        return acontasreceber;
    }

    public void setAcontasreceber(Boolean acontasreceber) {
        this.acontasreceber = acontasreceber;
    }

    public Boolean getEcontasreceber() {
        return econtasreceber;
    }

    public void setEcontasreceber(Boolean econtasreceber) {
        this.econtasreceber = econtasreceber;
    }

    public Boolean getRcontasreceber() {
        return rcontasreceber;
    }

    public void setRcontasreceber(Boolean rcontasreceber) {
        this.rcontasreceber = rcontasreceber;
    }

    public Boolean getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Boolean outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public Boolean getIoutroslancamentos() {
        return ioutroslancamentos;
    }

    public void setIoutroslancamentos(Boolean ioutroslancamentos) {
        this.ioutroslancamentos = ioutroslancamentos;
    }

    public Boolean getAoutroslancamentos() {
        return aoutroslancamentos;
    }

    public void setAoutroslancamentos(Boolean aoutroslancamentos) {
        this.aoutroslancamentos = aoutroslancamentos;
    }

    public Boolean getEoutroslancamentos() {
        return eoutroslancamentos;
    }

    public void setEoutroslancamentos(Boolean eoutroslancamentos) {
        this.eoutroslancamentos = eoutroslancamentos;
    }

    public Boolean getRvendas() {
        return rvendas;
    }

    public void setRvendas(Boolean rvendas) {
        this.rvendas = rvendas;
    }

    public Boolean getRconciliacao() {
        return rconciliacao;
    }

    public void setRconciliacao(Boolean rconciliacao) {
        this.rconciliacao = rconciliacao;
    }

    public Boolean getRreceber() {
        return rreceber;
    }

    public void setRreceber(Boolean rreceber) {
        this.rreceber = rreceber;
    }

    public Boolean getRpagar() {
        return rpagar;
    }

    public void setRpagar(Boolean rpagar) {
        this.rpagar = rpagar;
    }

    public Boolean getRfluxo() {
        return rfluxo;
    }

    public void setRfluxo(Boolean rfluxo) {
        this.rfluxo = rfluxo;
    }

    public Boolean getVendas() {
        return vendas;
    }

    public void setVendas(Boolean vendas) {
        this.vendas = vendas;
    }

    public Boolean getIvendas() {
        return ivendas;
    }

    public void setIvendas(Boolean ivendas) {
        this.ivendas = ivendas;
    }

    public Boolean getEvendas() {
        return evendas;
    }

    public void setEvendas(Boolean evendas) {
        this.evendas = evendas;
    }

    public Boolean getAvendas() {
        return avendas;
    }

    public void setAvendas(Boolean avendas) {
        this.avendas = avendas;
    }

    public Boolean getPvendas() {
        return pvendas;
    }

    public void setPvendas(Boolean pvendas) {
        this.pvendas = pvendas;
    }

    public Boolean getCvendas() {
        return cvendas;
    }

    public void setCvendas(Boolean cvendas) {
        this.cvendas = cvendas;
    }

    public Boolean getFvendas() {
        return fvendas;
    }

    public void setFvendas(Boolean fvendas) {
        this.fvendas = fvendas;
    }

    public Boolean getTipoplanocontas() {
        return tipoplanocontas;
    }

    public void setTipoplanocontas(Boolean tipoplanocontas) {
        this.tipoplanocontas = tipoplanocontas;
    }

    public Boolean getItipoplanocontas() {
        return itipoplanocontas;
    }

    public void setItipoplanocontas(Boolean itipoplanocontas) {
        this.itipoplanocontas = itipoplanocontas;
    }

    public Boolean getAtipoplanocontas() {
        return atipoplanocontas;
    }

    public void setAtipoplanocontas(Boolean atipoplanocontas) {
        this.atipoplanocontas = atipoplanocontas;
    }

    public Boolean getEtipoplanocontas() {
        return etipoplanocontas;
    }

    public void setEtipoplanocontas(Boolean etipoplanocontas) {
        this.etipoplanocontas = etipoplanocontas;
    }

    public List<Tipoacesso> getTipoacessoList() {
        return tipoacessoList;
    }

    public void setTipoacessoList(List<Tipoacesso> tipoacessoList) {
        this.tipoacessoList = tipoacessoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacesso != null ? idacesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // 
        if (!(object instanceof Acesso)) {
            return false;
        }
        Acesso other = (Acesso) object;
        if ((this.idacesso == null && other.idacesso != null) || (this.idacesso != null && !this.idacesso.equals(other.idacesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.financemate.model.Acesso[ idacesso=" + idacesso + " ]";
    }
    
}
