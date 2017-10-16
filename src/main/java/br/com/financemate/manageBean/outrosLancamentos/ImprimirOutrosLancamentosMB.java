package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.SaldoDao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Planocontas;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class ImprimirOutrosLancamentosMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<Cliente> listaCliente;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private Boolean habilitarUnidade;
    private Banco banco;
    private List<Banco> listaBanco;
    private Date dataIncial;
    private Date dataFinal;
    private Planocontas planocontas;
    private List<Planocontas> listaPlanoContas;
    private String nomeComboPlano = "Selecione";
    private Float saldoInicial;
    private String unidade;
    private String nomeBanco;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private PlanoContasDao planoContasDao;
    private float totalEntrada = 0.0f;
    private float totalSaida = 0.0f;
    @EJB
    private SaldoDao saldoDao;
    private String tipoConciliacao;

    @PostConstruct
    public void init() {
        gerarListaCliente();
        if (usuarioLogadoMB.getCliente() != null) {
            cliente = usuarioLogadoMB.getCliente();
            gerarListaBanco();
        }
        gerarListaPlanoContas();
        desabilitarUnidade();
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public Float getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Float saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getNomeComboPlano() {
        return nomeComboPlano;
    }

    public void setNomeComboPlano(String nomeComboPlano) {
        this.nomeComboPlano = nomeComboPlano;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public List<Planocontas> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public Date getDataIncial() {
        return dataIncial;
    }

    public void setDataIncial(Date dataIncial) {
        this.dataIncial = dataIncial;
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

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
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

    public OutrosLancamentosDao getOutrosLancamentosDao() {
        return outrosLancamentosDao;
    }

    public void setOutrosLancamentosDao(OutrosLancamentosDao outrosLancamentosDao) {
        this.outrosLancamentosDao = outrosLancamentosDao;
    }

    public PlanoContasDao getPlanoContasDao() {
        return planoContasDao;
    }

    public void setPlanoContasDao(PlanoContasDao planoContasDao) {
        this.planoContasDao = planoContasDao;
    }

    public float getTotalEntrada() {
        return totalEntrada;
    }

    public void setTotalEntrada(float totalEntrada) {
        this.totalEntrada = totalEntrada;
    }

    public float getTotalSaida() {
        return totalSaida;
    }

    public void setTotalSaida(float totalSaida) {
        this.totalSaida = totalSaida;
    }

    public String getTipoConciliacao() {
        return tipoConciliacao;
    }

    public void setTipoConciliacao(String tipoConciliacao) {
        this.tipoConciliacao = tipoConciliacao;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }

    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
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

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        String nomeRelatorio = "";
        Map<String, Object> parameters = new HashMap<String, Object>();
        caminhoRelatorio = "reports/Relatorios/outroslancamentos/reportConciliacao.jasper";
        nomeRelatorio = "Conciliacao";
        List<ConciliacaoBean> lista = gerarListaConciliacao();
        File f = new File(servletContext.getRealPath("resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        parameters.put("dataInicial", dataIncial);
        parameters.put("dataFinal", dataFinal);
        parameters.put("nomeFantasia", unidade);
        parameters.put("banco", nomeBanco);
        parameters.put("saldoInicial", saldoInicial);
        if (planocontas != null) {
            parameters.put("planocontas", planocontas.getDescricao());
        } else {
            String nome = "Todas";
            parameters.put("planocontas", nome);
        }
        JRDataSource jrds = new JRBeanCollectionDataSource(lista);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeRelatorio);
        } catch (JRException | IOException ex) {
            Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private List<ConciliacaoBean> gerarListaConciliacao() {
        unidade = "TODAS";
        nomeBanco = "TODOS";
        String sql = "select o from Movimentobanco o where o.descricao like '%%' ";
        if (dataIncial != null && dataFinal != null) {
            sql = sql + " and o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataIncial) + "' and"
                    + " o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";

        }
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            cliente = clienteDao.find(usuarioLogadoMB.getUsuario().getCliente());
        }
        if (cliente != null) {
            sql = sql + " and o.cliente.idcliente=" + cliente.getIdcliente();
            unidade = cliente.getNomeFantasia();
        }
        if (banco != null && banco.getIdbanco() != null) {
            sql = sql + " and o.banco.idbanco=" + banco.getIdbanco();
            nomeBanco = banco.getNome();
        }
        if (planocontas != null) {
            sql = sql + " and o.planocontas.idplanoContas=" + planocontas.getIdplanoContas();
        }
        if (tipoConciliacao.equalsIgnoreCase("Conciliados")) {
            sql = sql + " and o.conciliacao='sim' ";
        }else if(tipoConciliacao.equalsIgnoreCase("Pendentes")){
            sql = sql + " and o.conciliacao='não' or o.conciliacao is null ";
        }
        
        sql = sql + " order by o.dataCompensacao";
        List<Movimentobanco> lista;
        lista = outrosLancamentosDao.list(sql);
        List<ConciliacaoBean> listaConciliacao = new ArrayList<>();
        if (lista != null) {
            List<Movimentobanco> listaOutros = outrosLancamentosDao.list("select o from Movimentobanco o"
                    + " where o.dataVencimento<'" + Formatacao.ConvercaoDataSql(dataIncial) + "'");
            for (int i = 0; i < listaOutros.size(); i++) {
                if (listaOutros.get(i).getValorEntrada() > 0f) {
                    totalEntrada = totalEntrada + listaOutros.get(i).getValorEntrada();
                } else if (listaOutros.get(i).getValorSaida() > 0f) {
                    totalSaida = totalSaida + listaOutros.get(i).getValorSaida();
                }
            }
            Float saldoAtual;
            if (banco.getIdbanco() == null) {
                saldoAtual = gerarSqlSaldoTodasContas();
            } else {
                saldoAtual = geralSqlSaldoInicial();
            }
            for (int i = 0; i < lista.size(); i++) {
                ConciliacaoBean conciliacao = new ConciliacaoBean();
                conciliacao.setDataCompensacao(lista.get(i).getDataCompensacao());
                conciliacao.setDescricao(lista.get(i).getDescricao());
                conciliacao.setPlanoContas(lista.get(i).getPlanocontas().getDescricao());
                saldoAtual = saldoAtual + lista.get(i).getValorEntrada() - lista.get(i).getValorSaida();
                conciliacao.setSaldo(saldoAtual);
                conciliacao.setValorEntrada(lista.get(i).getValorEntrada());
                conciliacao.setValorSaida(lista.get(i).getValorSaida());
                if (lista.get(i).getConciliacao() == null) {
                    conciliacao.setConciliacao("");
                }else{
                    conciliacao.setConciliacao(lista.get(i).getConciliacao());
                }
                listaConciliacao.add(conciliacao);
            }
        }
        return listaConciliacao;
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public void gerarListaPlanoContas() {
        try {
            listaPlanoContas = planoContasDao.list("select p from Planocontas p");
            if (listaPlanoContas == null) {
                listaPlanoContas = new ArrayList<>();
            }
        } catch (Exception ex) {
            Logger.getLogger(ImprimirOutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
        }

    }

    public String nomeComboPlanoConta() {
        if (listaPlanoContas == null) {
            return nomeComboPlano;
        } else {
            return nomeComboPlano = "Todas";
        }
    }

    public float geralSqlSaldo() {
        try {
            return outrosLancamentosDao.gerarSaldoInicial(dataIncial);
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirOutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0f;
    }

    public float geralSqlSaldoInicial() {
        List<Movimentobanco> listaOutrosLancamentosAnteriores;
        float entrada = 0.0f;
        float saida = 0.0f;
        String sql;
        saldoInicial = 0f;
        sql = "select max(s.valor) from Saldo s";
        if (banco.getIdbanco() != null) {
            sql = sql + " where s.banco.idbanco=" + banco.getIdbanco();
        }
        if (banco.getIdbanco() == null) {
            List<Banco> listaBanco;
            listaBanco = bancoDao.list("select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente());
            if (listaBanco == null) {
                listaBanco = new ArrayList<>();
            }
            for (int i = 0; i < listaBanco.size(); i++) {
                String sql3 = "select max(s.valor) from Saldo s where s.banco.idbanco=" + listaBanco.get(i).getIdbanco();
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
        String sql2 = "select o from Movimentobanco o where o.dataCompensacao<'" + Formatacao.ConvercaoDataSql(dataIncial) + "'";
        if (banco.getIdbanco() != null) {
            sql2 = sql2 + " and o.banco.idbanco=" + banco.getIdbanco();
        }
        listaOutrosLancamentosAnteriores = outrosLancamentosDao.list(sql2);
        if (listaOutrosLancamentosAnteriores == null) {
            listaOutrosLancamentosAnteriores = new ArrayList<>();
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

    public Float gerarSqlSaldoTodasContas() {
        List<Movimentobanco> listaOutrosLancamentosAnteriores;
        float entrada = 0.0f;
        float saida = 0.0f;
        String sql;
        saldoInicial = 0f;
        if (cliente != null) {
            gerarListaBanco();
            for (int i = 0; i < listaBanco.size(); i++) {
                sql = "select max(s.valor) from Saldo s where s.banco.idbanco=" + listaBanco.get(i).getIdbanco();
                try {
                    saldoInicial = saldoInicial + saldoDao.consultar(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        String sql2 = "select o from Movimentobanco o where o.dataCompensacao<'" + Formatacao.ConvercaoDataSql(dataIncial) + "'";
        if (banco.getIdbanco() != null) {
            sql2 = sql2 + " and o.banco.idbanco=" + banco.getIdbanco();
        }
        listaOutrosLancamentosAnteriores = outrosLancamentosDao.list(sql2);
        if (listaOutrosLancamentosAnteriores == null) {
            listaOutrosLancamentosAnteriores = new ArrayList<>();
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

}
