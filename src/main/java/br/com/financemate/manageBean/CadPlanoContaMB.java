package br.com.financemate.manageBean;

import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.TipoPlanoContasDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadPlanoContaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Planocontas planocontas;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoPlanoContas;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private TipoPlanoContasDao tipoPlanoContasDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        planocontas = (Planocontas) session.getAttribute("planoconta");
        session.removeAttribute("planoconta");
        gerarlistaTipoPlanoContas();
        if (planocontas == null) {
            planocontas = new Planocontas();
        }//else{
        //	 tipoPlanoContas = planocontas.get;
        //}

    }

    public Tipoplanocontas getTipoPlanoContas() {
        return tipoPlanoContas;
    }

    public void setTipoPlanoContas(Tipoplanocontas tipoPlanoContas) {
        this.tipoPlanoContas = tipoPlanoContas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
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

    public void gerarlistaTipoPlanoContas() {
        listarTipoPlanoContas = tipoPlanoContasDao.list("select t from Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }
    }

    public String salvarPlanoContas() {
        planocontas = planoContasDao.update(planocontas);
        RequestContext.getCurrentInstance().closeDialog(planocontas);
        return "";
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

}
