/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.manageBean;

import br.com.financemate.dao.AcessoDao;
import br.com.financemate.dao.TipoAcessoDao;
import br.com.financemate.model.Acesso;
import br.com.financemate.model.Tipoacesso;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;



@Named
@ViewScoped
public class CadAcessoMB implements Serializable{
    
    private Tipoacesso tipoacesso;
    private Acesso acesso;
    @EJB
    private AcessoDao acessoDao;
    @EJB
    private TipoAcessoDao tipoAcessoDao;
    
    
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tipoacesso = (Tipoacesso) session.getAttribute("tipoacesso");
        session.removeAttribute("tipoacesso");
        if (tipoacesso == null) {
            tipoacesso = new Tipoacesso();
            acesso = new Acesso();
        }else{
            acesso = tipoacesso.getAcesso();
        }
    }

    public Tipoacesso getTipoacesso() {
        return tipoacesso;
    }

    public void setTipoacesso(Tipoacesso tipoacesso) {
        this.tipoacesso = tipoacesso;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public AcessoDao getAcessoDao() {
        return acessoDao;
    }

    public void setAcessoDao(AcessoDao acessoDao) {
        this.acessoDao = acessoDao;
    }

    public TipoAcessoDao getTipoAcessoDao() {
        return tipoAcessoDao;
    }

    public void setTipoAcessoDao(TipoAcessoDao tipoAcessoDao) {
        this.tipoAcessoDao = tipoAcessoDao;
    }
    
    
    public void salvarAcesso(){
        if (validarDados()) {
            acesso = acessoDao.update(acesso);
            tipoacesso.setAcesso(acesso);
            tipoacesso = tipoAcessoDao.update(tipoacesso);
            RequestContext.getCurrentInstance().closeDialog(tipoacesso);
        }
    }
    
    
    public boolean validarDados(){
        if (tipoacesso.getDescricao() == null || tipoacesso.getDescricao().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }
    
    
    public void cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new Tipoacesso());
    }
    
}
