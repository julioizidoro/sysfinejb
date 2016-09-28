package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.CobrancaDao;
import br.com.financemate.dao.HistoricoCobrancaDao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Historicocobranca;
import java.util.ArrayList;
import javax.ejb.EJB;

@Named
@ViewScoped
public class HistoricoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Contasreceber contasReceber;
    private List<Contasreceber> listaContasReceber;
    private Historicocobranca historicaCobranca;
    private Cobranca cobranca;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CobrancaDao cobrancaDao;
    @EJB
    private HistoricoCobrancaDao historicoCobrancaDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contasReceber");
        cobranca = (Cobranca) session.getAttribute("cobranca");
        historicaCobranca = (Historicocobranca) session.getAttribute("historico");
        gerarListaCliente();
        if (historicaCobranca == null) {
            historicaCobranca = new Historicocobranca();
            historicaCobranca.setCobranca(cobranca);
        }

    }

    public Historicocobranca getHistoricaCobranca() {
        return historicaCobranca;
    }

    public void setHistoricaCobranca(Historicocobranca historicaCobranca) {
        this.historicaCobranca = historicaCobranca;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public CobrancaDao getCobrancaDao() {
        return cobrancaDao;
    }

    public void setCobrancaDao(CobrancaDao cobrancaDao) {
        this.cobrancaDao = cobrancaDao;
    }

    public HistoricoCobrancaDao getHistoricoCobrancaDao() {
        return historicoCobrancaDao;
    }

    public void setHistoricoCobrancaDao(HistoricoCobrancaDao historicoCobrancaDao) {
        this.historicoCobrancaDao = historicoCobrancaDao;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + "" + "%' order by c.razaoSocial");
        if (listaCliente == null || listaCliente.isEmpty()) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    //Chamada na tela principal
    public String salvarHitorico() {
        if (cobranca.getIdcobranca() == null) {
            cobranca = cobrancaDao.update(cobranca);

        }
        historicaCobranca.setData(new Date());
        historicaCobranca.setCobranca(cobranca);
        historicaCobranca.setUsuario(usuarioLogadoMB.getUsuario());
        //historicaCobranca = cobrancaDao.update(historicaCobranca);
        cobranca.getHistoricocobrancaList().add(historicaCobranca);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Hist�rico de Cobran�a Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "historicoCobranca";
    }

    //Chamada na tela dentro da pages
    public String salvarHitoricos() {
        if (cobranca.getIdcobranca() == null) {
            cobranca = cobrancaDao.update(cobranca);

        }
        historicaCobranca.setData(new Date());
        historicaCobranca.setCobranca(cobranca);
        historicaCobranca.setUsuario(usuarioLogadoMB.getUsuario());
        //historicaCobranca = cobrancaDao.update(historicaCobranca);
        cobranca.getHistoricocobrancaList().add(historicaCobranca);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Hist�rico de Cobran�a Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "historicoCobrancas";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        return "cobrancas";
    }

    public String cancelarPrincipal() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        return "cobranca";
    }

    public String salvarEdicaoPrincipal() {
        historicoCobrancaDao.update(historicaCobranca);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        mensagem mensagem = new mensagem();
        mensagem.historico();
        return "cobranca";
    }

    public String salvarEdicao() {
        historicoCobrancaDao.update(historicaCobranca);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("historico");
        mensagem mensagem = new mensagem();
        mensagem.historico();
        return "cobrancas";
    }

}
