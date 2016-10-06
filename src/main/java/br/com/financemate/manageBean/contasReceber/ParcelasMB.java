package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ContasReceberDao;
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
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.model.Contasreceber;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ParcelasMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Contasreceber contasreceber;
    private Float total;
    private List<Contasreceber> listaParcela;
    @EJB
    private ContasReceberDao contasReceberDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasReceber");
        listaParcela = (List<Contasreceber>) session.getAttribute("listarParcelas");
        if (contasreceber != null) {
            if (listaParcela == null) {
                gerarListaParcelas();
            }
        }
    }

    public List<Contasreceber> getListaParcela() {
        return listaParcela;
    }

    public void setListaParcela(List<Contasreceber> listaParcela) {
        this.listaParcela = listaParcela;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Contasreceber getContasreceber() {
        return contasreceber;
    }

    public void setContasreceber(Contasreceber contasreceber) {
        this.contasreceber = contasreceber;
    }

    public void totalParcela() {
        for (int i = 0; i < listaParcela.size(); i++) {
            total = total + listaParcela.get(i).getValorParcela();
        }
    }

    public String voltar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasReceber", contasreceber);
        return "cadContasReceber";
    }

    public void gerarListaParcelas() {
        String sql = "SELECT c FROM Contasreceber c  WHERE c.numeroDocumento=" + contasreceber.getNumeroDocumento();
        listaParcela = contasReceberDao.list(sql);
        if (listaParcela == null) {
            listaParcela = new ArrayList<Contasreceber>();
        }
        totalParcela();
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

}
