package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContasDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.model.Planocontas;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadTransferenciaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Banco bancoCredito;
    private Banco bancoDebito;
    private List<Banco> listaBancoCredito;
    private List<Banco> listaBancoDebito;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Outroslancamentos outroslancamentos;
    private Float valor = 0.0f;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private PlanoContasDao planoContasDao;

    @PostConstruct
    public void init() {
        if (outroslancamentos == null) {
            bancoDebito = new Banco();
            bancoCredito = new Banco();
            outroslancamentos = new Outroslancamentos();
            outroslancamentos.setDescricao("Transfer�ncia");
            outroslancamentos.setDataRegistro(new Date());
        }
        gerarListaCliente();
    }

    public Banco getBancoCredito() {
        return bancoCredito;
    }

    public void setBancoCredito(Banco bancoCredito) {
        this.bancoCredito = bancoCredito;
    }

    public Banco getBancoDebito() {
        return bancoDebito;
    }

    public void setBancoDebito(Banco bancoDebito) {
        this.bancoDebito = bancoDebito;
    }

    public List<Banco> getListaBancoCredito() {
        return listaBancoCredito;
    }

    public void setListaBancoCredito(List<Banco> listaBancoCredito) {
        this.listaBancoCredito = listaBancoCredito;
    }

    public List<Banco> getListaBancoDebito() {
        return listaBancoDebito;
    }

    public void setListaBancoDebito(List<Banco> listaBancoDebito) {
        this.listaBancoDebito = listaBancoDebito;
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

    public Outroslancamentos getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Outroslancamentos outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public void gerarListaBanco() {
        String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente();
        listaBancoDebito = bancoDao.list(sql);
        listaBancoCredito = bancoDao.list(sql);
        if (listaBancoCredito == null || listaBancoCredito.isEmpty()) {
            listaBancoCredito = new ArrayList<Banco>();
        }
        if (listaBancoDebito == null || listaBancoDebito.isEmpty()) {
            listaBancoDebito = new ArrayList<Banco>();
        }
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog("");
    }

    public void salvar() {
        Planocontas planocontas = new Planocontas();
        Outroslancamentos debito = new Outroslancamentos();
        debito = pegarDebitar(debito);
        planocontas = planoContasDao.find(23);
        debito.setPlanocontas(planocontas);
        debito = pegarDebitar(debito);
        outrosLancamentosDao.update(debito);
        Outroslancamentos credito = new Outroslancamentos();
        credito = pegarCredito(credito);
        credito.setPlanocontas(planocontas);
        outrosLancamentosDao.update(credito);
        RequestContext.getCurrentInstance().closeDialog("Transfer�ncia feita com sucesso");
    }

    public Outroslancamentos pegarDebitar(Outroslancamentos debito) {
        debito.setBanco(bancoDebito);
        debito.setDataRegistro(outroslancamentos.getDataRegistro());
        debito.setDataCompensacao(outroslancamentos.getDataRegistro());
        debito.setDataVencimento(outroslancamentos.getDataRegistro());
        debito.setCliente(cliente);
        debito.setCompentencia(Formatacao.gerarCopetencia(outroslancamentos.getDataRegistro()));
        debito.setDescricao(outroslancamentos.getDescricao());
        debito.setTipoDocumento("transferencia");
        debito.setValorEntrada(0.0f);
        debito.setValorSaida(valor);
        debito.setUsuario(usuarioLogadoMB.getUsuario());
        debito.setIdcontaspagar(0);
        debito.setIdcontasreceber(0);
        return debito;
    }

    public Outroslancamentos pegarCredito(Outroslancamentos credito) {
        credito.setBanco(bancoCredito);
        credito.setDataRegistro(outroslancamentos.getDataRegistro());
        credito.setDataCompensacao(outroslancamentos.getDataRegistro());
        credito.setDataVencimento(outroslancamentos.getDataRegistro());
        credito.setCliente(cliente);
        credito.setCompentencia(Formatacao.gerarCopetencia(outroslancamentos.getDataRegistro()));
        credito.setDescricao(outroslancamentos.getDescricao());
        credito.setTipoDocumento("transferencia");
        credito.setValorEntrada(valor);
        credito.setValorSaida(0.0f);
        credito.setUsuario(usuarioLogadoMB.getUsuario());
        credito.setIdcontaspagar(0);
        credito.setIdcontasreceber(0);
        return credito;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null || listaCliente.isEmpty()) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

}
