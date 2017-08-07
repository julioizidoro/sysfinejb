package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.CobrancaDao;
import br.com.financemate.dao.CobrancaParcelasDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.HistoricoCobrancaDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Cobrancaparcelas;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Historicocobranca;
import br.com.financemate.model.Vendas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CobrancaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Contasreceber contasReceber;
    private Cobranca cobranca;
    private List<Contasreceber> listaContasReceber;
    private List<Historicocobranca> listaHistorico;
    private Historicocobranca historico;
    private Vendas venda;
    private List<Contasreceber> listaContasSelecionadas;
    private Cobrancaparcelas cobrancaParcela;
    @EJB
    private CobrancaDao cobrancaDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CobrancaParcelasDao cobrancaParcelaDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private HistoricoCobrancaDao historicoCobrancaDao;
    private Boolean noveDigito1 = false;
    private Boolean noveDigito2 = false;
    private String maskFone1 = "(99) 9999-9999";
    private String maskFone2 = "(99) 9999-9999";

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        //listaContasSelecionadas = (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
        if (listaContasSelecionadas == null) {
            listaContasSelecionadas = new ArrayList<>();
            listaContasSelecionadas.add(contasReceber);
        }
        gerarListaCliente();
        cliente = contasReceber.getCliente();
        cobranca = (Cobranca) session.getAttribute("cobranca");
        if (cobranca == null) {
            cobranca = cobrancaDao.find("select c.cobranca from Cobrancaparcelas c "
                    + " where c.contasreceber.idcontasReceber=" + contasReceber.getIdcontasReceber());
        }
        if (cobranca == null) {
            cobranca = new Cobranca();
            listaHistorico = new ArrayList<>();
        } else {
            gerarListaHistorico();
            if (listaHistorico == null) {
                listaHistorico = new ArrayList<>();
            }
        }
    }

    public Cobrancaparcelas getCobrancaParcela() {
        return cobrancaParcela;
    }

    public void setCobrancaParcela(Cobrancaparcelas cobrancaParcela) {
        this.cobrancaParcela = cobrancaParcela;
    }

    public List<Contasreceber> getListaContasSelecionadas() {
        return listaContasSelecionadas;
    }

    public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
        this.listaContasSelecionadas = listaContasSelecionadas;
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    public List<Historicocobranca> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<Historicocobranca> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    public Historicocobranca getHistorico() {
        return historico;
    }

    public void setHistorico(Historicocobranca historico) {
        this.historico = historico;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
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

    public CobrancaDao getCobrancaDao() {
        return cobrancaDao;
    }

    public void setCobrancaDao(CobrancaDao cobrancaDao) {
        this.cobrancaDao = cobrancaDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public CobrancaParcelasDao getCobrancaParcelaDao() {
        return cobrancaParcelaDao;
    }

    public void setCobrancaParcelaDao(CobrancaParcelasDao cobrancaParcelaDao) {
        this.cobrancaParcelaDao = cobrancaParcelaDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public HistoricoCobrancaDao getHistoricoCobrancaDao() {
        return historicoCobrancaDao;
    }

    public void setHistoricoCobrancaDao(HistoricoCobrancaDao historicoCobrancaDao) {
        this.historicoCobrancaDao = historicoCobrancaDao;
    }

    public Boolean getNoveDigito1() {
        return noveDigito1;
    }

    public void setNoveDigito1(Boolean noveDigito1) {
        this.noveDigito1 = noveDigito1;
    }

     

    public Boolean getNoveDigito2() {
        return noveDigito2;
    }

    public void setNoveDigito2(Boolean noveDigito2) {
        this.noveDigito2 = noveDigito2;
    }

    public String getMaskFone1() {
        return maskFone1;
    }

    public void setMaskFone1(String maskFone1) {
        this.maskFone1 = maskFone1;
    }

    public String getMaskFone2() {
        return maskFone2;
    }

    public void setMaskFone2(String maskFone2) {
        this.maskFone2 = maskFone2;
    }
    
    

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null || listaCliente.isEmpty()) {
            listaCliente = new ArrayList<>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String novoHistorico() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasReceber", contasReceber);
        session.setAttribute("cobranca", cobranca);
        return "historico";
    }

    public String salvarDadosCobranca() {
        cobranca.setVencimentooriginal(contasReceber.getDataVencimento());
        if (cobranca.getAlterarvencimento() != null) {
            contasReceber.setDataVencimento(cobranca.getAlterarvencimento());
        }
        cobranca = cobrancaDao.update(cobranca);
        contasReceberDao.update(contasReceber);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Dados  salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "";
    }

    public String novoHistoricos() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasReceber", contasReceber);
        session.setAttribute("cobranca", cobranca);
        return "historicos";
    }

    public String salvarHitorico() {
        if (cobranca.getIdcobranca() == null) {
            cobranca = cobrancaDao.update(cobranca);

        }
        historico.setData(new Date());
        historico.setCobranca(cobranca);
        historico.setUsuario(usuarioLogadoMB.getUsuario());
        try {
            historico = cobrancaDao.salvar(historico);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        cobranca.getHistoricocobrancaList().add(historico);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Historico de Cobrançaa Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "cobranca";
    }

    //Para chamada da tela, na tela principal
    public String editarHistorico(Historicocobranca historicocobranca) {
        if (historico != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
        }
        return "editarHistorico";
    }

    // Para chamada da dialog dentro de algum modulo
    public String editarHistoricos(Historicocobranca historicocobranca) {
        if (historico != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
            session.setAttribute("contasReceber", contasReceber);
        }
        return "editarHistoricos";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("cobranca");
        session.removeAttribute("listaContasSelecionadas");
        session.removeAttribute("contasReceber");
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    //Para chamada da tela, na tela principal
    public String listaContasCobranca() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        return "listaContaCobranca";
    }

    // Para chamada da dialog dentro de algum modulo
    public String listaContasCobrancas() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        return "listaContaCobrancas";
    }

    //Para chamada da tela, na tela principal
    public String cobranca() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.getAttribute("listaHistorico");
        return "cobranca";
    }

    // Para chamada da dialog dentro de algum modulo
    public String cobrancas() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.getAttribute("listaHistorico");
        return "cobrancas";
    }

    //Para chamada da tela, na tela principal
    public String historicoCobranca() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        gerarListaHistorico();
        return "historicoCobranca";
    }

    // Para chamada da dialog dentro de algum modulo
    public String historicoCobrancas() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        gerarListaHistorico();
        return "historicoCobrancas";
    }

    public void gerarListaHistorico() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (cobranca == null) {
            cobranca = (Cobranca) session.getAttribute("cobranca");
        }
        listaHistorico = historicoCobrancaDao.list("select h from Historicocobranca h where h.cobranca.idcobranca=" + cobranca.getIdcobranca());
        if (listaHistorico == null) {
            listaHistorico = new ArrayList<>();
        }
    }

    public void salvarCobrancasParcelas() {
        List<Cobrancaparcelas> listaCobrancaParcelas;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cobranca = (Cobranca) session.getAttribute("cobranca");
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        String sql = "select cp from Cobrancaparcelas cp ";
        sql = sql + " where cp.contasreceber.idcontasReceber=" + contasReceber.getIdcontasReceber();
        sql = sql + " and cp.cobranca.idcobranca=" + cobranca.getIdcobranca();
        listaCobrancaParcelas = cobrancaParcelaDao.list(sql);
        if (listaCobrancaParcelas.isEmpty()) {
            for (int i = 0; i < listaContasSelecionadas.size(); i++) {
                if (cobranca != null && contasReceber != null) {
                    cobrancaParcela = new Cobrancaparcelas();
                    cobrancaParcela.setCobranca(cobranca);
                    cobrancaParcela.setContasreceber(listaContasSelecionadas.get(i));
                    cobrancaParcelaDao.update(cobrancaParcela);
                    session.removeAttribute("listaContasSelecionadas");
                }
            }
        }

    }
    
    public void verificarDigitoFone1(){
        if (noveDigito1) {
            maskFone1 = "(99) 99999-9999";
        }else{
            maskFone1 = "(99) 9999-9999";
        }
    }
    
    
    public void verificarDigitoFone2(){
        if (noveDigito2) {
            maskFone2 = "(99) 99999-9999";
        }else{
            maskFone2 = "(99) 9999-9999";
        }
    }

}
