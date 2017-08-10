/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.SaldoDao;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.util.Formatacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Anderson
 */
@Named
@ViewScoped
public class OutrosConciliacaoMB implements Serializable {

    private List<Movimentobanco> listaOutrosLancamentos;
    private Cliente cliente;
    private List<Cliente> listaClientes;
    private Movimentobanco outrosLancamentos;
    private Date dataInicial;
    private Date dataFinal;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    @EJB
    private PlanoContasDao planoContasDao;
    private Banco banco;
    private List<Banco> listaBancos;
    @EJB
    private BancoDao bancoDao;
    private String sql;
    private List<Movimentobanco> listaLancamentosSelecionados;
    private List<TransacaoBean> listaTransacao;
    private List<Movimentobanco> listaLacamentos;
    private ConciliarBean conciliacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaTransacao = (List<TransacaoBean>) session.getAttribute("listaTransacao");
        listaLacamentos = (List<Movimentobanco>) session.getAttribute("listaLancamentos");
        banco = (Banco) session.getAttribute("banco");
        conciliacao = (ConciliarBean) session.getAttribute("conciliacao");
        session.removeAttribute("listaTransacao");
        session.removeAttribute("listaLancamentos");
        session.removeAttribute("banco");
        if (banco != null) {
            cliente = banco.getCliente();
        }
        gerarListaCliente();
    }

    public List<Movimentobanco> getListaOutrosLancamentos() {
        return listaOutrosLancamentos;
    }

    public void setListaOutrosLancamentos(List<Movimentobanco> listaOutrosLancamentos) {
        this.listaOutrosLancamentos = listaOutrosLancamentos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Movimentobanco getOutrosLancamentos() {
        return outrosLancamentos;
    }

    public void setOutrosLancamentos(Movimentobanco outrosLancamentos) {
        this.outrosLancamentos = outrosLancamentos;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBancos() {
        return listaBancos;
    }

    public void setListaBancos(List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Movimentobanco> getListaLancamentosSelecionados() {
        return listaLancamentosSelecionados;
    }

    public void setListaLancamentosSelecionados(List<Movimentobanco> listaLancamentosSelecionados) {
        this.listaLancamentosSelecionados = listaLancamentosSelecionados;
    }

    public void setListaTransacao(List<TransacaoBean> listaTransacao) {
        this.listaTransacao = listaTransacao;
    }

    public void setListaLacamentos(List<Movimentobanco> listaLacamentos) {
        this.listaLacamentos = listaLacamentos;
    }

    public void gerarPesquisa() {
        if ((cliente != null) && (dataInicial != null) && (dataFinal != null)) {
            if (banco.getIdbanco() != null) {
                sql = "select o from Movimentobanco o where o.banco.idbanco=" + banco.getIdbanco()
                        + "  and o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
                        + "'  and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' and o.cliente.idcliente=" + cliente.getIdcliente();
                sql = sql + " order by o.dataCompensacao";
            } else {
                sql = "select o from Movimentobanco o where"
                        + " o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
                        + "'  and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' and o.cliente.idcliente=" + cliente.getIdcliente();
                sql = sql + " order by o.dataCompensacao";
            }
            listaOutrosLancamentos = outrosLancamentosDao.list(sql);
            if (listaOutrosLancamentos == null) {
                listaOutrosLancamentos = new ArrayList<>();
            }
        } else {
            mensagem mensagem = new mensagem();
            mensagem.faltaInformacao("Dados invalidos");
        }
    }

    public void gerarListaCliente() {
        listaClientes = clienteDao.list("select c from Cliente c");
        if (listaClientes == null || listaClientes.isEmpty()) {
            listaClientes = new ArrayList<>();
        }
    }
    
    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBancos = bancoDao.list(sql);
            if (listaBancos == null) {
                listaBancos = new ArrayList<>();
            }
        } else {
            listaBancos = new ArrayList<>();
        }
    }
    
    public void salvar() {
        Movimentobanco outros = new Movimentobanco();
        listaLancamentosSelecionados = new ArrayList<>();
        for (int i = 0; i < listaOutrosLancamentos.size(); i++) {
            if (listaOutrosLancamentos.get(i).isSelecionado()) {
                listaLancamentosSelecionados.add(listaOutrosLancamentos.get(i));
            }
        }
        outros.setValorEntrada(conciliacao.getTransacao().getValorEntrada());
        outros.setValorSaida(conciliacao.getTransacao().getValorSaida());
        outros.setBanco(banco);
        outros.setCliente(banco.getCliente());
        outros.setDataRegistro(new Date());
        outros.setDataCompensacao(conciliacao.getTransacao().getData());
        outros.setDescricao(conciliacao.getTransacao().getDescricao());
        outros.setPlanocontas(listaLancamentosSelecionados.get(0).getPlanocontas());
        outros.setUsuario(usuarioLogadoMB.getUsuario());
        outros.setDataVencimento(listaLancamentosSelecionados.get(0).getDataVencimento());
        outros.setTipoDocumento(listaLancamentosSelecionados.get(0).getTipoDocumento());
        outros = outrosLancamentosDao.update(outros);
        if (listaLancamentosSelecionados.size() > 0) {
            for (int i = 0; i < listaLancamentosSelecionados.size(); i++) {
                Movimentobanco movimentobanco = listaLancamentosSelecionados.get(i);
                outrosLancamentosDao.remove(movimentobanco.getIdmovimentobanco());
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("listaLancamentos", listaLacamentos);
            session.setAttribute("listaTransacao", listaTransacao);
            session.setAttribute("banco", banco);
            RequestContext.getCurrentInstance().closeDialog(outros);
        }else{
            mensagem mensagem = new mensagem();
            mensagem.faltaInformacao("Selecione algum lançamento");
        }
    }
    
    public void cancelar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("listaLancamentos", listaLacamentos);
        session.setAttribute("listaTransacao", listaTransacao);
        session.setAttribute("banco", banco);
        RequestContext.getCurrentInstance().closeDialog(new Movimentobanco());
    }

}
