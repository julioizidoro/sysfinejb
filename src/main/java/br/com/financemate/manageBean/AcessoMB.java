/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.manageBean;

import br.com.financemate.dao.TipoAcessoDao;
import br.com.financemate.model.Tipoacesso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


@Named
@ViewScoped
public class AcessoMB implements Serializable{
    
    
    private Tipoacesso tipoacesso;
    private List<Tipoacesso> listaTipoAcesso;
    @EJB
    private TipoAcessoDao tipoAcessoDao;
    
    
    @PostConstruct
    public void init(){
        listarTipoAcesso();
    }

    
    
    public Tipoacesso getTipoacesso() {
        return tipoacesso;
    }

    public void setTipoacesso(Tipoacesso tipoacesso) {
        this.tipoacesso = tipoacesso;
    }

    public List<Tipoacesso> getListaTipoAcesso() {
        return listaTipoAcesso;
    }

    public void setListaTipoAcesso(List<Tipoacesso> listaTipoAcesso) {
        this.listaTipoAcesso = listaTipoAcesso;
    }
    
    
    
    
    public void listarTipoAcesso(){
        listaTipoAcesso = tipoAcessoDao.list("Select t From Tipoacesso t");
        if (listaTipoAcesso == null || listaTipoAcesso.isEmpty()) {
            listaTipoAcesso = new ArrayList<>();
        }
    }
    
    
    public void retornoDialogNovo(SelectEvent event){
        Tipoacesso tipoacesso = (Tipoacesso) event.getObject();
        if (tipoacesso.getIdtipoacesso() != null) {
            if (listaTipoAcesso != null) {
                listaTipoAcesso.add(tipoacesso);
                mensagem msg = new mensagem();
                msg.saveMessagem();
            }
        }
    }
   
    
    public String novoAcesso() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 700);
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadAcesso", options, null);
        return "";
    }
    
    public void editarAcesso(Tipoacesso tipoacesso){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 700);
        options.put("closable", false);
        session.setAttribute("tipoacesso", tipoacesso);
        RequestContext.getCurrentInstance().openDialog("cadAcesso", options, null);
    }
}
