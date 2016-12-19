package br.com.financemate.manageBean;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.TipoPlanoContasDao;
import br.com.financemate.model.Banco;
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

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadClienteMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoplanocontas;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private TipoPlanoContasDao tipoPlanoContaDao;
    private Boolean noveDigito = false;
    private String maskTelefone = "(99)9999-9999";
    @EJB
    private BancoDao bancoDao;
    private Banco banco;
    

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("cliente");
        gerarListaTipoPlanoContas();
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setVisualizacao("Operacional");
        } else {
            tipoplanocontas = cliente.getTipoplanocontas();
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public TipoPlanoContasDao getTipoPlanoContaDao() {
        return tipoPlanoContaDao;
    }

    public void setTipoPlanoContaDao(TipoPlanoContasDao tipoPlanoContaDao) {
        this.tipoPlanoContaDao = tipoPlanoContaDao;
    }


    public String getMaskTelefone() {
        return maskTelefone;
    }

    public void setMaskTelefone(String maskTelefone) {
        this.maskTelefone = maskTelefone;
    }

    public Boolean getNoveDigito() {
        return noveDigito;
    }

    public void setNoveDigito(Boolean noveDigito) {
        this.noveDigito = noveDigito;
    }

    
    
    

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Cliente());
        return null;
    }

    public void gerarListaTipoPlanoContas() {
        listarTipoPlanoContas = tipoPlanoContaDao.list("select t from Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void salvar() {
        Cliente clienteBanco = cliente;
        cliente.setTipoplanocontas(tipoplanocontas);
        cliente = clienteDao.update(cliente);
        if (clienteBanco.getIdcliente() == null) {
            salvarBancoDefault(cliente);
        }
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }
    
    
    public void verificarDigitoTelefone(){
        if (noveDigito) {
            maskTelefone = "(99)99999-9999";
        }else{
            maskTelefone = "(99)9999-9999";
        }
    }
    
    
    public void salvarBancoDefault(Cliente cliente){
        banco = new Banco();
        banco.setAgencia("0");
        banco.setConta("0");
        banco.setCliente(cliente);
        banco.setNome("Nenhum");
        banco.setNumeroBanco("0");
        bancoDao.update(banco);
    }

}
