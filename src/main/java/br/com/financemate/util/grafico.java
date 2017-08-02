package br.com.financemate.util;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.VendasDao;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jfree.data.time.Year;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Vendas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class grafico implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private BarChartModel animatedModel3;
    private Integer nVendasJaneiros;
    private Date dataInicial;
    private Date dataFinal;
    private Integer diaInicio;
    private Integer diaFinal;
    private Date dataTerceiroDia;
    private Date dataQuartoDia;
    private Date dataQuintoDia;
    private Float saldoPrimeiroDia = 0f;
    private Float saldoSegundoDia = 0f;
    private Float saldoTerceiroDia = 0f;
    private Float saldoQuartoDia = 0f;
    private Float saldoQuintaDia = 0f;
    private Integer numeroDia;
    private Float valorMaximo = 0f;
    private Float valorMinimo = 0f;
    private Float valorFaturamento = 0f;
    private String valorTotalFaturamento = "";
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private VendasDao vendasDao;
    private String ano = "" + new Year();
    private int nVendasGrafico =0;

    @PostConstruct
    public void init() {
        createAnimatedModels();
        valorTotalFaturamento = Formatacao.foramtarFloatString(valorFaturamento);
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public String getValorTotalFaturamento() {
        return valorTotalFaturamento;
    }

    public void setValorTotalFaturamento(String valorTotalFaturamento) {
        this.valorTotalFaturamento = valorTotalFaturamento;
    }

    public Float getValorFaturamento() {
        return valorFaturamento;
    }

    public void setValorFaturamento(Float valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public BarChartModel getAnimatedModel3() {
        return animatedModel3;
    }

    public void setAnimatedModel3(BarChartModel animatedModel3) {
        this.animatedModel3 = animatedModel3;
    }

    public void setAnimatedModel1(LineChartModel animatedModel1) {
        this.animatedModel1 = animatedModel1;
    }

    public void setAnimatedModel2(BarChartModel animatedModel2) {
        this.animatedModel2 = animatedModel2;
    }

    public Float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Float getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }

    public Float getSaldoPrimeiroDia() {
        return saldoPrimeiroDia;
    }

    public void setSaldoPrimeiroDia(Float saldoPrimeiroDia) {
        this.saldoPrimeiroDia = saldoPrimeiroDia;
    }

    public Float getSaldoSegundoDia() {
        return saldoSegundoDia;
    }

    public void setSaldoSegundoDia(Float saldoSegundoDia) {
        this.saldoSegundoDia = saldoSegundoDia;
    }

    public Float getSaldoTerceiroDia() {
        return saldoTerceiroDia;
    }

    public void setSaldoTerceiroDia(Float saldoTerceiroDia) {
        this.saldoTerceiroDia = saldoTerceiroDia;
    }

    public Float getSaldoQuartoDia() {
        return saldoQuartoDia;
    }

    public void setSaldoQuartoDia(Float saldoQuartoDia) {
        this.saldoQuartoDia = saldoQuartoDia;
    }

    public Float getSaldoQuintaDia() {
        return saldoQuintaDia;
    }

    public void setSaldoQuintaDia(Float saldoQuintaDia) {
        this.saldoQuintaDia = saldoQuintaDia;
    }

    public Date getDataTerceiroDia() {
        return dataTerceiroDia;
    }

    public void setDataTerceiroDia(Date dataTerceiroDia) {
        this.dataTerceiroDia = dataTerceiroDia;
    }

    public Date getDataQuartoDia() {
        return dataQuartoDia;
    }

    public void setDataQuartoDia(Date dataQuartoDia) {
        this.dataQuartoDia = dataQuartoDia;
    }

    public Date getDataQuintoDia() {
        return dataQuintoDia;
    }

    public void setDataQuintoDia(Date dataQuintoDia) {
        this.dataQuintoDia = dataQuintoDia;
    }

    public Integer getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(Integer diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Integer getDiaFinal() {
        return diaFinal;
    }

    public void setDiaFinal(Integer diaFinal) {
        this.diaFinal = diaFinal;
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

    public Integer getnVendasJaneiros() {
        return nVendasJaneiros;
    }

    public void setnVendasJaneiros(Integer nVendasJaneiros) {
        this.nVendasJaneiros = nVendasJaneiros;
    }

    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }

    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getnVendasGrafico() {
        return nVendasGrafico;
    }

    public void setnVendasGrafico(int nVendasGrafico) {
        this.nVendasGrafico = nVendasGrafico;
    }
    
    
    

    private void createAnimatedModels() {
        gerarDiasFluxoCaixa();
        animatedModel1 = initLinearModel();
        animatedModel1.setAnimate(true);
        animatedModel1.getAxes().put(AxisType.X, new CategoryAxis("Dia"));
       // animatedModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        animatedModel1.setSeriesColors("66cc66, FE2E2E, A4A4A4");
        animatedModel1.setLegendPosition("se");
        animatedModel1.setZoom(true);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setLabel("R$");
        yAxis.setMin(valorMinimo.floatValue());
        yAxis.setMax(valorMaximo.floatValue() + 100);

        animatedModel2 = initBarModel();

        animatedModel2.setAnimate(true);
        yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(nVendasGrafico + 200);

    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        ChartSeries mes = new ChartSeries();
        mes.setLabel("Quantidade");

        mes.set("JAN", gerarVendasMensaisJaneiro());
        mes.set("FEV", gerarVendasMensaisFevereiro());
        mes.set("MAR", gerarVendasMensaisMarco());
        mes.set("ABRI", gerarVendasMensaisAbril());
        mes.set("MAI", gerarVendasMensaisMaio());
        mes.set("JUN", gerarVendasMensaisJunho());
        mes.set("JUL", gerarVendasMensaisJulho());
        mes.set("AGO", gerarVendasMensaisAgosto());
        mes.set("SET", gerarVendasMensaisSetembro());
        mes.set("OUT", gerarVendasMensaisOutubro());
        mes.set("NOV", gerarVendasMensaisNovembro());
        mes.set("DEZ", gerarVendasMensaisDezembro());
        model.addSeries(mes);

        return model;
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        Date data = cal.getTime();
        int mes = data.getMonth() + 1;
        int ndia = data.getDay() + 8;
        LineChartSeries recebimentos = new LineChartSeries();
        recebimentos.setLabel("Recebimentos");
        recebimentos.set(dia, recebimentosDia1());
        recebimentos.set(gerarNumeroDia(dia + 1, mes), recebimentosDia2());
        recebimentos.set(gerarNumeroDia(dia + 2, mes), recebimentosDia3());
        recebimentos.set(gerarNumeroDia(dia + 3, mes), recebimentosDia4());
        recebimentos.set(gerarNumeroDia(dia + 4, mes), recebimentosDia5());

        LineChartSeries pagamentos = new LineChartSeries();
        pagamentos.setLabel("Pagamentos");
        pagamentos.set(dia, pagamentodia1());
        pagamentos.set(gerarNumeroDia(dia + 1, mes), pagamentodia2());
        pagamentos.set(gerarNumeroDia(dia + 2, mes), pagamentodia3());
        pagamentos.set(gerarNumeroDia(dia + 3, mes), pagamentodia4());
        pagamentos.set(gerarNumeroDia(dia + 4, mes), pagamentodia5());

        LineChartSeries saldo = new LineChartSeries();
        saldo.setLabel("Saldo");

        saldo.set(dia, saldoPrimeiroDia);
        saldo.set(gerarNumeroDia(dia + 1, mes), saldoSegundoDia);
        saldo.set(gerarNumeroDia(dia + 2, mes), saldoTerceiroDia);
        saldo.set(gerarNumeroDia(dia + 3, mes), saldoQuartoDia);
        saldo.set(gerarNumeroDia(dia + 4, mes), saldoQuintaDia);

        model.addSeries(recebimentos);
        model.addSeries(pagamentos);
        model.addSeries(saldo);
        return model;
    }

    public Integer gerarNumeroDia(Integer dia, Integer mes) {
        if (mes == 1) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 2) {
            if (dia > 28) {
                dia = 1;
            }
        } else if (mes == 3) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 4) {
            if (dia > 30) {
                dia = 1;
            }
        } else if (mes == 5) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 6) {
            if (dia > 30) {
                dia = 1;
            }
        } else if (mes == 7) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 8) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 9) {
            if (dia > 30) {
                return null;
            }
        } else if (mes == 10) {
            if (dia > 31) {
                dia = 1;
            }
        } else if (mes == 11) {
            if (dia > 30) {
                dia = 1;
            }
        } else if (mes == 12) {
            if (dia > 31) {
                dia = 1;
            }
        }
        return dia;
    }

    public Integer gerarVendasMensaisJaneiro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-01-01'"
                + " and v.dataVenda<='" + new Year() + "-01-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisFevereiro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-02-01'"
                + " and v.dataVenda<='" + new Year() + "-02-28'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisMarco() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-03-01'"
                + " and v.dataVenda<='" + new Year() + "-03-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisAbril() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-04-01'"
                + " and v.dataVenda<='" + new Year() + "-04-30'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisMaio() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-05-01'"
                + " and v.dataVenda<='" + new Year() + "-05-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisJunho() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-06-01'"
                + " and v.dataVenda<='" + new Year() + "-06-30'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisJulho() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-07-01'"
                + " and v.dataVenda<='" + new Year() + "-07-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisAgosto() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-08-01'"
                + " and v.dataVenda<='" + new Year() + "-08-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisSetembro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-09-01'"
                + " and v.dataVenda<='" + new Year() + "-09-30'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisOutubro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-10-01'"
                + " and v.dataVenda<='" + new Year() + "-10-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisNovembro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-11-01'"
                + " and v.dataVenda<='" + new Year() + "-11-30'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public Integer gerarVendasMensaisDezembro() {
        String sql = "Select v From Vendas v where v.dataVenda>='" + new Year() + "-12-01'"
                + " and v.dataVenda<='" + new Year() + "-12-31'";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }
        List<Vendas> listaQuantidadeVendas = vendasDao.list(sql);
        for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
            valorFaturamento = valorFaturamento + listaQuantidadeVendas.get(i).getValorLiquido();
        }
        if (nVendasGrafico < listaQuantidadeVendas.size()) {
            nVendasGrafico = listaQuantidadeVendas.size();
        }
        return listaQuantidadeVendas.size();
    }

    public void gerarDiasFluxoCaixa() {
        dataInicial = new Date();
        numeroDia = dataInicial.getDay();
        Calendar c = new GregorianCalendar();
        c.setTime(dataInicial);
        c.add(Calendar.DAY_OF_MONTH, 5);
        Date data = c.getTime();
        dataFinal = data;
        diaFinal = dataFinal.getDay();
        Calendar c2 = new GregorianCalendar();
        c2.setTime(dataInicial);
        c2.add(Calendar.DAY_OF_MONTH, 1);
        Date data2 = c2.getTime();
        dataInicial = data2;
        diaInicio = dataInicial.getDay();
        Calendar c3 = new GregorianCalendar();
        c3.setTime(dataInicial);
        c3.add(Calendar.DAY_OF_MONTH, 1);
        Date data3 = c3.getTime();
        dataTerceiroDia = data3;
        Calendar c4 = new GregorianCalendar();
        c4.setTime(dataTerceiroDia);
        c4.add(Calendar.DAY_OF_MONTH, 1);
        Date data4 = c4.getTime();
        dataQuartoDia = data4;
        Calendar c5 = new GregorianCalendar();
        c5.setTime(dataQuartoDia);
        c5.add(Calendar.DAY_OF_MONTH, 1);
        Date data5 = c5.getTime();
        dataQuintoDia = data5;
    }

    public float recebimentosDia1() {
        Float recebimento = null;
        try {
            String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(new Date()) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            recebimento = contasReceberDao.recebimentoPorDia(sql);
            if (recebimento == null) {
                recebimento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoPrimeiroDia = saldoPrimeiroDia + recebimento;
        if (recebimento >= valorMaximo) {
            valorMaximo = recebimento;
        } else if (recebimento < valorMinimo) {
            valorMinimo = recebimento;
        }
        if (saldoPrimeiroDia >= valorMaximo) {
            valorMaximo = saldoPrimeiroDia;
        } else if (saldoPrimeiroDia < valorMinimo) {
            valorMinimo = saldoPrimeiroDia;
        }
        return recebimento;
    }

    public float recebimentosDia2() {
        Float recebimento = null;
        try {
            String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataInicial) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            recebimento = contasReceberDao.recebimentoPorDia(sql);
            if (recebimento == null) {
                recebimento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoSegundoDia = saldoSegundoDia + recebimento;
        if (recebimento >= valorMaximo) {
            valorMaximo = recebimento;
        } else if (recebimento < valorMinimo) {
            valorMinimo = recebimento;
        }
        if (saldoSegundoDia >= valorMaximo) {
            valorMaximo = saldoSegundoDia;
        } else if (saldoSegundoDia < valorMinimo) {
            valorMinimo = saldoSegundoDia;
        }
        return recebimento;
    }

    public float recebimentosDia3() {
        Float recebimento = null;
        try {
            String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataTerceiroDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            recebimento = contasReceberDao.recebimentoPorDia(sql);
            if (recebimento == null) {
                recebimento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoTerceiroDia = saldoTerceiroDia + recebimento;
        if (recebimento >= valorMaximo) {
            valorMaximo = recebimento;
        } else if (recebimento < valorMinimo) {
            valorMinimo = recebimento;
        }
        if (saldoTerceiroDia >= valorMaximo) {
            valorMaximo = saldoTerceiroDia;
        } else if (saldoTerceiroDia < valorMinimo) {
            valorMinimo = saldoTerceiroDia;
        }
        return recebimento;
    }

    public float recebimentosDia4() {
        Float recebimento = null;
        try {
            String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuartoDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            recebimento = contasReceberDao.recebimentoPorDia(sql);
            if (recebimento == null) {
                recebimento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoQuartoDia = saldoQuartoDia + recebimento;
        if (recebimento >= valorMaximo) {
            valorMaximo = recebimento;
        } else if (recebimento < valorMinimo) {
            valorMinimo = recebimento;
        }
        if (saldoQuartoDia >= valorMaximo) {
            valorMaximo = saldoQuartoDia;
        } else if (saldoQuartoDia < valorMinimo) {
            valorMinimo = saldoQuartoDia;
        }
        return recebimento;
    }

    public float recebimentosDia5() {
        Float recebimento = null;
        try {
            String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuintoDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            recebimento = contasReceberDao.recebimentoPorDia(sql);
            if (recebimento == null) {
                recebimento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoQuintaDia = saldoQuintaDia + recebimento;
        if (recebimento >= valorMaximo) {
            valorMaximo = recebimento;
        } else if (recebimento < valorMinimo) {
            valorMinimo = recebimento;
        }
        if (saldoQuintaDia >= valorMaximo) {
            valorMaximo = saldoQuintaDia;
        } else if (saldoQuintaDia < valorMinimo) {
            valorMinimo = saldoQuintaDia;
        }
        return recebimento;
    }

    public float pagamentodia1() {
        Float pagamento = null;
        try {
            String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(new Date()) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            pagamento = contasPagarDao.pagamentoPorDia(sql);
            if (pagamento == null) {
                pagamento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoPrimeiroDia = saldoPrimeiroDia - pagamento;
        if (pagamento >= valorMaximo) {
            valorMaximo = pagamento;
        } else if (pagamento < valorMinimo) {
            valorMinimo = pagamento;
        }
        if (saldoQuintaDia >= valorMaximo) {
            valorMaximo = saldoPrimeiroDia;
        } else if (saldoPrimeiroDia < valorMinimo) {
            valorMinimo = saldoPrimeiroDia;
        }
        return pagamento;
    }

    public float pagamentodia2() {
        Float pagamento = null;
        try {
            String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataInicial) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            pagamento = contasPagarDao.pagamentoPorDia(sql);
            if (pagamento == null) {
                pagamento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoSegundoDia = saldoSegundoDia - pagamento;
        if (pagamento >= valorMaximo) {
            valorMaximo = pagamento;
        } else if (pagamento < valorMinimo) {
            valorMinimo = pagamento;
        }
        if (saldoSegundoDia >= valorMaximo) {
            valorMaximo = saldoSegundoDia;
        } else if (saldoSegundoDia < valorMinimo) {
            valorMinimo = saldoSegundoDia;
        }
        return pagamento;
    }

    public float pagamentodia3() {
        Float pagamento = null;
        try {
            String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataTerceiroDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            pagamento = contasPagarDao.pagamentoPorDia(sql);
            if (pagamento == null) {
                pagamento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoTerceiroDia = saldoTerceiroDia - pagamento;
        if (pagamento >= valorMaximo) {
            valorMaximo = pagamento;
        } else if (pagamento < valorMinimo) {
            valorMinimo = pagamento;
        }
        if (saldoTerceiroDia >= valorMaximo) {
            valorMaximo = saldoTerceiroDia;
        } else if (saldoTerceiroDia < valorMinimo) {
            valorMinimo = saldoTerceiroDia;
        }
        return pagamento;
    }

    public float pagamentodia4() {
        Float pagamento = null;
        try {
            String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuartoDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            pagamento = contasPagarDao.pagamentoPorDia(sql);
            if (pagamento == null) {
                pagamento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoQuartoDia = saldoQuartoDia - pagamento;
        if (pagamento >= valorMaximo) {
            valorMaximo = pagamento;
        } else if (pagamento < valorMinimo) {
            valorMinimo = pagamento;
        }
        if (saldoQuartoDia >= valorMaximo) {
            valorMaximo = saldoQuartoDia;
        } else if (saldoQuartoDia < valorMinimo) {
            valorMinimo = saldoQuartoDia;
        }
        return pagamento;
    }

    public float pagamentodia5() {
        Float pagamento = null;
        try {
            String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuintoDia) + "'";
            if (usuarioLogadoMB.getCliente() != null) {
                sql = sql + " and c.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
            }
            pagamento = contasPagarDao.pagamentoPorDia(sql);
            if (pagamento == null) {
                pagamento = 0f;
            }
        } catch (SQLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        saldoQuintaDia = saldoQuintaDia - pagamento;
        if (pagamento >= valorMaximo) {
            valorMaximo = pagamento;
        } else if (pagamento < valorMinimo) {
            valorMinimo = pagamento;
        }
        if (saldoQuintaDia >= valorMaximo) {
            valorMaximo = saldoQuintaDia;
        } else if (saldoQuintaDia < valorMinimo) {
            valorMinimo = saldoQuintaDia;
        }
        return pagamento;
    }
}
