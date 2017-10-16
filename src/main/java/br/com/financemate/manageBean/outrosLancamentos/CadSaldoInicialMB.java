package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.SaldoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Saldo;
import java.util.Date;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadSaldoInicialMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private Saldo saldo;
    private Banco banco;
    private List<Banco> listaBanco;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private SaldoDao saldoDao;
    private List<Saldo> listaSaldo;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        banco = (Banco) session.getAttribute("banco");
        listaSaldo = (List<Saldo>) session.getAttribute("listaSaldo");
        if (saldo == null) {
            saldo = new Saldo();
            saldo.setDatainclusao(new Date());
        }
        if (cliente != null) {
            gerarListaBanco();
        }
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

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
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

    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoDao.list(sql);
            if (listaBanco == null) {
                listaBanco = new ArrayList<>();
            }
        } else {
            listaBanco = new ArrayList<>();
        }
    }

    public String salvar() {
        saldo.setUsuario(usuarioLogadoMB.getUsuario());
        saldo.setBanco(banco);
        saldo = saldoDao.update(saldo);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("listaSaldo", listaSaldo);
        return "consSaldoIncial";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("listaSaldo", listaSaldo);
        return "consSaldoIncial";
    }

}
