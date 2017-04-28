package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.CobrancaParcelasDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.HistoricoCobrancaDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.VendasDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.manageBean.CalculosContasMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobrancaparcelas;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Historicocobranca;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ContasReceberMB implements Serializable {

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
    private String sql;
    private List<Contasreceber> listaContasReceber;
    private Contasreceber contasReceber;
    private boolean recebidas;
    private boolean verCliente = false;
    private int quantidadeTitulos;
    private float totalContasReceber;
    private float totalJurosReceber;
    private float totalDescontosReceber;
    private float valorTotalRecebido;
    private String totalVencer;
    private String totalVencidas;
    private String total;
    private Banco banco;
    private List<Banco> listaBanco;
    private String imagemFiltro = "#3D7E46;";
    private List<Contasreceber> listaSelecionadas;
    private Vendas vendas;
    @Inject
    private CalculosContasMB calculosContasMB;
    private Boolean habilitar = true;
    private List<Contasreceber> listaContasSelecionadas;
    private String totalReceberLote;
    private Date dataRecebimentoLote;
    private String nomeCliente;
    private float valorParcela;
    private int nVenda;
    private String status = "";
    private Float valorPagoParcial;
    private Integer totalParcela;
    private List<Contasreceber> listaTotalParcela;
    private Integer cob;
    private List<Cobrancaparcelas> listaCob;
    private Boolean habilitarUnidade = false;
    private Date dataRecebimentoInicial;
    private Date dataRecebimentoFinal;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CobrancaParcelasDao cobrancaParcelasDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private VendasDao vendasDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private HistoricoCobrancaDao historicoCobrancaDao;

    @PostConstruct
    public void init() {
        gerarListaCliente();
        getUsuarioLogadoMB();
        verificarCliente();
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            desabilitarUnidade();
            criarConsultaContaReceber();
            cliente = clienteDao.find(usuarioLogadoMB.getUsuario().getCliente());
        }
    }

    public Date getDataRecebimentoInicial() {
        return dataRecebimentoInicial;
    }

    public void setDataRecebimentoInicial(Date dataRecebimentoInicial) {
        this.dataRecebimentoInicial = dataRecebimentoInicial;
    }

    public Date getDataRecebimentoFinal() {
        return dataRecebimentoFinal;
    }

    public void setDataRecebimentoFinal(Date dataRecebimentoFinal) {
        this.dataRecebimentoFinal = dataRecebimentoFinal;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public List<Cobrancaparcelas> getListaCob() {
        return listaCob;
    }

    public void setListaCob(List<Cobrancaparcelas> listaCob) {
        this.listaCob = listaCob;
    }

    public Integer getCob() {
        return cob;
    }

    public void setCob(Integer cob) {
        this.cob = cob;
    }

    public List<Contasreceber> getListaTotalParcela() {
        return listaTotalParcela;
    }

    public void setListaTotalParcela(List<Contasreceber> listaTotalParcela) {
        this.listaTotalParcela = listaTotalParcela;
    }

    public Integer getTotalParcela() {
        return totalParcela;
    }

    public void setTotalParcela(Integer totalParcela) {
        this.totalParcela = totalParcela;
    }

    public Float getValorPagoParcial() {
        return valorPagoParcial;
    }

    public void setValorPagoParcial(Float valorPagoParcial) {
        this.valorPagoParcial = valorPagoParcial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getnVenda() {
        return nVenda;
    }

    public void setnVenda(int nVenda) {
        this.nVenda = nVenda;
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

    public CobrancaParcelasDao getCobrancaParcelasDao() {
        return cobrancaParcelasDao;
    }

    public void setCobrancaParcelasDao(CobrancaParcelasDao cobrancaParcelasDao) {
        this.cobrancaParcelasDao = cobrancaParcelasDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public VendasDao getVendasDao() {
        return vendasDao;
    }

    public void setVendasDao(VendasDao vendasDao) {
        this.vendasDao = vendasDao;
    }

    public OutrosLancamentosDao getOutrosLancamentosDao() {
        return outrosLancamentosDao;
    }

    public void setOutrosLancamentosDao(OutrosLancamentosDao outrosLancamentosDao) {
        this.outrosLancamentosDao = outrosLancamentosDao;
    }

    

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<Contasreceber> getListaContasSelecionadas() {
        return listaContasSelecionadas;
    }

    public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
        this.listaContasSelecionadas = listaContasSelecionadas;
    }

    public String getTotalReceberLote() {
        return totalReceberLote;
    }

    public void setTotalReceberLote(String totalReceberLote) {
        this.totalReceberLote = totalReceberLote;
    }

    public Date getDataRecebimentoLote() {
        return dataRecebimentoLote;
    }

    public void setDataRecebimentoLote(Date dataRecebimentoLote) {
        this.dataRecebimentoLote = dataRecebimentoLote;
    }

    public Boolean getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(Boolean habilitar) {
        this.habilitar = habilitar;
    }

    public CalculosContasMB getCalculosContasMB() {
        return calculosContasMB;
    }

    public void setCalculosContasMB(CalculosContasMB calculosContasMB) {
        this.calculosContasMB = calculosContasMB;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public List<Contasreceber> getListaSelecionadas() {
        return listaSelecionadas;
    }

    public void setListaSelecionadas(List<Contasreceber> listaSelecionadas) {
        this.listaSelecionadas = listaSelecionadas;
    }

    public String getImagemFiltro() {
        return imagemFiltro;
    }

    public void setImagemFiltro(String imagemFiltro) {
        this.imagemFiltro = imagemFiltro;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalVencer() {
        return totalVencer;
    }

    public void setTotalVencer(String totalVencer) {
        this.totalVencer = totalVencer;
    }

    public String getTotalVencidas() {
        return totalVencidas;
    }

    public void setTotalVencidas(String totalVencidas) {
        this.totalVencidas = totalVencidas;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Contasreceber> getListaContasReceber() {
        return listaContasReceber;
    }

    public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
        this.listaContasReceber = listaContasReceber;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public boolean isRecebidas() {
        return recebidas;
    }

    public void setRecebidas(boolean recebidas) {
        this.recebidas = recebidas;
    }

    public boolean isVerCliente() {
        return verCliente;
    }

    public void setVerCliente(boolean verCliente) {
        this.verCliente = verCliente;
    }

    public int getQuantidadeTitulos() {
        return quantidadeTitulos;
    }

    public void setQuantidadeTitulos(int quantidadeTitulos) {
        this.quantidadeTitulos = quantidadeTitulos;
    }

    public float getTotalContasReceber() {
        return totalContasReceber;
    }

    public void setTotalContasReceber(float totalContasReceber) {
        this.totalContasReceber = totalContasReceber;
    }

    public float getTotalJurosReceber() {
        return totalJurosReceber;
    }

    public void setTotalJurosReceber(float totalJurosReceber) {
        this.totalJurosReceber = totalJurosReceber;
    }

    public float getTotalDescontosReceber() {
        return totalDescontosReceber;
    }

    public void setTotalDescontosReceber(float totalDescontosReceber) {
        this.totalDescontosReceber = totalDescontosReceber;
    }

    public float getValorTotalRecebido() {
        return valorTotalRecebido;
    }

    public void setValorTotalRecebido(float valorTotalRecebido) {
        this.valorTotalRecebido = valorTotalRecebido;
    }
    
    

    public String novaConta() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadContasReceber", options, null);
        return "";
    }

    public void retornoDialogNovoPrincipal(String valor) {
        calculosContasMB.calcularTotaisContasReceber();
        calculosContasMB.habilitarDesabilitarSemCompraTelaPrincipal(valor);
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String novaImpressao() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("imprimirContasReceber", options, null);
        return "";
    }

    public void criarConsultaContaReceber() {
        String data = Formatacao.ConvercaoDataPadrao(new Date());
        String mesString = data.substring(3, 5);
        String anoString = data.substring(6, 10);
        int mesInicio = Integer.parseInt(mesString);
        int anoInicio = Integer.parseInt(anoString);
        int mescInicio;
        int mescFinal;
        int anocInicio = 0;
        int anocFinal = 0;
        if (mesInicio == 1) {
            mescInicio = 12;
            anocInicio = anoInicio - 1;
        } else {
            mescInicio = mesInicio - 1;
            anocInicio = anoInicio;
        }
        if (mesInicio == 12) {
            mescFinal = 1;
            anocFinal = anoInicio + 1;
        } else {
            mescFinal = mesInicio + 1;
            anocFinal = anoInicio;
        }
        String dataInicial = anocInicio + "-" + Formatacao.retornaDataInicia(mescInicio);
        String dataFinal = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
        setDataInicial(Formatacao.ConvercaoStringData(dataInicial));
        setDataFinal(Formatacao.ConvercaoStringData(dataFinal));
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            sql = " Select v from Contasreceber v where "
                    + " v.cliente.idcliente=" + usuarioLogadoMB.getUsuario().getCliente()
                    + " and v.dataPagamento is null and v.status<>'CANCELADA' and v.status<>'RECEBIMENTO' " + " order by v.dataVencimento";
        } else {
            sql = " Select v from Contasreceber v where v.cliente.visualizacao='Operacional' and v.cliente.idcliente=" + cliente.getIdcliente()
                    + " and v.dataPagamento is null and v.status<>'CANCELADA' and v.status<>'RECEBIMENTO' " + " order by v.dataVencimento";
        }
        gerarListaContas();
    }

    public void gerarListaContas() {
        listaContasReceber = contasReceberDao.list(sql);
        if (listaContasReceber == null) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
        calcularTotal();
        gerarTotalContas();
    }

    public void gerarTotalContas() {
        quantidadeTitulos = listaContasReceber.size();
        totalContasReceber = 0;
        totalJurosReceber = 0;
        totalDescontosReceber = 0;
        valorTotalRecebido = 0;
        for (int i = 0; i < listaContasReceber.size(); i++) {
            totalContasReceber = totalContasReceber + listaContasReceber.get(i).getValorParcela();
            totalJurosReceber = totalJurosReceber + listaContasReceber.get(i).getJuros();
            totalDescontosReceber = totalDescontosReceber + listaContasReceber.get(i).getDesagio();
            valorTotalRecebido = valorTotalRecebido + listaContasReceber.get(i).getValorPago();
        }

    }

    public void calcularTotal() {
        float vencida = 0.0f;
        float vencendo = 0.0f;
        float vencer = 0.0f;
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        for (int i = 0; i < listaContasReceber.size(); i++) {
            String vencData = Formatacao.ConvercaoDataPadrao(listaContasReceber.get(i).getDataVencimento());
            if (listaContasReceber.get(i).getValorParcela() == null) {
            }else{
                if (diaData.equalsIgnoreCase(vencData)) {
                    vencendo = vencendo + listaContasReceber.get(i).getValorParcela();
                } else if (listaContasReceber.get(i).getDataVencimento().before(data)) {
                    vencida = vencida + listaContasReceber.get(i).getValorParcela();
                } else if (listaContasReceber.get(i).getDataVencimento().after(data)) {
                    vencer = vencer + listaContasReceber.get(i).getValorParcela();
                }
            } 

        }
        setTotalVencidas(Formatacao.foramtarFloatString(vencida));
        setTotalVencer(Formatacao.foramtarFloatString(vencer));
        setTotal(Formatacao.foramtarFloatString(vencida + vencer + vencendo));
    }

    public String verStatus(Contasreceber contasreceber) {
        String corStatus = "../../resources/img/bolaVerde.png";
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        data = Formatacao.ConvercaoStringDataBrasil(diaData);
        if (contasreceber.getDataVencimento().after(data)) {
            corStatus = "../../resources/img/bolaVerde.png";
        } else if (contasreceber.getDataVencimento().before(data)) {
            corStatus =  "../../resources/img/bolaVermelha.png";
        } else if (contasreceber.equals(data)) {
            corStatus = "../../resources/img/bolaAmarela.png";
        }
        
        if (contasreceber.getNumeroDocumento().equalsIgnoreCase("CANCELADA")) {
            corStatus = "../../resources/img/bolinhaPretaS.ico";
        }else if (contasreceber.getDataPagamento() != null){
            corStatus =  "../../resources/img/bolaVerde.png";
        }
        return corStatus;
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
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

    public void retornoDialogNovo(SelectEvent event) {
        Contasreceber contasreceber = (Contasreceber) event.getObject();
        if (contasreceber.getIdcontasReceber() != null) {
            criarConsultaContaReceber();
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
        calculosContasMB.calcularTotalContasPagar();
    }
    
    
    public void retornoDialogRecebimento(SelectEvent event) {
        criarConsultaContaReceber();
        gerarListaContas();
    }

    public String excluir() {
        List<Contasreceber> listaContasMultiplas = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                listaContasMultiplas.add(listaContasReceber.get(i));
            }

        }
        if (listaContasMultiplas.isEmpty()) {
            excluirCobrancaParcela(contasReceber.getIdcontasReceber());
            contasReceberDao.remove(contasReceber.getIdcontasReceber());
        } else {
            for (int i = 0; i < listaContasMultiplas.size(); i++) {
                excluirCobrancaParcela(listaContasMultiplas.get(i).getIdcontasReceber());
                contasReceberDao.remove(listaContasMultiplas.get(i).getIdcontasReceber());
            }
        }
        gerarListaContas();
        mensagem msg = new mensagem();
        msg.excluiMessagem();
        return "";
    }

    private String excluirCobrancaParcela(Integer idcontasReceber) {
        Cobrancaparcelas cobrancaparcelas = new Cobrancaparcelas();
        cobrancaparcelas = cobrancaParcelasDao.find("SELECT c FROM Cobrancaparcelas c where c.contasreceber.idcontasReceber=" + idcontasReceber);
        if (cobrancaparcelas == null) {
            return "";
        } else {
            cobrancaParcelasDao.remove(cobrancaparcelas.getIdcobrancaparcelas());
            cobrancaparcelas = new Cobrancaparcelas();
        }
        return "";
    }

    public String editar(Contasreceber contasreceber) {
        if (contasreceber != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contareceber", contasreceber);
            RequestContext.getCurrentInstance().openDialog("cadContasReceber");
        }
        return "";
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

    public void novoFiltro() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("filtroConsContaReceber", options, null);
    }

    public void filtrar() {
        sql = "Select v from Contasreceber v  where ";
        if (cliente != null) {
            sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente();
            if (nomeCliente != "") {
                sql = sql + " and ";
            } else if (valorParcela != 0f) {
                sql = sql + " and ";
            } else if (nVenda != 0) {
                sql = sql + " and ";
            } else if (banco != null && banco.getIdbanco() != null) {
                sql = sql + " and ";
            } else if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        } else {
            sql = sql + " v.cliente.visualizacao='Operacional'";
            if (nomeCliente != "") {
                sql = sql + " and ";
            } else if (valorParcela != 0f) {
                sql = sql + " and ";
            } else if (nVenda != 0) {
                sql = sql + " and ";
            } else if (banco != null && banco.getIdbanco() != null) {
                sql = sql + " and ";
            } else if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if (nomeCliente != "") {
            sql = sql + " v.nomeCliente like '%" + nomeCliente + "%'";
            if (valorParcela != 0f) {
                sql = sql + " and ";
            } else if (nVenda != 0) {
                sql = sql + " and ";
            } else if (banco != null && banco.getIdbanco() != null) {
                sql = sql + " and ";
            } else if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if (valorParcela != 0f) {
            sql = sql + " v.valorParcela=" + valorParcela;
            if (nVenda != 0) {
                sql = sql + " and ";
            } else if (banco != null && banco.getIdbanco() != null) {
                sql = sql + " and ";
            } else if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if (nVenda != 0) {
            sql = sql + " v.idcontasReceber=" + nVenda;
            if (banco != null && banco.getIdbanco() != null) {
                sql = sql + " and ";
            } else if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if (banco != null && banco.getIdbanco() != null) {
            sql = sql + " v.banco.idbanco=" + banco.getIdbanco();
            if (status != null && !status.equalsIgnoreCase("sn")) {
                sql = sql + " and ";
            } else if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if (status.equalsIgnoreCase("Recebidas")) {
            sql = sql + " v.valorPago>0";
            if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        } else if (status.equalsIgnoreCase("Vencidas")) {
            sql = sql + " v.dataVencimento<'" + Formatacao.ConvercaoDataSql(new Date()) + "' and v.dataPagamento=null";
            if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        } else if (status.equalsIgnoreCase("A vencer")) {
            sql = sql + " v.dataVencimento>'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
            if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        } else if (status.equalsIgnoreCase("Canceladas")) {
            sql = sql + " v.status=" + "'CANCELADA'";
            if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            } else if (dataRecebimentoInicial != null && dataRecebimentoFinal != null) {
                sql = sql + " and ";
            }
        }

        if ((dataRecebimentoInicial != null) && (dataRecebimentoFinal != null)) {
            sql = sql + "v.dataPagamento>='" + Formatacao.ConvercaoDataSql(dataRecebimentoInicial)
                    + "' and v.dataPagamento<='" + Formatacao.ConvercaoDataSql(dataRecebimentoFinal) + "'";
            if ((dataInicial != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }  
        }

        if ((dataInicial != null) && (dataFinal != null)) {
            sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial)
                    + "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal)
                    + "' order by v.dataVencimento";
        }
        RequestContext.getCurrentInstance().closeDialog(sql);
    }

    public String coresFiltrar() {
        if (imagemFiltro.equalsIgnoreCase("#3D7E46;")) {
            novoFiltro();
            imagemFiltro = "#FF0000;";
        } else if (imagemFiltro.equalsIgnoreCase("#FF0000;")) {
            listaContasReceber = null;
            if (cliente == null) {
            } else {
                criarConsultaContaReceber();
            }
            imagemFiltro = "#3D7E46;";
        }
        return "";
    }

    public String recebimentoConta(Contasreceber contasreceber) {
        if (contasreceber != null) {
            valorPagoParcial = contasreceber.getValorPago();
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contareceber", contasreceber);
            session.setAttribute("valorPagoParcial", valorPagoParcial);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("recebimentoConta", options, null);
        }
        return "";
    }

    public String novaCobranca(Contasreceber contasreceber) {
        listaContasSelecionadas = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                listaContasSelecionadas.add(listaContasReceber.get(i));
            }
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasReceber", contasReceber);
        session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
        mensagem mensagem = new mensagem();
        mensagem.cobrancasSelecionadas();
        return "";
    }

    public void novoHistorico(Contasreceber contasreceber) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("historico", options, null);
    }

    public void desfazerRecebimento(Contasreceber contasreceber) {
        Movimentobanco outroslancamentos = new Movimentobanco();
        outroslancamentos.setBanco(contasreceber.getBanco());
        outroslancamentos.setCliente(contasreceber.getCliente());
        outroslancamentos.setValorSaida(contasreceber.getValorPago());
        outroslancamentos.setValorEntrada(0f);
        outroslancamentos.setPlanocontas(contasreceber.getPlanocontas());
        outroslancamentos.setDataVencimento(contasreceber.getDataVencimento());
        outroslancamentos.setDataCompensacao(new Date());
        outroslancamentos.setUsuario(contasreceber.getUsuario());
        outroslancamentos.setIdcontasreceber(contasreceber.getIdcontasReceber());
        outroslancamentos.setTipoDocumento(contasreceber.getTipodocumento());
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setDescricao("Desfazendo recebimento do cliente: " + contasreceber.getNomeCliente() + " da parcela:" + contasreceber.getNumeroParcela());
        outroslancamentos = outrosLancamentosDao.update(outroslancamentos);
        float valorPago = contasreceber.getValorPago();
        contasreceber.setDataPagamento(null);
        contasreceber.setDesagio(0f);
        contasreceber.setJuros(0f);
        contasreceber.setValorParcela(contasreceber.getValorParcela() + valorPago);
        contasreceber.setValorPago(0f);
        excluirRecebimentos(contasreceber);
        contasReceberDao.update(contasreceber);
    }

    public Boolean habilitarDesabilitarDesfazer(Contasreceber contasreceber) {
        Boolean desabilitar = true;
        if (contasreceber.getDataPagamento().equals(null)) {
            desabilitar = false;
            return desabilitar;
        }
        return desabilitar;
    }

    public String novoRecebimentoLote() {
        float semJuros = 0.0f;
        float semDesagio = 0.0f;
        totalReceberLote = "0.00";
        dataRecebimentoLote = new Date();
        float valorTotal = 0.0f;
        listaContasSelecionadas = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                if (!listaContasReceber.get(i).getJuros().equals(0)) {
                    semJuros = listaContasReceber.get(i).getJuros();
                    listaContasReceber.get(i).setValorParcela(listaContasReceber.get(i).getValorParcela() - semJuros);
                }
                if (!listaContasReceber.get(i).getDesagio().equals(0)) {
                    semDesagio = listaContasReceber.get(i).getDesagio();
                    listaContasReceber.get(i).setValorParcela(listaContasReceber.get(i).getValorParcela() - semDesagio);
                }
                listaContasSelecionadas.add(listaContasReceber.get(i));
                valorTotal = valorTotal + listaContasReceber.get(i).getValorParcela();
            }

        }
        totalReceberLote = Formatacao.foramtarFloatString(valorTotal);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
        session.setAttribute("totalReceberLote", totalReceberLote);
        session.setAttribute("contasReceber", contasReceber);
        session.setAttribute("cliente", cliente);
        RequestContext.getCurrentInstance().openDialog("recebimentoLote", options, null);
        return "";
    }

    public void retornoDialogRecebimentoLote(SelectEvent event) {
        gerarListaContas();
        mensagem mensagem = new mensagem();
        mensagem.recebido();
        calculosContasMB.calcularTotalContasPagar();
    }

    public void retornoDialogFiltrar(SelectEvent event) {
        String sql = (String) event.getObject();
        gerarListaContaas(sql);
    }

    public void gerarListaContaas(String sql) {
        listaContasReceber = contasReceberDao.list(sql);
        if (listaContasReceber == null) {
            listaContasReceber = new ArrayList<Contasreceber>();
        }
        calcularTotal();
    }

    public String limparConsulta() {
        listaContasReceber = contasReceberDao.list(sql);
        return "";

    }

    public void cancelar(Contasreceber contasreceber) {
        List<Contasreceber> listaContasMultiplas = new ArrayList<Contasreceber>();
        for (int i = 0; i < listaContasReceber.size(); i++) {
            if (listaContasReceber.get(i).isSelecionado()) {
                listaContasMultiplas.add(listaContasReceber.get(i));
            }

        }
        if (listaContasMultiplas.isEmpty()) {
            contasreceber.setStatus("CANCELADA");
            contasReceberDao.update(contasreceber);
        } else {
            for (int i = 0; i < listaContasMultiplas.size(); i++) {
                listaContasMultiplas.get(i).setStatus("CANCELADA");
                contasReceberDao.update(listaContasMultiplas.get(i));
            }
        }
        mensagem msg = new mensagem();
        msg.cancelado();
        listaContasReceber.remove(contasreceber);
        calcularTotal();
        gerarTotalContas();
    }

    public Integer numeroCob(int contasreceber) {
        List<Historicocobranca> listahistorico = new ArrayList<>();
        String sql = "Select cp From Cobrancaparcelas cp  Where cp.contasreceber.idcontasReceber=" + contasreceber;
        Cobrancaparcelas cobrancaparcela = cobrancaParcelasDao.find(sql);
        if (cobrancaparcela != null) {
            listahistorico = historicoCobrancaDao.list("Select c From Historicocobranca c WHere c.cobranca.idcobranca=" + cobrancaparcela.getCobranca().getIdcobranca());
        }else{
           cob = 0;
           return cob;
        }
        if (listahistorico.size() > 0) {
            cob = listahistorico.size();
        } else {
            cob = 0;
        }
        return cob;

    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String cobrancas(Contasreceber contasreceber) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasreceber", contasreceber);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cobrancas", options, null);
        return "";
    }

    public void retornoDialogCobranca(SelectEvent event) {
        String msg = "";
        criarConsultaContaReceber();
        //mensagem mensagem = new mensagem();
       // mensagem.cobranca(msg);
    }

    public String informacoesVendas(Contasreceber contasreceber) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        Vendas vendas = new Vendas();
        vendas = vendasDao.find(contasreceber.getVenda());
        if (vendas.getIdvendas() != null) {
            session.setAttribute("vendas", vendas);
            RequestContext.getCurrentInstance().openDialog("cadVendas", options, null);
            return "";
        } else {
            mensagem mensagem = new mensagem();
            mensagem.naoContemVenda();
            return "";
        }
    }
    
     
    public void excluirRecebimentos(Contasreceber contasreceber){
        List<Contasreceber> listaContas = contasReceberDao.list("Select c From Contasreceber c Where c.status='RECEBIMENTO-" + contasreceber.getIdcontasReceber() + "'");
        for (int i = 0; i < listaContas.size(); i++) {
            contasReceberDao.remove(listaContas.get(i).getIdcontasReceber());
        }
    }
}
