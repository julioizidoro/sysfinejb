package br.com.financemate.manageBean;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadBancoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Banco banco;
    private int idCliente;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("banco");
        gerarListaCliente();
        if (banco == null) {
            banco = new Banco();
        } else {
            cliente = banco.getCliente();
        }
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }
    
    

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void salvar() {
        banco.setCliente(cliente);
        banco = bancoDao.update(banco);
        RequestContext.getCurrentInstance().closeDialog(banco);
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + "" + "%' order by c.razaoSocial");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

}
