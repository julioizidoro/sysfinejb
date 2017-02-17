package br.com.financemate.manageBean;

import br.com.financemate.dao.TipoPlanoContasDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class TipoPlanoContaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoplanocontas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private TipoPlanoContasDao tipoPlanoContasDao;

    @PostConstruct
    public void init() {
        gerarListaTipoPlanoConta();
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public Tipoplanocontas getTipoplanocontas() {
        return tipoplanocontas;
    }

    public void setTipoplanocontas(Tipoplanocontas tipoplanocontas) {
        this.tipoplanocontas = tipoplanocontas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public void gerarListaTipoPlanoConta() {
        listarTipoPlanoContas = tipoPlanoContasDao.list("Select t From Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }

    }

    public void retornoDialogNovo(SelectEvent event) {
        Tipoplanocontas tipoplanocontas = (Tipoplanocontas) event.getObject();
        if (tipoplanocontas.getIdtipoplanocontas() != null) {
            listarTipoPlanoContas.add(tipoplanocontas);
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
    }
    
    
    public void retornoDialogEdicao(SelectEvent event) {
        Tipoplanocontas tipoplanocontas = (Tipoplanocontas) event.getObject();
        if (tipoplanocontas.getIdtipoplanocontas() != null) {
            gerarListaTipoPlanoConta();
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String novo() {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()) {
            tipoplanocontas = new Tipoplanocontas();
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta", options, null);
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";

    }

    public String editar(Tipoplanocontas tipoplanocontas) {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getAtipoplanocontas()) {
            if (tipoplanocontas != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("tipoplanocontas", tipoplanocontas);
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("closable", false);
                RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta", options, null);
            }
            return "";
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }

    }
}
