package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasReceberDao;
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

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import javax.ejb.EJB;

@Named
@ViewScoped
public class EditarBancoRecebimentoLoteMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Contasreceber conta;
    private Banco banco;
    private List<Banco> listaBanco;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Boolean habilitarUnidade = false;
    private String totalReceberLote;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conta = (Contasreceber) session.getAttribute("conta");
        totalReceberLote = (String) session.getAttribute("totalReceberLote");
        session.removeAttribute("totalReceberLote");
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

    public String getTotalReceberLote() {
        return totalReceberLote;
    }

    public void setTotalReceberLote(String totalReceberLote) {
        this.totalReceberLote = totalReceberLote;
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

    public Contasreceber getConta() {
        return conta;
    }

    public void setConta(Contasreceber conta) {
        this.conta = conta;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
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
                listaBanco = new ArrayList<>();
            }
        } else {
            listaBanco = new ArrayList<>();
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
        conta = contasReceberDao.update(conta);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("totalReceberLote", totalReceberLote);
        return "recebimentoLote";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("totalReceberLote", totalReceberLote);
        return "recebimentoLote";
    }
}
