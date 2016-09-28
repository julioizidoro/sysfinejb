package br.com.financemate.manageBean;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasPagarDao;
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

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import javax.ejb.EJB;

@Named
@ViewScoped
public class EditarBancoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Contaspagar conta;
    private Banco banco;
    private List<Banco> listaBanco;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Boolean habilitarUnidade = false;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB 
    private ContasPagarDao contasPagarDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conta = (Contaspagar) session.getAttribute("conta");
        gerarListaCliente();
        if (conta == null) {
            if (usuarioLogadoMB.getCliente() != null) {
                cliente = usuarioLogadoMB.getCliente();
                gerarListaBanco();
            } else {
                cliente = new Cliente();
            }
        } else {
            cliente = conta.getCliente();
            gerarListaBanco();
            banco = conta.getBanco();
        }
        desabilitarUnidade();
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Contaspagar getConta() {
        return conta;
    }

    public void setConta(Contaspagar conta) {
        this.conta = conta;
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

    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }
    
    

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + "" + "%' order by c.razaoSocial");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoDao.list(sql);
            if (listaBanco == null) {
                listaBanco = new ArrayList<Banco>();
            }
        } else {
            listaBanco = new ArrayList<Banco>();
        }
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String salvar() {
        conta.setBanco(banco);
        conta = contasPagarDao.update(conta);
        return "liberarContasPagar";
    }

    public String cancelar() {
        return "liberarContasPagar";
    }

}
