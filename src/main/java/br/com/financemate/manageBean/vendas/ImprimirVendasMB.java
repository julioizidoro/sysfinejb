package br.com.financemate.manageBean.vendas;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.VendasDao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import javax.ejb.EJB;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ImprimirVendasMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Date dataInicial;
    private Date dataFinal;
    private Boolean habilitarUnidade = false;
    private String relatorio;
    private Date segundaData;
    private Date terceiraData;
    private float faturamento = 0f;
    private float faturamento2 = 0f;
    private float faturamento3 = 0f;
    private float faturamento4 = 0f;
    private float totalRecebidoMes = 0f;
    private float totalRecebidoMes2 = 0f;
    private float totalRecebidoMes3 = 0f;
    private float totalRecebidoMes4 = 0f;
    private float totalAberto = 0f;
    private float totalAberto2 = 0f;
    private float totalAberto3 = 0f;
    private float totalAberto4 = 0f;
    private Date data;
    private Date data2;
    private Date data3;
    private Date data4;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private VendasDao vendasDao;

    @PostConstruct
    public void init() {
        gerarListaCliente();
        if (usuarioLogadoMB.getCliente() != null) {
            cliente = usuarioLogadoMB.getCliente();
        } else {
            cliente = new Cliente();
        }
        desabilitarUnidade();
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData2() {
        return data2;
    }

    public void setData2(Date data2) {
        this.data2 = data2;
    }

    public Date getData3() {
        return data3;
    }

    public void setData3(Date data3) {
        this.data3 = data3;
    }

    public Date getData4() {
        return data4;
    }

    public void setData4(Date data4) {
        this.data4 = data4;
    }

    public float getFaturamento2() {
        return faturamento2;
    }

    public void setFaturamento2(float faturamento2) {
        this.faturamento2 = faturamento2;
    }

    public float getFaturamento3() {
        return faturamento3;
    }

    public void setFaturamento3(float faturamento3) {
        this.faturamento3 = faturamento3;
    }

    public float getFaturamento4() {
        return faturamento4;
    }

    public void setFaturamento4(float faturamento4) {
        this.faturamento4 = faturamento4;
    }

    public float getTotalRecebidoMes2() {
        return totalRecebidoMes2;
    }

    public void setTotalRecebidoMes2(float totalRecebidoMes2) {
        this.totalRecebidoMes2 = totalRecebidoMes2;
    }

    public float getTotalRecebidoMes3() {
        return totalRecebidoMes3;
    }

    public void setTotalRecebidoMes3(float totalRecebidoMes3) {
        this.totalRecebidoMes3 = totalRecebidoMes3;
    }

    public float getTotalRecebidoMes4() {
        return totalRecebidoMes4;
    }

    public void setTotalRecebidoMes4(float totalRecebidoMes4) {
        this.totalRecebidoMes4 = totalRecebidoMes4;
    }

    public float getTotalAberto2() {
        return totalAberto2;
    }

    public void setTotalAberto2(float totalAberto2) {
        this.totalAberto2 = totalAberto2;
    }

    public float getTotalAberto3() {
        return totalAberto3;
    }

    public void setTotalAberto3(float totalAberto3) {
        this.totalAberto3 = totalAberto3;
    }

    public float getTotalAberto4() {
        return totalAberto4;
    }

    public void setTotalAberto4(float totalAberto4) {
        this.totalAberto4 = totalAberto4;
    }

    public float getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(float faturamento) {
        this.faturamento = faturamento;
    }

    public float getTotalRecebidoMes() {
        return totalRecebidoMes;
    }

    public void setTotalRecebidoMes(float totalRecebidoMes) {
        this.totalRecebidoMes = totalRecebidoMes;
    }

    public float getTotalAberto() {
        return totalAberto;
    }

    public void setTotalAberto(float totalAberto) {
        this.totalAberto = totalAberto;
    }

    public Date getSegundaData() {
        return segundaData;
    }

    public void setSegundaData(Date segundaData) {
        this.segundaData = segundaData;
    }

    public Date getTerceiraData() {
        return terceiraData;
    }

    public void setTerceiraData(Date terceiraData) {
        this.terceiraData = terceiraData;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
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

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
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

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "";
        String nomeRelatorio = null;
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (relatorio.equalsIgnoreCase("vendas")) {
            caminhoRelatorio = "reports/Relatorios/vendas/reportVendas.jasper";
            nomeRelatorio = "Mapa de Vendas Gerencial";
        } else if (relatorio.equalsIgnoreCase("vendasRecebimento")) {
            caminhoRelatorio = "reports/Relatorios/vendas/reportVendasRecebimento.jasper";
            nomeRelatorio = "Relat�rio de Vendas por Recebimentos";
        } else if (relatorio.equalsIgnoreCase("faturamentoRecebimento")) {
            caminhoRelatorio = "reports/Relatorios/vendas/reportFaturamentoRecebimento.jasper";
            nomeRelatorio = "Faturamento X Recebimento";
            segundaData();
            terceiraData();
            gerarDatas();
            calcularValoresFaturamentoRecebimento();
            calcularValoresTotalRecebidoMes();
            calcularValoresTotalAberto();
            parameters.put("faturamento", faturamento);
            parameters.put("faturamento2", faturamento2);
            parameters.put("faturamento3", faturamento3);
            parameters.put("faturamento4", faturamento4);
            parameters.put("totalRecebidoMes", totalRecebidoMes);
            parameters.put("totalRecebidoMes2", totalRecebidoMes2);
            parameters.put("totalRecebidoMes3", totalRecebidoMes3);
            parameters.put("totalRecebidoMes4", totalRecebidoMes4);
            parameters.put("totalAberto", totalAberto);
            parameters.put("totalAberto2", totalAberto2);
            parameters.put("totalAberto3", totalAberto3);
            parameters.put("totalAberto4", totalAberto4);
            parameters.put("data2", segundaData);
            parameters.put("data3", terceiraData);
            zerarValores();
        }
        File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("sql", gerarSqlImpresssao());
        parameters.put("dataInicial", dataInicial);
        parameters.put("dataFinal", dataFinal);
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio, null);
        } catch (JRException ex) {
            Logger.getLogger(ImprimirVendasMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImprimirVendasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String gerarSqlImpresssao() {
        String sql = "";
        if (relatorio.equalsIgnoreCase("vendas")) {
            sql = sql + "SELECT distinct vendas.dataVenda, vendas.valorBruto, vendas.valordesconto, "
                    + "vendas.comissaoLiquidaTotal, vendas.despesasFinanceiras, vendas.comissaoTerceiros, "
                    + "vendas.comissaoFuncionarios, vendas.liquidoVendas, produto.descricao, "
                    + "vendas.nomeCliente, vendas.idvendas, cliente.nomeFantasia  From "
                    + "vendas join cliente on vendas.cliente_idcliente = cliente.idcliente "
                    + "join produto on vendas.produto_idproduto = produto.idproduto "
                    + "where vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and vendas.dataVenda<='"
                    + Formatacao.ConvercaoDataSql(dataFinal) + "' and cliente.idcliente=" + cliente.getIdcliente()
                    + " order by vendas.dataVenda";
        } else if (relatorio.equalsIgnoreCase("vendasRecebimento")) {
            sql = sql + "SELECT distinct vendas.dataVenda, vendas.valorBruto,  contasreceber.numeroParcela, vendas.liquidoVendas, cliente.nomeFantasia,"
                    + " vendas.idvendas, contasreceber.valorParcela, contasreceber.dataVencimento From vendas join "
                    + "cliente on vendas.cliente_idcliente = cliente.idcliente "
                    + " join contasreceber on vendas.idvendas=contasreceber.venda where vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' and vendas.dataVenda<='"
                    + Formatacao.ConvercaoDataSql(dataFinal) + "' and cliente.idcliente=" + cliente.getIdcliente()
                    + " order by vendas.dataVenda";
        } else if (relatorio.equalsIgnoreCase("faturamentoRecebimento")) {
            sql = sql + "Select distinct cliente.nomeFantasia from cliente where cliente.idcliente=" + cliente.getIdcliente();
        }

        return sql;
    }

    public Date segundaData() {
        Calendar c = new GregorianCalendar();
        c.setTime(dataInicial);
        c.add(Calendar.MONTH, 1);
        segundaData = c.getTime();
        return segundaData;
    }

    public Date terceiraData() {
        Calendar c = new GregorianCalendar();
        c.setTime(dataInicial);
        c.add(Calendar.MONTH, 2);
        terceiraData = c.getTime();
        return terceiraData;
    }

    public void gerarDatas() {
        Calendar c = new GregorianCalendar();
        c.setTime(dataInicial);
        c.add(Calendar.DAY_OF_MONTH, 30);
        data = c.getTime();

        Calendar c2 = new GregorianCalendar();
        c2.setTime(segundaData);
        c2.add(Calendar.DAY_OF_MONTH, 30);
        data2 = c2.getTime();

        Calendar c3 = new GregorianCalendar();
        c3.setTime(terceiraData);
        c3.add(Calendar.DAY_OF_MONTH, 30);
        data3 = c3.getTime();

        Calendar c4 = new GregorianCalendar();
        c4.setTime(dataFinal);
        c4.add(Calendar.DAY_OF_MONTH, 30);
        data4 = c4.getTime();
    }

    public void calcularValoresFaturamentoRecebimento() {
        List<Vendas> listaFaturamentoVendas;
        List<Vendas> listaFaturamentoVendas2;
        List<Vendas> listaFaturamentoVendas3;
        List<Vendas> listaFaturamentoVendas4;

        listaFaturamentoVendas = vendasDao.list("Select v from Vendas v where v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial)
                + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(data) + "' and v.cliente.idcliente=" + cliente.getIdcliente());
        listaFaturamentoVendas2 = vendasDao.list("Select v from Vendas v where v.dataVenda>='" + Formatacao.ConvercaoDataSql(segundaData)
                + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(data2) + "' and v.cliente.idcliente=" + cliente.getIdcliente());
        listaFaturamentoVendas3 = vendasDao.list("Select v from Vendas v where v.dataVenda>='" + Formatacao.ConvercaoDataSql(terceiraData)
                + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(data3) + "' and v.cliente.idcliente=" + cliente.getIdcliente());
        listaFaturamentoVendas4 = vendasDao.list("Select v from Vendas v where v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataFinal)
                + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(data4) + "' and v.cliente.idcliente=" + cliente.getIdcliente());
        if (listaFaturamentoVendas == null) {
            listaFaturamentoVendas = new ArrayList<Vendas>();
        }
        if (listaFaturamentoVendas2 == null) {
            listaFaturamentoVendas2 = new ArrayList<Vendas>();
        }
        if (listaFaturamentoVendas3 == null) {
            listaFaturamentoVendas3 = new ArrayList<Vendas>();
        }
        if (listaFaturamentoVendas4 == null) {
            listaFaturamentoVendas4 = new ArrayList<Vendas>();
        }
        for (int i = 0; i < listaFaturamentoVendas.size(); i++) {
            faturamento = faturamento + listaFaturamentoVendas.get(i).getValorLiquido();
        }
        for (int i = 0; i < listaFaturamentoVendas2.size(); i++) {
            faturamento2 = faturamento2 + listaFaturamentoVendas2.get(i).getValorLiquido();
        }
        for (int i = 0; i < listaFaturamentoVendas3.size(); i++) {
            faturamento3 = faturamento3 + listaFaturamentoVendas3.get(i).getValorLiquido();
        }
        for (int i = 0; i < listaFaturamentoVendas4.size(); i++) {
            faturamento4 = faturamento4 + listaFaturamentoVendas4.get(i).getValorLiquido();
        }

    }

    public void calcularValoresTotalRecebidoMes() {
        List<Contasreceber> listaTotalRecebidoMes;
        List<Contasreceber> listaTotalRecebidoMes2;
        List<Contasreceber> listaTotalRecebidoMes3;
        List<Contasreceber> listaTotalRecebidoMes4;

        listaTotalRecebidoMes = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='"
                + Formatacao.ConvercaoDataSql(dataInicial) + "' and  c.dataVencimento<='" + Formatacao.ConvercaoDataSql(data)
                + "' and c.valorPago>0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalRecebidoMes2 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='"
                + Formatacao.ConvercaoDataSql(segundaData) + "' and  c.dataVencimento<='" + Formatacao.ConvercaoDataSql(data2)
                + "' and c.valorPago>0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalRecebidoMes3 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='"
                + Formatacao.ConvercaoDataSql(terceiraData) + "' and  c.dataVencimento<='" + Formatacao.ConvercaoDataSql(data3)
                + "' and c.valorPago>0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalRecebidoMes4 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='"
                + Formatacao.ConvercaoDataSql(dataFinal) + "' and  c.dataVencimento<='" + Formatacao.ConvercaoDataSql(data4)
                + "' and c.valorPago>0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        if (listaTotalRecebidoMes == null) {
            listaTotalRecebidoMes = new ArrayList<Contasreceber>();
        }
        if (listaTotalRecebidoMes2 == null) {
            listaTotalRecebidoMes2 = new ArrayList<Contasreceber>();
        }
        if (listaTotalRecebidoMes3 == null) {
            listaTotalRecebidoMes3 = new ArrayList<Contasreceber>();
        }
        if (listaTotalRecebidoMes4 == null) {
            listaTotalRecebidoMes4 = new ArrayList<Contasreceber>();
        }
        for (int i = 0; i < listaTotalRecebidoMes.size(); i++) {
            totalRecebidoMes = totalRecebidoMes + listaTotalRecebidoMes.get(i).getValorPago();
        }
        for (int i = 0; i < listaTotalRecebidoMes2.size(); i++) {
            totalRecebidoMes2 = totalRecebidoMes2 + listaTotalRecebidoMes2.get(i).getValorPago();
        }
        for (int i = 0; i < listaTotalRecebidoMes3.size(); i++) {
            totalRecebidoMes3 = totalRecebidoMes3 + listaTotalRecebidoMes3.get(i).getValorPago();
        }
        for (int i = 0; i < listaTotalRecebidoMes4.size(); i++) {
            totalRecebidoMes4 = totalRecebidoMes4 + listaTotalRecebidoMes4.get(i).getValorPago();
        }

    }

    public void calcularValoresTotalAberto() {
        List<Contasreceber> listaTotalAberto;
        List<Contasreceber> listaTotalAberto2;
        List<Contasreceber> listaTotalAberto3;
        List<Contasreceber> listaTotalAberto4;

        listaTotalAberto = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial)
                + "' and c.dataVencimento<'" + Formatacao.ConvercaoDataSql(data) + "' and c.valorPago=0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalAberto2 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='" + Formatacao.ConvercaoDataSql(segundaData)
                + "' and c.dataVencimento<'" + Formatacao.ConvercaoDataSql(data2) + "' and c.valorPago=0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalAberto3 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='" + Formatacao.ConvercaoDataSql(terceiraData)
                + "' and c.dataVencimento<'" + Formatacao.ConvercaoDataSql(data3) + "' and c.valorPago=0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        listaTotalAberto4 = contasReceberDao.list("Select c from Contasreceber c where c.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataFinal)
                + "' and c.dataVencimento<'" + Formatacao.ConvercaoDataSql(data4) + "' and c.valorPago=0 and c.status<>'CANCELADA' and c.cliente.idcliente=" + cliente.getIdcliente());
        if (listaTotalAberto == null) {
            listaTotalAberto = new ArrayList<Contasreceber>();
        }
        if (listaTotalAberto2 == null) {
            listaTotalAberto2 = new ArrayList<Contasreceber>();
        }
        if (listaTotalAberto3 == null) {
            listaTotalAberto3 = new ArrayList<Contasreceber>();
        }
        if (listaTotalAberto4 == null) {
            listaTotalAberto4 = new ArrayList<Contasreceber>();
        }
        for (int i = 0; i < listaTotalAberto.size(); i++) {
            totalAberto = totalAberto + listaTotalAberto.get(i).getValorParcela();
        }
        for (int i = 0; i < listaTotalAberto2.size(); i++) {
            totalAberto2 = totalAberto2 + listaTotalAberto2.get(i).getValorParcela();
        }
        for (int i = 0; i < listaTotalAberto3.size(); i++) {
            totalAberto3 = totalAberto3 + listaTotalAberto3.get(i).getValorParcela();
        }
        for (int i = 0; i < listaTotalAberto4.size(); i++) {
            totalAberto4 = totalAberto4 + listaTotalAberto4.get(i).getValorParcela();
        }

    }

    public void zerarValores() {
        faturamento = 0f;
        faturamento2 = 0f;
        faturamento3 = 0f;
        faturamento4 = 0f;
        totalAberto = 0f;
        totalAberto2 = 0f;
        totalAberto3 = 0f;
        totalAberto4 = 0f;
        totalRecebidoMes = 0f;
        totalRecebidoMes2 = 0f;
        totalRecebidoMes3 = 0f;
        totalRecebidoMes4 = 0f;
    }

}
