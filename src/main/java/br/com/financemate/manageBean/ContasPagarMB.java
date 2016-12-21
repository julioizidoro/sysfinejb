package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.CpTransferenciaDao;
import br.com.financemate.dao.NomeArquivoDao;
import br.com.financemate.dao.OperacaoUsuarioDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
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

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Nomearquivo;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ContasPagarMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Date dataInicio;
    private Date dataFinal;
    private String sql;
    private Cliente cliente;
    private List<Contaspagar> listaContasPagar;
    private List<Cliente> listaCliente;
    private Boolean liberadas;
    private Boolean autorizadas;
    private String totalVencidas;
    private String totalVencer;
    private String totalVencendo;
    private String total;
    private Contaspagar contasPagar;
    private String linha;
    private String totalLiberadas;
    private List<Contaspagar> listaContasSelecionadas;
    private Date dataLiberacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cptransferencia cpTransferencia;
    private Planocontas planocontas;
    private String descricao;
    private List<Planocontas> listaPlanoContas;
    private String imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
    private List<Cptransferencia> listaTransferencia;
    @Inject
    private CalculosContasMB calculosContasMB;
    private Boolean habilitarUnidade = false;
    private String totalRestante;
    private String status;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private CpTransferenciaDao cpTransferenciaDao;
    @EJB
    private NomeArquivoDao nomeArquivoDao;
    @EJB
    private OperacaoUsuarioDao operacaoUsuarioDao;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    private Planocontatipo planocontatipo;
    private List<Planocontatipo> listaPlanoContaTipo;

    @PostConstruct
    public void init() {
        gerarListaCliente();
        desabilitarUnidade();
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
            cliente = usuarioLogadoMB.getCliente();
            gerarListaPlanoContas();
        }else{
            habilitarUnidade = false;
        }
        criarConsultaContasPagarInicial();
        gerarListaContas();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalRestante() {
        return totalRestante;
    }

    public void setTotalRestante(String totalRestante) {
        this.totalRestante = totalRestante;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public CalculosContasMB getCalculosContasMB() {
        return calculosContasMB;
    }

    public void setCalculosContasMB(CalculosContasMB calculosContasMB) {
        this.calculosContasMB = calculosContasMB;
    }

    public List<Planocontas> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public List<Cptransferencia> getListaTransferencia() {
        return listaTransferencia;
    }

    public void setListaTransferencia(List<Cptransferencia> listaTransferencia) {
        this.listaTransferencia = listaTransferencia;
    }

    public String getImagemFiltro() {
        return imagemFiltro;
    }

    public void setImagemFiltro(String imagemFiltro) {
        this.imagemFiltro = imagemFiltro;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Cptransferencia getCpTransferencia() {
        return cpTransferencia;
    }

    public void setCpTransferencia(Cptransferencia cpTransferencia) {
        this.cpTransferencia = cpTransferencia;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Boolean getLiberadas() {
        return liberadas;
    }

    public void setLiberadas(Boolean liberadas) {
        this.liberadas = liberadas;
    }

    public Boolean getAutorizadas() {
        return autorizadas;
    }

    public void setAutorizadas(Boolean autorizadas) {
        this.autorizadas = autorizadas;
    }

    public String getTotalVencidas() {
        return totalVencidas;
    }

    public void setTotalVencidas(String totalVencidas) {
        this.totalVencidas = totalVencidas;
    }

    public String getTotalVencer() {
        return totalVencer;
    }

    public void setTotalVencer(String totalVencer) {
        this.totalVencer = totalVencer;
    }

    public String getTotalVencendo() {
        return totalVencendo;
    }

    public void setTotalVencendo(String totalVencendo) {
        this.totalVencendo = totalVencendo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Contaspagar getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(Contaspagar contasPagar) {
        this.contasPagar = contasPagar;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getTotalLiberadas() {
        return totalLiberadas;
    }

    public void setTotalLiberadas(String totalLiberadas) {
        this.totalLiberadas = totalLiberadas;
    }

    public List<Contaspagar> getListaContasSelecionadas() {
        return listaContasSelecionadas;
    }

    public void setListaContasSelecionadas(List<Contaspagar> listaContasSelecionadas) {
        this.listaContasSelecionadas = listaContasSelecionadas;
    }

    public Date getDataLiberacao() {
        return dataLiberacao;
    }

    public void setDataLiberacao(Date dataLiberacao) {
        this.dataLiberacao = dataLiberacao;
    }

    public Planocontatipo getPlanocontatipo() {
        return planocontatipo;
    }

    public void setPlanocontatipo(Planocontatipo planocontatipo) {
        this.planocontatipo = planocontatipo;
    }

    public List<Planocontatipo> getListaPlanoContaTipo() {
        return listaPlanoContaTipo;
    }

    public void setListaPlanoContaTipo(List<Planocontatipo> listaPlanoContaTipo) {
        this.listaPlanoContaTipo = listaPlanoContaTipo;
    }
    
    

    public String verStatus(Contaspagar contaspagar) {
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        data = Formatacao.ConvercaoStringDataBrasil(diaData);
        if (contaspagar.getStatus().equalsIgnoreCase("CANCELADA")) {
            return "../../resources/img/bolinhaPretaS.ico";
        } else if (contaspagar.getDataVencimento().after(data)) {
            return "../../resources/img/bolaVerde.png";
        } else if (contaspagar.getDataVencimento().before(data)) {
            return "../../resources/img/bolaVermelha.png";
        } else if (contaspagar.equals(data)) {
            return "../../resources/img/bolaAmarela.png";
        }
        return "../../resources/img/bolaVerde.png";
    }

    public String imagemAutorizadas(Contaspagar contaspagar) {
        if (contaspagar.getAutorizarPagamento() == null) {
            return "../../resources/img/cancelarS.png";
        } else if (contaspagar.getAutorizarPagamento().equalsIgnoreCase("s")) {
            return "../../resources/img/confirmarS.png";
        } else {
            return "../../resources/img/cancelarS.png";
        }
    }

    public void criarConsultaContasPagarInicial() {
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
        String dataTermino = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
        setDataInicio(Formatacao.ConvercaoStringData(dataInicial));
        setDataFinal(Formatacao.ConvercaoStringData(dataTermino));
        sql = " Select v from Contaspagar v where v.dataVencimento>='" + dataInicial
                + "' and v.dataVencimento<='" + dataTermino + "' and v.contaPaga='N' ";
        if (usuarioLogadoMB.getCliente() != null) {
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        } else {
            sql = sql + "  and v.cliente.visualizacao='Operacional' ";
        }
        sql = sql + " and v.status<>'CANCELADA' order by v.dataVencimento";

    }

    public void gerarListaContas() {
        listaContasPagar = contasPagarDao.list(sql);
        if (listaContasPagar == null) {
            listaContasPagar = new ArrayList<Contaspagar>();
        }
        calcularTotal();
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c order by c.nomeFantasia");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
  
    }
 
    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String novaConta() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadContasPagar", options, null);
        return "";
    }

    public String novaContaTelaPrincipal() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadContasPagarPrincipal", options, null);
        return "";
    }

    public void retornoDialogNovo(SelectEvent event) {
        Contaspagar contaspagar = (Contaspagar) event.getObject();
        criarConsultaContasPagarInicial();
        gerarListaContas();
        if (contaspagar.getIdcontasPagar() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
        calculosContasMB.calcularTotalContasPagar();
    }

    public void retornoDialogNovoPrincipal(String valor) {
        calculosContasMB.calcularTotalContasPagar();
        calculosContasMB.habilitarDesabilitarSemCompraTelaPrincipal(valor);
        calculosContasMB.habilitarDesabilitarComCompraTelaPrincipal(valor);
        mensagem mensagem = new mensagem();
        mensagem.saveMessagem();
    }

    public String excluir() {
        List<Contaspagar> listaContasMultiplas = new ArrayList<Contaspagar>();
        for (int i = 0; i < listaContasPagar.size(); i++) {
            if (listaContasPagar.get(i).isSelecionado()) {
                listaContasMultiplas.add(listaContasPagar.get(i));
            }

        }
        if (listaContasMultiplas.isEmpty()) {
            if (contasPagar.getFormaPagamento().equalsIgnoreCase("Transferencia")) {
                excluindoTransferencia(contasPagar);
            }

            excluirNomeArquivo(contasPagar.getIdcontasPagar());
            contasPagarDao.remove(contasPagar.getIdcontasPagar());
        } else {
            for (int i = 0; i < listaContasMultiplas.size(); i++) {
                if (listaContasMultiplas.get(i).getFormaPagamento().equalsIgnoreCase("Transferencia")) {
                    excluindoTransferencia(listaContasMultiplas.get(i));
                }
                excluirNomeArquivo(listaContasMultiplas.get(i).getIdcontasPagar());
                contasPagarDao.remove(listaContasMultiplas.get(i).getIdcontasPagar());
            }
        }
        gerarListaContas();
        mensagem msg = new mensagem();
        msg.excluiMessagem();
        return "";
    }

    public void autorizarPagamento(Contaspagar contaspagar) {
        if (contaspagar.getAutorizarPagamento().equalsIgnoreCase("S")) {
            contaspagar.setAutorizarPagamento("N");
            if (contaspagar.getIdcontasPagar() != null) {
                Operacaousuairo operacaousuairo = new Operacaousuairo();
                operacaousuairo.setContaspagar(contaspagar);
                operacaousuairo.setData(new Date());
                operacaousuairo.setTipooperacao("Usuário Desautorizou");
                operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
                operacaoUsuarioDao.update(operacaousuairo);
            }
            mensagem mensagem = new mensagem();
            mensagem.desautorizar();
        }else{
            contaspagar.setAutorizarPagamento("S");
            contaspagar = contasPagarDao.update(contaspagar);
            if (contaspagar.getIdcontasPagar() != null) {
                Operacaousuairo operacaousuairo = new Operacaousuairo();
                operacaousuairo.setContaspagar(contaspagar);
                operacaousuairo.setData(new Date());
                operacaousuairo.setTipooperacao("Usuário Autorizou");
                operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
                operacaoUsuarioDao.update(operacaousuairo);
            }
            mensagem mensagem = new mensagem();
            mensagem.autorizar();
        }
    }

    public void novoFiltro() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("filtrarConsContaPagar", options, null);
    }

    public String editar(Contaspagar contaspagar) {
        if (contaspagar != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contapagar", contaspagar);
            session.setAttribute("cptransferencia", cpTransferencia);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadContasPagar", options, null);
        }
        return "";
    }

    public String novaImpressao() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("imprimir", options, null);
        return "";
    }

    public String novaLiberacao() {
        totalLiberadas = "0.00";
        dataLiberacao = new Date();
        float valorTotal = 0.0f;
        listaContasSelecionadas = new ArrayList<Contaspagar>();
        for (int i = 0; i < listaContasPagar.size(); i++) {
            if (listaContasPagar.get(i).isSelecionado()) {
                listaContasSelecionadas.add(listaContasPagar.get(i));
                valorTotal = valorTotal + listaContasPagar.get(i).getValor();
            }

        }
        totalLiberadas = Formatacao.foramtarFloatString(valorTotal);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
        session.setAttribute("totalLiberadas", totalLiberadas);
        session.setAttribute("contasPagar", contasPagar);
        RequestContext.getCurrentInstance().openDialog("liberarContasPagar", options, null);
        return "";
    }

    public void filtrar() {
        sql = "Select v from Contaspagar v where ";
        if (liberadas) {
            sql = sql + " v.contaPaga='S' and ";
        } else {
            sql = sql + " v.contaPaga='N' and ";

        }
        if (autorizadas) {
            sql = sql + " v.autorizarPagamento='S' and ";
        }
        if (cliente != null) {
            sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente();
            if (!descricao.equalsIgnoreCase("")) {
                sql = sql + " and ";
            } else if (planocontas != null) {
                sql = sql + " and ";
            } else if (status != null && status != "") {
                sql = sql + " and ";
            } else if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }
        } else {
            sql = sql + " v.cliente.visualizacao='Operacional'";
            if (!descricao.equalsIgnoreCase("")) {
                sql = sql + " and ";
            } else if (planocontas != null) {
                sql = sql + " and ";
            } else if (status != null && status != "") {
                sql = sql + " and ";
            } else if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }
        }
        if (!descricao.equalsIgnoreCase("")) {
            sql = sql + " v.descricao like '%" + descricao + "%'";
            if (planocontas != null) {
                sql = sql + " and ";
            } else if (status != null && status != "") {
                sql = sql + " and ";
            } else if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }
        }
        if (planocontas != null) {
            sql = sql + " v.planocontas.idplanoContas=" + planocontas.getIdplanoContas();
            if (status != null && status != "") {
                sql = sql + " and ";
            } else if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }
        }
        if (status != "" && status != null) {
            if (status.equalsIgnoreCase("Canceladas")) {
                sql = sql + " v.status='CANCELADA' ";
            } else if (status.equalsIgnoreCase("Ativo")) {
                sql = sql + " v.status='Ativo' ";
            }
            if ((dataInicio != null) && (dataFinal != null)) {
                sql = sql + " and ";
            }
        }
        if ((dataInicio != null) && (dataFinal != null)) {
            if (liberadas) {
                sql = sql + "v.dataLiberacao>='" + Formatacao.ConvercaoDataSql(dataInicio)
                        + "' and v.dataLiberacao<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' order by v.dataLiberacao";
            } else {
                sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicio)
                        + "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal)
                        + "' order by v.dataVencimento";
            }
        }
        gerarListaContas();
        RequestContext.getCurrentInstance().closeDialog(sql);
    }



    public void retornoDialogFiltrar(SelectEvent event) {
        String sql = (String) event.getObject();
        if (sql.length() > 1) {
            gerarListaContaas(sql);  
        }
    }

    public void gerarListaContaas(String sql) {
        listaContasPagar = contasPagarDao.list(sql);
        if (listaContasPagar == null) {
            listaContasPagar = new ArrayList<Contaspagar>();
        }
    }

    public String coresFiltrar() {
        if (imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVerde.ico")) {
            novoFiltro();
            imagemFiltro = "../../resources/img/iconefiltrosVermelho.ico";
        } else if (imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVermelho.ico")) {
            limparConsulta();
            criarConsultaContasPagarInicial();
            gerarListaContas();
            imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
        }
        return "";
    }

    public String limparConsulta() {
        listaContasPagar = contasPagarDao.list(sql);
        setLiberadas(false);
        setAutorizadas(false);
        return "";

    }

    public void retornoDialogLiberar(SelectEvent event) {
        mensagem msg = new mensagem();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaContasSelecionadas = (List<Contaspagar>) session.getAttribute("listaContasSelecionadas");
        session.removeAttribute("listaSelecionadas");
        for (int i = 0; i < listaContasSelecionadas.size(); i++) {
            if (listaContasSelecionadas.get(i).getContaPaga().equalsIgnoreCase("S")) {
                msg.liberar();
            } else {
                msg.naoLiberar();
            }
        }

        gerarListaContas();
        calculosContasMB.calcularTotalContasPagar();
    }

    public String operacoesUsuario(Contaspagar contaspagar) {
        if (contaspagar != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 600);
            options.put("closable", false);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contapagar", contaspagar);
            RequestContext.getCurrentInstance().openDialog("operacoesUsuario", options, null);
        }
        return "";
    }

    public String consultarArquivo(Contaspagar contaspagar) {
        if (contaspagar != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 480);
            options.put("closable", false);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contapagar", contaspagar);
            RequestContext.getCurrentInstance().openDialog("visualizarArquivo", options, null);
        }
        return "";
    }

    public void calcularTotal() {
        float vencida = 0.0f;
        float vencendo = 0.0f;
        float vencer = 0.0f;
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        for (int i = 0; i < listaContasPagar.size(); i++) {
            String vencData = Formatacao.ConvercaoDataPadrao(listaContasPagar.get(i).getDataVencimento());
            if (diaData.equalsIgnoreCase(vencData)) {
                vencendo = vencendo + listaContasPagar.get(i).getValor();
            } else if (listaContasPagar.get(i).getDataVencimento().before(data)) {
                vencida = vencida + listaContasPagar.get(i).getValor();
            } else if (listaContasPagar.get(i).getDataVencimento().after(data)) {
                vencer = vencer + listaContasPagar.get(i).getValor();
            }
        }
        setTotalVencidas(Formatacao.foramtarFloatString(vencida));
        setTotalVencendo(Formatacao.foramtarFloatString(vencendo));
        setTotalVencer(Formatacao.foramtarFloatString(vencer));
        setTotal(Formatacao.foramtarFloatString(vencida + vencer + vencendo));
        setTotalRestante(Formatacao.foramtarFloatString(vencer - vencendo));
    }

    public String excluirNomeArquivo(int idConta) {
        Nomearquivo nomearquivo = new Nomearquivo();
        nomearquivo = nomeArquivoDao.find("SELECT n FROM Nomearquivo n where n.contaspagar.idcontasPagar=" + idConta);
        if (nomearquivo == null) {
            return "";
        } else {
            nomeArquivoDao.remove(nomearquivo.getIdnomearquivo());
            nomearquivo = new Nomearquivo();
        }
        return "";
    }

    public void cancelar(Contaspagar contaspagar) {
        List<Contaspagar> listaContasMultiplas = new ArrayList<Contaspagar>();
        for (int i = 0; i < listaContasPagar.size(); i++) {
            if (listaContasPagar.get(i).isSelecionado()) {
                listaContasMultiplas.add(listaContasPagar.get(i));
            }

        }
        if (listaContasMultiplas.isEmpty()) {
            contaspagar.setStatus("CANCELADA");
            contasPagarDao.update(contaspagar);
        } else {
            for (int i = 0; i < listaContasMultiplas.size(); i++) {
                listaContasMultiplas.get(i).setStatus("CANCELADA");
                contasPagarDao.update(listaContasMultiplas.get(i));
            }
        }
        mensagem msg = new mensagem();
        msg.cancelado();
        gerarListaContas();
    }

    public String excluindoTransferencia(Contaspagar conta) {
        String sql = "Select c from Cptransferencia c join Contaspagar p on c.contaspagar.idcontasPagar= p.idcontasPagar"
                + " where c.contaspagar.idcontasPagar=" + conta.getIdcontasPagar();
        listaTransferencia = cpTransferenciaDao.list(sql);
        if (listaTransferencia != null) {
            for (int i = 0; i < listaTransferencia.size(); i++) {
                cpTransferenciaDao.remove(listaTransferencia.get(i).getIdcptransferencia());
            }
        }

        return "";
    }
    
    
    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    
    
    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<Planocontatipo>();
            }
            listaPlanoContas = new ArrayList<Planocontas>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoContas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public void cancelarFiltro() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        RequestContext.getCurrentInstance().closeDialog("");
    }
}
