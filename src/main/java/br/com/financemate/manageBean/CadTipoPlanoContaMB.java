package br.com.financemate.manageBean;

import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.TipoPlanoContasDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadTipoPlanoContaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Tipoplanocontas tipoplanocontas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    private List<Planocontas> listarPlanoContas;
    private List<Planocontatipo> listaPlanoContaTipo;
    private Planocontatipo planocontastipo;
    private Planocontas planocontas;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private TipoPlanoContasDao tipoPlanoContasDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tipoplanocontas = (Tipoplanocontas) session.getAttribute("tipoplanocontas");
        session.removeAttribute("tipoplanocontas");
        gerarListaTipoPlanoConta();
        gerarListaPlanoContas();
        if (tipoplanocontas == null) {
            tipoplanocontas = new Tipoplanocontas();
            listaPlanoContaTipo = new ArrayList<Planocontatipo>();
        } else {
            try {
                listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + tipoplanocontas.getIdtipoplanocontas());
                if (listaPlanoContaTipo == null) {
                    listaPlanoContaTipo = new ArrayList<Planocontatipo>();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public List<Planocontas> getListarPlanoContas() {
        return listarPlanoContas;
    }

    public void setListarPlanoContas(List<Planocontas> listarPlanoContas) {
        this.listarPlanoContas = listarPlanoContas;
    }

    public List<Planocontatipo> getListaPlanoContaTipo() {
        return listaPlanoContaTipo;
    }

    public void setListaPlanoContaTipo(List<Planocontatipo> listaPlanoContaTipo) {
        this.listaPlanoContaTipo = listaPlanoContaTipo;
    }

    public Planocontatipo getPlanocontastipo() {
        return planocontastipo;
    }

    public void setPlanocontastipo(Planocontatipo planocontastipo) {
        this.planocontastipo = planocontastipo;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public String salvar() {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()) {
            tipoplanocontas = tipoPlanoContasDao.update(tipoplanocontas);
            if (tipoplanocontas != null) {
                for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                    if (listaPlanoContaTipo.get(i).getTipoplanocontas() == null) {
                        listaPlanoContaTipo.get(i).setTipoplanocontas(tipoplanocontas);
                        planoContaTipoDao.update(listaPlanoContaTipo.get(i));
                    }
                }
            }
            RequestContext.getCurrentInstance().closeDialog(tipoplanocontas);

        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public void gerarListaTipoPlanoConta() {
        listarTipoPlanoContas = tipoPlanoContasDao.list("select t from Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }

    }

    public void gerarListaPlanoContas() {
        try {
            listarPlanoContas = planoContasDao.list("Select p from Planocontas p  order by p.descricao");
            if (listarPlanoContas == null) {
                listarPlanoContas = new ArrayList<Planocontas>();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void gerarListaPlanoContasTipo() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p");
            if (listaPlanoContaTipo == null) {
                listaPlanoContaTipo = new ArrayList<Planocontatipo>();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void adicionarPlanoConta() {
        if (planocontas != null) {
            planocontastipo = new Planocontatipo();
            planocontastipo.setPlanocontas(planocontas);
            listaPlanoContaTipo.add(planocontastipo);
        }
    }

}
