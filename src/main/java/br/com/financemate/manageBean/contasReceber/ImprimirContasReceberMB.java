package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.CobrancaParcelasDao;
import br.com.financemate.dao.ContasReceberDao;
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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Cobrancaparcelas;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ImprimirContasReceberMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Date dataInicial;
    private Date dataFinal;
    private String relatorio;
    private Boolean contasAberto;
    private Boolean contasRecebidas;
    private Boolean todas;
    private Boolean selecionado = false;
    private Boolean selecionadoTipoDocumento = false;
    private Boolean habilitarUnidade = false;
    private String nomeDosRelatorio;
    private String tipoDocumento;
    private List<RelatorioCobrancaBean> listaRelatorio;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CobrancaParcelasDao cobrancaParcelasDao;

    @PostConstruct
    public void init() {
        gerarListaCliente();
        if (usuarioLogadoMB.getCliente() != null) {
            cliente = usuarioLogadoMB.getCliente();
        }
        desabilitarUnidade();
    }

    public String getNomeDosRelatorio() {
        return nomeDosRelatorio;
    }

    public void setNomeDosRelatorio(String nomeDosRelatorio) {
        this.nomeDosRelatorio = nomeDosRelatorio;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
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

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    public Boolean getContasAberto() {
        return contasAberto;
    }

    public void setContasAberto(Boolean contasAberto) {
        this.contasAberto = contasAberto;
    }

    public Boolean getContasRecebidas() {
        return contasRecebidas;
    }

    public void setContasRecebidas(Boolean contasRecebidas) {
        this.contasRecebidas = contasRecebidas;
    }

    public Boolean getTodas() {
        return todas;
    }

    public void setTodas(Boolean todas) {
        this.todas = todas;
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

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public List<RelatorioCobrancaBean> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<RelatorioCobrancaBean> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public Boolean getSelecionadoTipoDocumento() {
        return selecionadoTipoDocumento;
    }

    public void setSelecionadoTipoDocumento(Boolean selecionadoTipoDocumento) {
        this.selecionadoTipoDocumento = selecionadoTipoDocumento;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }

    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        String nomeRelatorio = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (relatorio.equalsIgnoreCase("Contas a Receber")) {
            caminhoRelatorio = "reports/Relatorios/contasReceber/reportContasReceber.jasper";
            nomeRelatorio = "Contas a Receber";
        } else if (relatorio.equalsIgnoreCase("cobrancas")) {
            gerarRelatorioCobranca();
            RequestContext.getCurrentInstance().closeDialog(null);
            return "relatoriocobranca";
        }
        parameters.put("sql", gerarSql());
        File f = new File(servletContext.getRealPath("resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("nomeFantasia", cliente.getNomeFantasia());
        parameters.put("logo", logo);
        String periodo = null;
        periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicial)
                + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        parameters.put("periodo", periodo);
        String titulo = null;
        if (nomeDosRelatorio.equalsIgnoreCase("contasRecebidas")) {
            titulo = "RELATÓRIO DE CONTAS RECEBIDAS";
        } else if (nomeDosRelatorio.equalsIgnoreCase("contasAberto")) {
            titulo = "RELATÓRIO DE CONTAS EM ABERTO";
        } else {
            titulo = "RELATÓRIO DE CONTAS A RECEBER";
        }
        if (cliente == null) {
            parameters.put("unidade", "TODAS AS UNIDADES");
        } else {
            parameters.put("unidade", cliente.getNomeFantasia());
        }
        parameters.put("titulo", titulo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSql() {
        String sql = "";
        if (relatorio.equalsIgnoreCase("Contas a receber")) {
            sql = "SELECT distinct contasreceber.idcontasReceber, contasreceber.numeroDocumento, contasreceber.nomeCliente as nomeCliente,contasreceber.valorParcela, contasreceber.numeroParcela, contasreceber.dataVencimento, contasreceber.juros,contasreceber.desagio, contasreceber.tipodocumento, contasreceber.venda, contasreceber.dataPagamento, contasreceber.valorPago,cliente.nomeFantasia, banco.nome ";
            sql = sql + "from ";
            sql = sql + "contasreceber join cliente on contasreceber.cliente_idcliente = cliente.idcliente ";
            sql = sql + "join banco on contasreceber.banco_idbanco = banco.idbanco ";
            sql = sql + "where ";
            String ordem = "";
            if (nomeDosRelatorio.equalsIgnoreCase("contasAberto")) {
                sql = sql + " contasreceber.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
                sql = sql + " and contasreceber.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
                sql = sql + " and contasreceber.dataPagamento is null ";
                ordem = " order by contasreceber.dataVencimento";
            } 
            if (nomeDosRelatorio.equalsIgnoreCase("contasRecebidas")) {
                sql = sql + " contasreceber.datapagamento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
                sql = sql + " and contasreceber.dataPagamento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
                sql = sql + " and valorPago>0 ";
                ordem = " order by contasreceber.dataPagamento";
            }
            if (nomeDosRelatorio.equalsIgnoreCase("todascontas")) {
                sql = sql + " contasreceber.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
                sql = sql + " and contasreceber.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                ordem = " order by contasreceber.dataVencimento";
            }
            if (cliente != null) {
                sql = sql + " and cliente.idcliente=" + cliente.getIdcliente() + "";
            }
            sql = sql + ordem;
        }

        return sql;
    }

    public String fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

    public Boolean habilitarTipoRelatorio() {
        selecionado = false;
        selecionadoTipoDocumento = false;
        if (relatorio.equalsIgnoreCase("Contas a Receber")) {
            selecionado = true;
        } else if (relatorio.equalsIgnoreCase("cobrancas")) {
            selecionadoTipoDocumento = true;
        }
        return selecionado;
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String gerarRelatorioCobranca() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        carregarListaContasReceber();
        session.setAttribute("listaRelatorio", listaRelatorio);
        return "relatoriocobranca";
    }

    public void carregarListaContasReceber() {
        listaRelatorio = new ArrayList<RelatorioCobrancaBean>();
        int diaSemana = Formatacao.diaSemana(new Date());
        String data = "";
        if (diaSemana == 1) {
            data = Formatacao.SubtarirDatas(new Date(), 5, "yyyy-MM-dd");
        } else if (diaSemana == 7) {
            data = Formatacao.SubtarirDatas(new Date(), 4, "yyyy-MM-dd");
        } else {
            data = Formatacao.SubtarirDatas(new Date(), 3, "yyyy-MM-dd");
        }

        String sql = "Select c from Contasreceber c where c.dataPagamento is NULL and c.valorPago=0 and c.dataVencimento<'"
                + data + "' and c.status<>'CANCELADA' ";

        if (!tipoDocumento.equalsIgnoreCase("Todos")) {
            sql = sql + " and c.tipodocumento='" + tipoDocumento + "' ";
        }
        if (cliente != null) {
            sql = sql + " and c.cliente.idcliente=" + cliente.getIdcliente();
        }
        sql = sql + " order by c.idcontasReceber, c.dataVencimento";
        List<Contasreceber> listaContas = null;
        listaContas = contasReceberDao.list(sql);
        if (listaContas != null) {
            carregarCobranca(listaContas);
        }
    }

    public void carregarCobranca(List<Contasreceber> listaContas) {
        List<Contasreceber> listaContasCobranca = new ArrayList<Contasreceber>();
        Cobranca cobranca = null;
        float valorTotal = 0.0f;
        Contasreceber contasreceber = null;
        List<Cobrancaparcelas> listaCobrancaParcela;
        listaCobrancaParcela = cobrancaParcelasDao.list("Select c from Cobrancaparcelas c");
        for (int i = 0; i < listaContas.size(); i++) {
            for (int j = 0; j < listaCobrancaParcela.size(); j++) {
                if (listaCobrancaParcela.get(j).getContasreceber().getIdcontasReceber() == listaContas.get(i)
                        .getIdcontasReceber()) {
                    listaContasCobranca.add(listaContas.get(i));
                    valorTotal = valorTotal + listaContas.get(i).getValorParcela();
                    cobranca = listaCobrancaParcela.get(j).getCobranca();
                    contasreceber = listaContas.get(i);
                }
            }

            if (cobranca != null) {
                if ((i + 1) < listaContas.size()) {
                    incluirListaRelatorio(cobranca, listaContasCobranca, valorTotal, contasreceber);
                    cobranca = null;
                    listaContasCobranca = new ArrayList<Contasreceber>();
                    valorTotal = 0.0f;
                }
            }
        }

    }

    public void incluirListaRelatorio(Cobranca cobranca, List<Contasreceber> listaContas, float valorTotal, Contasreceber contasreceber) {
        RelatorioCobrancaBean relatorio = new RelatorioCobrancaBean();
        relatorio.setCobranca(cobranca);
        relatorio.setListaContas(listaContas);
        relatorio.setValorTotal(valorTotal);
        relatorio.setContasreceber(contasreceber);
        listaRelatorio.add(relatorio);
    }

}
