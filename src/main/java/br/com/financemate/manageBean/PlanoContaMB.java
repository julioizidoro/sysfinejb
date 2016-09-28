package br.com.financemate.manageBean;

import br.com.financemate.dao.PlanoContasDao;
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

import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class PlanoContaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Planocontas> listarPlanoContas;
    private Planocontas planocontas;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private TipoPlanoContasDao tipoPlanoContasDao;

    @PostConstruct
    public void init() {
        gerarListaPlanoConta();
        gerarlistaTipoPlanoContas();
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Planocontas> getListarPlanoContas() {
        return listarPlanoContas;
    }

    public void setListarPlanoContas(List<Planocontas> listarPlanoContas) {
        this.listarPlanoContas = listarPlanoContas;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public PlanoContasDao getPlanoContasDao() {
        return planoContasDao;
    }

    public void setPlanoContasDao(PlanoContasDao planoContasDao) {
        this.planoContasDao = planoContasDao;
    }

    public TipoPlanoContasDao getTipoPlanoContasDao() {
        return tipoPlanoContasDao;
    }

    public void setTipoPlanoContasDao(TipoPlanoContasDao tipoPlanoContasDao) {
        this.tipoPlanoContasDao = tipoPlanoContasDao;
    }

    public void gerarListaPlanoConta() {
        try {
            listarPlanoContas = planoContasDao.list("Select p from Planocontas p  order by p.descricao");
            if (listarPlanoContas == null) {
                listarPlanoContas = new ArrayList<Planocontas>();
            }

        } catch (Exception ex) {
            Logger.getLogger(PlanoContaMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro! ", "Ao gerar Lista Plano de Contas");
        }

    }

    public void gerarlistaTipoPlanoContas() {
        listarTipoPlanoContas = tipoPlanoContasDao.list("select t from Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas != null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }

    }

    public String novo() {
        if (planocontas == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("planoconta", planocontas);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            options.put("contentWidth", 500);
            RequestContext.getCurrentInstance().openDialog("cadPlanoConta", options, null);
        } else {

            mostrarMensagem(null, "Erro! ", "Acesso Negado");

        }
        return "";
    }

    public String editar(Planocontas planocontas) {
        if (planocontas != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("planoconta", planocontas);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            options.put("contentWidth", 500);
            RequestContext.getCurrentInstance().openDialog("cadPlanoConta", options, null);

        }
        return "";
    }

    public void retornoDialog(SelectEvent event) {
        Planocontas planocontas = (Planocontas) event.getObject();
        if (planocontas.getIdplanoContas() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
        gerarListaPlanoConta();
    }

    public String pesquisar() {
        gerarListaPlanoConta();
        return "consPlanoConta";
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

}
