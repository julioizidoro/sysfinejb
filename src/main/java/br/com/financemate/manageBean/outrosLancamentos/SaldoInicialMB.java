package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.SaldoDao;
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

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Saldo;
import javax.ejb.EJB;

@Named
@ViewScoped
public class SaldoInicialMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Saldo saldo;
    private List<Saldo> listaSaldo;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Banco banco;
    private List<Banco> listaBanco;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private SaldoDao saldoDao;

    @PostConstruct
    public void init() {
        listaSaldo = new ArrayList<Saldo>();
        gerarListaCliente();
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public List<Saldo> getListaSaldo() {
        return listaSaldo;
    }

    public void setListaSaldo(List<Saldo> listaSaldo) {
        this.listaSaldo = listaSaldo;
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

    public SaldoDao getSaldoDao() {
        return saldoDao;
    }

    public void setSaldoDao(SaldoDao saldoDao) {
        this.saldoDao = saldoDao;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + "" + "%' order by c.razaoSocial");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
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

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void gerarListaSaldoInicial() {
        listaSaldo = saldoDao.list("Select s from Saldo s where s.banco.idbanco=" + banco.getIdbanco());
        if (listaSaldo == null) {
            listaSaldo = new ArrayList<Saldo>();
        }
    }

    public String cadSaldoInicial() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cliente", cliente);
        return "cadSaldoInicial";
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

}
