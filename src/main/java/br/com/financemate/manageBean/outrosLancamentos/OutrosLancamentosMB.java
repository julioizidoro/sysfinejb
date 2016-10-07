package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.SaldoDao;
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
import org.primefaces.event.SelectEvent;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.model.Tipoplanocontas;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class OutrosLancamentosMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Outroslancamentos> listaOutrosLancamentos;
    private Banco banco;
    private Cliente cliente;
    private List<Cliente> listaClientes;
    private String sql;
    private List<Banco> listaBancos;
    private boolean verCliente = false;
    private Outroslancamentos outrosLancamentos;
    private String valorEntrada;
    private String valorSaida;
    private String valorTotal;
    private Boolean habilitarUnidade;
    private Date dataInicial;
    private Date dataFinal;
    private String todosBanco;
    private String nomeComboBanco = "Selecione";
    private List<Outroslancamentos> listaOutrosLancamentosAnteriores;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private SaldoDao saldoDao;

    @PostConstruct
    public void init() {
        listaOutrosLancamentos = new ArrayList<Outroslancamentos>();
        gerarListaCliente();
        getUsuarioLogadoMB();
        verificarCliente();
        if (usuarioLogadoMB.getCliente() != null) {
            cliente = usuarioLogadoMB.getCliente();
            gerarListaBanco();
        }
        desabilitarUnidade();
    }

    public List<Outroslancamentos> getListaOutrosLancamentosAnteriores() {
        return listaOutrosLancamentosAnteriores;
    }

    public void setListaOutrosLancamentosAnteriores(List<Outroslancamentos> listaOutrosLancamentosAnteriores) {
        this.listaOutrosLancamentosAnteriores = listaOutrosLancamentosAnteriores;
    }

    public String getNomeComboBanco() {
        return nomeComboBanco;
    }

    public void setNomeComboBanco(String nomeComboBanco) {
        this.nomeComboBanco = nomeComboBanco;
    }

    public String getTodosBanco() {
        return todosBanco;
    }

    public void setTodosBanco(String todosBanco) {
        this.todosBanco = todosBanco;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public String getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(String valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public String getValorSaida() {
        return valorSaida;
    }

    public void setValorSaida(String valorSaida) {
        this.valorSaida = valorSaida;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Outroslancamentos getOutrosLancamentos() {
        return outrosLancamentos;
    }

    public void setOutrosLancamentos(Outroslancamentos outrosLancamentos) {
        this.outrosLancamentos = outrosLancamentos;
    }

    public List<Outroslancamentos> getListaOutrosLancamentos() {
        return listaOutrosLancamentos;
    }

    public void setListaOutrosLancamentos(List<Outroslancamentos> listaOutrosLancamentos) {
        this.listaOutrosLancamentos = listaOutrosLancamentos;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
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

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Banco> getListaBancos() {
        return listaBancos;
    }

    public void setListaBancos(List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    public boolean isVerCliente() {
        return verCliente;
    }

    public void setVerCliente(boolean verCliente) {
        this.verCliente = verCliente;
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void gerarPesquisa() {
        if ((cliente != null) && (dataInicial != null) && (dataFinal != null)) {
            if (banco.getIdbanco() != null) {
                sql = "Select o from Outroslancamentos o where o.banco.idbanco=" + banco.getIdbanco()
                        + "  and o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
                        + "'  and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' and o.cliente.idcliente=" + cliente.getIdcliente();
                sql = sql + " order by o.dataCompensacao";
            } else {
                sql = "Select o from Outroslancamentos o where"
                        + " o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
                        + "'  and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' and o.cliente.idcliente=" + cliente.getIdcliente();
                sql = sql + " order by o.dataCompensacao";
            }
            listaOutrosLancamentos = outrosLancamentosDao.list(sql);
            if (listaOutrosLancamentos == null) {
                listaOutrosLancamentos = new ArrayList<Outroslancamentos>();
            }
            calcularTotal();
        } else {
            mostrarMensagem(null, "Dados invalidos", "Aviso");
        }
    }

    public void gerarListaCliente() {
        listaClientes = clienteDao.list("Select c From Cliente c");
        if (listaClientes == null || listaClientes.isEmpty()) {
            listaClientes = new ArrayList<Cliente>();
        }
    }

    public void verificarCliente() {
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            cliente = clienteDao.find(usuarioLogadoMB.getUsuario().getCliente());
            verCliente = true;
            if (cliente == null) {
                verCliente = false;
                cliente = new Cliente();
            }
        }
    }

    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBancos = bancoDao.list(sql);
            if (listaBancos == null) {
                listaBancos = new ArrayList<Banco>();
            }
        } else {
            listaBancos = new ArrayList<Banco>();
        }
    }

    public String coresValoresEntrada(Outroslancamentos outroslancamentos) {
        if (outroslancamentos.getValorEntrada() > outroslancamentos.getValorSaida()) {
            return "green";
        } else {
            return "gray";
        }
    }

    public String coresValoresSaida(Outroslancamentos outroslancamentos) {
        if (outroslancamentos.getValorSaida() > outroslancamentos.getValorEntrada()) {
            return "red";
        } else {
            return "gray";
        }
    }

    public String excluir() {
        outrosLancamentosDao.remove(outrosLancamentos.getIdoutroslancamentos());
        gerarPesquisa();
        mensagem mensagem = new mensagem();
        mensagem.excluiMessagem();
        return "";
    }

    public String novoLancamento() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadOutrosLancamentos", options, null);
        return "";
    }

    public String novaTransferencia() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("cadTransferencia", options, null);
        return "";
    }

    public void retornoDialogTransferencia(SelectEvent event) {
        String msg = (String) event.getObject();
        if (msg.length() > 2) {
            mensagem mensagem = new mensagem();
            mensagem.notificacao(msg);
            if (listaOutrosLancamentos.isEmpty() || listaOutrosLancamentos != null) {
                gerarPesquisa();
            }
        }
    }

    public String consultaSaldoInicial() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("consSaldoIncial", options, null);
        return "";
    }

    public void salvarTransferencia() {
        Planocontas planocontas = new Planocontas();
        Planocontatipo planocontatipo;
        Tipoplanocontas tipoplanocontas = new Tipoplanocontas();
        planocontas = planoContasDao.find(23);
        List<Cliente> listaCliente = clienteDao.list("Select c From Cliente c");
        for (int i = 0; i < listaCliente.size(); i++) {
            tipoplanocontas = listaCliente.get(i).getTipoplanocontas();
            planocontatipo = new Planocontatipo();
            planocontatipo.setTipoplanocontas(tipoplanocontas);
            planocontatipo.setPlanocontas(planocontas);
            planoContaTipoDao.update(planocontatipo);
        }
    }

    public String editar(Outroslancamentos outroslancamentos) {
        if (outroslancamentos != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("outroslancamentos", outroslancamentos);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadOutrosLancamentos", options, null);
        }
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Outroslancamentos outroslancamentos = (Outroslancamentos) event.getObject();
        if (outroslancamentos.getIdoutroslancamentos() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        } else {
            mensagem mensagem = new mensagem();
            mensagem.cancelado();
        }
        gerarPesquisa();
    }

    public void calcularTotal() {
        float entrada = 0.0f;
        float saida = 0.0f;
        float saldo = 0.0f;
        saldo = geralSqlSaldoInicial();
        for (int i = 0; i < listaOutrosLancamentos.size(); i++) {
            if (listaOutrosLancamentos.get(i).getValorEntrada() > 0) {
                entrada = entrada + listaOutrosLancamentos.get(i).getValorEntrada();
                saldo = saldo + (listaOutrosLancamentos.get(i).getValorEntrada() - listaOutrosLancamentos.get(i).getValorSaida());
                listaOutrosLancamentos.get(i).setSaldo(saldo);
            } else if (listaOutrosLancamentos.get(i).getValorSaida() > 0) {
                saida = saida + listaOutrosLancamentos.get(i).getValorSaida();
                saldo = saldo + (listaOutrosLancamentos.get(i).getValorEntrada() - listaOutrosLancamentos.get(i).getValorSaida());
                listaOutrosLancamentos.get(i).setSaldo(saldo);
            }
        }

        setValorEntrada(Formatacao.foramtarFloatString(entrada));
        setValorSaida(Formatacao.foramtarFloatString(saida));
        setValorTotal(Formatacao.foramtarFloatString(saldo));
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String nomeComboConta() {
        if (listaBancos == null) {
            return nomeComboBanco;
        } else {
            return nomeComboBanco = "Todas";
        }
    }

    public void excluirConfirmacao() {
        mensagem mensagem = new mensagem();
        mensagem.excluirConfirmacao();
    }

    public String impressaoOutrosLancamentos() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("imprimirOutrosLancamentos", options, null);
        return "";
    }

    public float geralSqlSaldoInicial() {
        float entrada = 0.0f;
        float saida = 0.0f;
        String sql;
        Float saldoInicial = 0f;
        sql = "Select max(s.valor) from Saldo s";
        if (banco.getIdbanco() != null) {
            sql = sql + " where s.banco.idbanco=" + banco.getIdbanco();
        }
        if (banco.getIdbanco() == null) {
            List<Banco> listaBanco;
            listaBanco = bancoDao.list("Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente());
            if (listaBanco == null) {
                listaBanco = new ArrayList<Banco>();
            }
            for (int i = 0; i < listaBanco.size(); i++) {
                String sql3 = "Select max(s.valor) from Saldo s where s.banco.idbanco=" + listaBanco.get(i).getIdbanco();
                try {
                    saldoDao.consultar(sql3);
                } catch (SQLException ex) {
                    Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                saldoInicial = saldoDao.consultar(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (saldoInicial == null) {
            saldoInicial = 0.0f;
        }
        String sql2 = "Select o from Outroslancamentos o where o.dataCompensacao<'" + Formatacao.ConvercaoDataSql(dataInicial) + "'";
        if (banco.getIdbanco() != null) {
            sql2 = sql2 + " and o.banco.idbanco=" + banco.getIdbanco();
        }
        listaOutrosLancamentosAnteriores = outrosLancamentosDao.list(sql2);
        if (listaOutrosLancamentosAnteriores == null) {
            listaOutrosLancamentosAnteriores = new ArrayList<Outroslancamentos>();
        }
        for (int i = 0; i < listaOutrosLancamentosAnteriores.size(); i++) {
            if (listaOutrosLancamentosAnteriores.get(i).getValorEntrada() > 0) {
                entrada = entrada + listaOutrosLancamentosAnteriores.get(i).getValorEntrada();
                saldoInicial = saldoInicial + (listaOutrosLancamentosAnteriores.get(i).getValorEntrada() - listaOutrosLancamentosAnteriores.get(i).getValorSaida());

            } else if (listaOutrosLancamentosAnteriores.get(i).getValorSaida() > 0) {
                saida = saida + listaOutrosLancamentosAnteriores.get(i).getValorSaida();
                saldoInicial = saldoInicial + (listaOutrosLancamentosAnteriores.get(i).getValorEntrada() - listaOutrosLancamentosAnteriores.get(i).getValorSaida());

            }
        }
        return saldoInicial;

    }

    public String conciliacaoBancaria() {
        return "conciliacaoBancaria";
    }

}
