package br.com.financemate.manageBean;

import br.com.financemate.dao.OperacaoUsuarioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Operacaousuairo;
import javax.ejb.EJB;

@Named
@ViewScoped
public class OperacoesUsuarioMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Contaspagar contasPagar;
    private Operacaousuairo operacaousuairo;
    private List<Operacaousuairo> listaOperacaousuairo;
    private String usuarioCadastrou;
    private String usuarioAgendou;
    private String usuarioLiberou;
    private String usuarioAutorizou;
    @EJB
    private OperacaoUsuarioDao operacaoUsuarioDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasPagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        gerarListaOperacoesUsuarios();
    }

    public String getUsuarioCadastrou() {
        return usuarioCadastrou;
    }

    public void setUsuarioCadastrou(String usuarioCadastrou) {
        this.usuarioCadastrou = usuarioCadastrou;
    }

    public String getUsuarioAgendou() {
        return usuarioAgendou;
    }

    public void setUsuarioAgendou(String usuarioAgendou) {
        this.usuarioAgendou = usuarioAgendou;
    }

    public String getUsuarioLiberou() {
        return usuarioLiberou;
    }

    public void setUsuarioLiberou(String usuarioLiberou) {
        this.usuarioLiberou = usuarioLiberou;
    }

    public String getUsuarioAutorizou() {
        return usuarioAutorizou;
    }

    public void setUsuarioAutorizou(String usuarioAutorizou) {
        this.usuarioAutorizou = usuarioAutorizou;
    }

    public List<Operacaousuairo> getListaOperacaousuairo() {
        return listaOperacaousuairo;
    }

    public void setListaOperacaousuairo(List<Operacaousuairo> listaOperacaousuairo) {
        this.listaOperacaousuairo = listaOperacaousuairo;
    }

    public Operacaousuairo getOperacaousuairo() {
        return operacaousuairo;
    }

    public void setOperacaousuairo(Operacaousuairo operacaousuairo) {
        this.operacaousuairo = operacaousuairo;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Contaspagar getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(Contaspagar contasPagar) {
        this.contasPagar = contasPagar;
    }

    public void gerarListaOperacoesUsuarios() {
        if (contasPagar != null) {
            listaOperacaousuairo = operacaoUsuarioDao.list("SELECT o FROM Operacaousuairo o "
                    + " WHERE o.contaspagar.idcontasPagar=" + contasPagar.getIdcontasPagar());
            if (listaOperacaousuairo == null) {
                listaOperacaousuairo = new ArrayList<>();
            }
        }
    }

    public String voltar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

}
