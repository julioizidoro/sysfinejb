package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.TipoPlanoContasDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoplanocontas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ClienteMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private String nomeCliente;
    private List<Cliente> listaClientes;
    private String pagina;
    private String idTipoPlanoConta = "0";
    private List<Tipoplanocontas> listarTipoPlanoContas;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private TipoPlanoContasDao tipoPlanoContasDao;

    @PostConstruct
    public void init() {
        cliente = new Cliente();
        getListaClientes();
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

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<Cliente> getListaClientes() {
        if (listaClientes == null) {
            gerarListaClientes();
        }
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getIdTipoPlanoConta() {
        return idTipoPlanoConta;
    }

    public void setIdTipoPlanoConta(String idTipoPlanoConta) {
        this.idTipoPlanoConta = idTipoPlanoConta;
    }

    public List<Tipoplanocontas> getListarTipoPlanoContas() {
        return listarTipoPlanoContas;
    }

    public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
        this.listarTipoPlanoContas = listarTipoPlanoContas;
    }

    public void gerarListaClientes() {
        if (nomeCliente == null) {
            nomeCliente = "";
        }
        listaClientes = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + nomeCliente + "%' order by c.razaoSocial");
        if (listaClientes == null) {
            listaClientes = new ArrayList<Cliente>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String editar(Cliente cliente) {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getAcliente()) {
            if (cliente != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("cliente", cliente);
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("contentWidth", 700);
                options.put("closable", false);
                RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
            }
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";
    }

    public String novo() {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getIcliente()) {
            listarTipoPlanoContas();
            cliente = new Cliente();
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 700);
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
            return "";

        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }

    public void retornoDialogNovo(SelectEvent event) {
        Cliente cliente = (Cliente) event.getObject();
        if (cliente.getIdcliente() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
            listaClientes.add(cliente);
        }
    }

    public void retornoDialogEdicao(SelectEvent event) {
        Cliente cliente = (Cliente) event.getObject();
        if (cliente.getIdcliente() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
            gerarListaClientes();
        }
    }

    public String consultarTipoPlanoContas() throws SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        int idCliente = Integer.parseInt(params.get("idCliente"));
        if (idCliente > 0) {
            cliente = clienteDao.find(idCliente);
            if (cliente != null) {
                return "cadCliente";
            }
        }
        return null;
    }

    public void listarTipoPlanoContas() {
        listarTipoPlanoContas = tipoPlanoContasDao.list("Select t From Tipoplanocontas t order by t.descricao");
        if (listarTipoPlanoContas == null) {
            listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
        }
    }

}
