package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.util.Formatacao;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named
@ViewScoped
public class ConciliacaoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Banco banco;
    private List<Movimentobanco> listaLacamentos;
    private List<TransacaoBean> listaTransacao;
    private Date dataInicial;
    private Date dataFinal;
    private UploadedFile arquivo;
    private List<ConciliarBean> listaConciliacaoBancaria;
    private String nomeBotaoConciliar = "Conciliar";
    private List<TransacaoBean> listaTransacaoNaoConciliado;
    private List<Movimentobanco> listaOutrosNaoConciliado;
    private Integer sizeConciliada;
    private TransacaoBean transacaoBean;
    private Movimentobanco outroslancamentos;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private ClienteDao clienteDao;
    private Planocontas planocontas;
    private List<Planocontas> listaPlanoContas;
    private List<Planocontatipo> listaPlanoContaTipo;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    private List<Banco> listaBanco;
    private String agencia;
    private String conta;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaTransacao = (List<TransacaoBean>) session.getAttribute("listaTransacao");
        listaLacamentos = (List<Movimentobanco>) session.getAttribute("listaLancamentos");
        banco = (Banco) session.getAttribute("banco");
        cliente = (Cliente) session.getAttribute("cliente");
        dataInicial = (Date) session.getAttribute("dataInicial");
        dataFinal = (Date) session.getAttribute("dataFinal");
        session.removeAttribute("dataInicial");
        session.removeAttribute("dataFinal");
        session.removeAttribute("cliente");
        session.removeAttribute("listaTransacao");
        session.removeAttribute("listaLancamentos");
        session.removeAttribute("banco");
        gerarListaPlanoContas();
    }

    public String getNomeBotaoConciliar() {
        return nomeBotaoConciliar;
    }

    public void setNomeBotaoConciliar(String nomeBotaoConciliar) {
        this.nomeBotaoConciliar = nomeBotaoConciliar;
    }

    public List<TransacaoBean> getListaTransacaoNaoConciliado() {
        return listaTransacaoNaoConciliado;
    }

    public void setListaTransacaoNaoConciliado(List<TransacaoBean> listaTransacaoNaoConciliado) {
        this.listaTransacaoNaoConciliado = listaTransacaoNaoConciliado;
    }

    public List<Movimentobanco> getListaOutrosNaoConciliado() {
        return listaOutrosNaoConciliado;
    }

    public void setListaOutrosNaoConciliado(List<Movimentobanco> listaOutrosNaoConciliado) {
        this.listaOutrosNaoConciliado = listaOutrosNaoConciliado;
    }

    public Integer getSizeConciliada() {
        return sizeConciliada;
    }

    public void setSizeConciliada(Integer sizeConciliada) {
        this.sizeConciliada = sizeConciliada;
    }

    public TransacaoBean getTransacaoBean() {
        return transacaoBean;
    }

    public void setTransacaoBean(TransacaoBean transacaoBean) {
        this.transacaoBean = transacaoBean;
    }

    public Movimentobanco getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Movimentobanco outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public List<ConciliarBean> getListaConciliacaoBancaria() {
        return listaConciliacaoBancaria;
    }

    public void setListaConciliacaoBancaria(List<ConciliarBean> listaConciliacaoBancaria) {
        this.listaConciliacaoBancaria = listaConciliacaoBancaria;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Movimentobanco> getListaLacamentos() {
        return listaLacamentos;
    }

    public void setListaLacamentos(List<Movimentobanco> listaLacamentos) {
        this.listaLacamentos = listaLacamentos;
    }

    public List<TransacaoBean> getListaTransacao() {
        return listaTransacao;
    }

    public void setListaTransacao(List<TransacaoBean> listaTransacao) {
        this.listaTransacao = listaTransacao;
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

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }


    public void carregarArquivo(FileUploadEvent e) {
        listaConciliacaoBancaria = null;
        arquivo = e.getFile();
        File arq = new File(arquivo.getFileName());
        InputStream file = null;
        try {
            file = arquivo.getInputstream();
        } catch (IOException ex) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        LerOFXBean ler = new LerOFXBean();
        ler.iniciarLeitura(file);
        listaTransacao = ler.getListaTransacao();
        if ((ler.getAgencia() != null) && (ler.getConta() != null)) {
            banco = consultarBanco(ler.getAgencia(), ler.getConta());
            agencia = ler.getAgencia();
            conta = ler.getConta();
        }
        if (banco != null) {
            cliente = banco.getCliente();
            dataInicial = listaTransacao.get(0).getData();
            dataFinal = listaTransacao.get(listaTransacao.size() - 1).getData();
            carregarOutrosLancamentos();
            if (listaLacamentos != null) {
                conciliar();
            }
        }
        gerarListaCliente();
        gerarListaBanco();
        gerarListaPlanoContas();
    }

    public Banco consultarBanco(String agencia, String conta) {
        String sql = "select b from Banco b where b.agencia='" + agencia + "' and b.conta like '" + conta + "%'";
        List<Banco> listabanco = bancoDao.list(sql);
        if (listabanco == null) {
            return null;
        }
        return listabanco.get(0);
    }

    public void carregarOutrosLancamentos() {
        String sql = "select o from Movimentobanco o where o.dataCompensacao>='"
                + Formatacao.ConvercaoDataSql(dataInicial) + "' and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and o.banco.idbanco=" + banco.getIdbanco() + " order by o.dataCompensacao";
        listaLacamentos = outrosLancamentosDao.list(sql);
    }

    public void conciliar() {
        for (int i = 0; i < listaTransacao.size(); i++) {
            verficarLancamentos(listaTransacao.get(i));
        }
        sizeConciliada = listaConciliacaoBancaria.size();
        for (int i = 0; i < listaTransacao.size(); i++) {
            gerarListaTransacaoNaoConciliada(listaTransacao.get(i));
        }
        gerarListaOutrosLancamentosNaoConciliado();
        for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
            if (listaConciliacaoBancaria.get(i).getOutroslancamentos() == null) {
                listaConciliacaoBancaria.get(i).setOutroslancamentos(new Movimentobanco());
                listaConciliacaoBancaria.get(i).getOutroslancamentos().setSelecionado(false);
            }
        }
    }

    public void verficarLancamentos(TransacaoBean transacao) {
        ConciliarBean cb = new ConciliarBean();
        if (listaConciliacaoBancaria == null) {
            listaConciliacaoBancaria = new ArrayList<>();
        }
        for (int j = 0; j < listaLacamentos.size(); j++) {
            Float valor;
            String dataTransacao = Formatacao.ConvercaoDataPadrao(transacao.getData());
            String dataLancamento = Formatacao.ConvercaoDataPadrao(listaLacamentos.get(j).getDataCompensacao());
            if (transacao.getTipo().equalsIgnoreCase("DEBIT")) {
                valor = transacao.getValorSaida();
                if ((listaLacamentos.get(j).getValorSaida().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))) {
                    listaLacamentos.get(j).setConciliacao(transacao.getId());
                    listaLacamentos.get(j).setConciliada(true);
                    transacao.setConciliada(true);
                    cb = new ConciliarBean();
                    cb.setOutroslancamentos(listaLacamentos.get(j));
                    cb.setTransacao(transacao);
                    listaConciliacaoBancaria.add(cb);
                }
            } else if (transacao.getTipo().equalsIgnoreCase("CREDIT")) {
                valor = transacao.getValorEntrada();
                if ((listaLacamentos.get(j).getValorEntrada().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))) {
                    listaLacamentos.get(j).setConciliacao(transacao.getId());
                    listaLacamentos.get(j).setConciliada(true);
                    transacao.setConciliada(true);
                    cb = new ConciliarBean();
                    cb.setOutroslancamentos(listaLacamentos.get(j));
                    cb.setTransacao(transacao);
                    listaConciliacaoBancaria.add(cb);
                }
            }

        }

    }

    public String estiloDaTabelaTransacao(TransacaoBean transacao) {
        String color;
        if (transacao.getConciliada() == true) {
            color = "color:black;";
        } else {
            color = "color:red;font-weight:bold;";
        }
        return color;
    }

    public String estiloDaTabelaOutrosLancamentos(Movimentobanco outros) {
        String color;
        if (outros.getConciliada() == true) {
            color = "color:gren";
        } else {
            color = "color:red;font-weight:bold;";
        }
        return color;
    }

    public void gerarListaTransacaoNaoConciliada(TransacaoBean transacao) {
        ConciliarBean conciliacaoBean = new ConciliarBean();
        if (listaTransacaoNaoConciliado == null) {
            listaTransacaoNaoConciliado = new ArrayList<>();
        }
        if (transacao.getConciliada() == false) {
            listaTransacaoNaoConciliado.add(transacao);
            conciliacaoBean.setTransacao(transacao);
            listaConciliacaoBancaria.add(conciliacaoBean);
        }

    }

    public void gerarListaOutrosLancamentosNaoConciliado() {
        for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
            if (!listaConciliacaoBancaria.get(i).getTransacao().getConciliada()) {
                for (int j = 0; j < listaLacamentos.size(); j++) {
                    if (listaLacamentos.get(j).getConciliada() == false) {
                        if (listaConciliacaoBancaria.get(i).getTransacao().getDescricao().equalsIgnoreCase(listaLacamentos.get(j).getDescricao())) {
                            listaLacamentos.get(j).setConciliada(false);
                            listaConciliacaoBancaria.get(i).setOutroslancamentos(listaLacamentos.get(j));
                        }
                    }
                }
            }
        }
    }

    public String novaConciliacao(ConciliarBean conciliacao) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("conciliacao", conciliacao);
        session.setAttribute("listaTransacao", listaTransacao);
        session.setAttribute("listaLancamentos", listaLacamentos);
        session.setAttribute("banco", banco);
        session.setAttribute("dataInicial", dataInicial);
        session.setAttribute("dataFinal", dataFinal);
        listaConciliacaoBancaria.remove(conciliacao);
        if (conciliacao != null) {
            Movimentobanco outros = conciliacao.getOutroslancamentos();
            if (outros == null) {
                outros = new Movimentobanco();
            }
            outros.setValorEntrada(conciliacao.getTransacao().getValorEntrada());
            outros.setValorSaida(conciliacao.getTransacao().getValorSaida());
            outros.setDataCompensacao(conciliacao.getTransacao().getData());
            outros.setDescricao(conciliacao.getTransacao().getDescricao());
            outros.setUsuario(usuarioLogadoMB.getUsuario());
            outros.setDataVencimento(conciliacao.getTransacao().getData());
            outros.setTipoDocumento(conciliacao.getTransacao().getTipo());
            outros.setDataRegistro(new Date());
            outros.setIdcontaspagar(0);
            outros.setIdcontasreceber(0);
            if (banco == null) {
               List<Banco> lista = bancoDao.list("select b from Banco b where b.agencia='" + agencia + "' and b.conta like '" + conta + "%'");
               outros.setBanco(lista.get(0));
               outros.setCliente(lista.get(0).getCliente());
            }else{
                outros.setBanco(banco);
                outros.setCliente(banco.getCliente());
            }
            if (outros.getPlanocontas() == null) {
                outros.setPlanocontas(planocontas);
            }
            outrosLancamentosDao.update(outros);
        }
        listaConciliacaoBancaria.add(conciliacao);
        conciliar();
        mensagem mensagem = new mensagem();
        mensagem.faltaInformacao("Conciliado com sucesso");
        return "";

    }

    public void retornoDialogConciliado(SelectEvent e) {
        listaConciliacaoBancaria = null;
        carregarOutrosLancamentos();
        conciliar();
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }
    }

    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where tipoplanocontas.idtipoplanocontas="
                    + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<>();
            }
            listaPlanoContas = new ArrayList<>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoContas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
            if (listaPlanoContas.size() > 0) {
                planocontas = listaPlanoContas.get(0);
            }
        } catch (Exception e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
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

    public boolean retornoValorEntrada(TransacaoBean transacaoBean) {
        if (transacaoBean.getValorEntrada() > 0f) {
            return true;
        } else {
            return false;
        }
    }

    public boolean retornoValorSaida(TransacaoBean transacaoBean) {
        if (transacaoBean.getValorEntrada() > 0f) {
            return false;
        } else {
            return true;
        }
    }

    public String novoLancamento(TransacaoBean transacaoBean) {
        if (transacaoBean != null) {
            Movimentobanco outroslancamentos = new Movimentobanco();
            outroslancamentos.setDescricao(transacaoBean.getDescricao());
            outroslancamentos.setDataCompensacao(transacaoBean.getData());
            outroslancamentos.setValorEntrada(transacaoBean.getValorEntrada());
            outroslancamentos.setValorSaida(transacaoBean.getValorSaida());
            if (banco != null) {
                outroslancamentos.setBanco(banco);
                outroslancamentos.setCliente(banco.getCliente());
            }
            if (planocontas != null) {
                outroslancamentos.setPlanocontas(planocontas);
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("outroslancamentos", outroslancamentos);
            session.setAttribute("cliente", cliente);
            session.setAttribute("banco", banco);
            session.setAttribute("dataInicial", dataInicial);
            session.setAttribute("dataFinal", dataFinal);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadOutrosConciliacao", options, null);
        }
        return "";
    }

    public void retornoDialogOutros(SelectEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("cliente");
        session.removeAttribute("banco");
        dataInicial = (Date) session.getAttribute("dataInicial");
        dataFinal = (Date) session.getAttribute("dataFinal");
        session.removeAttribute("dataInicial");
        session.removeAttribute("dataFinal");
        Movimentobanco outros = (Movimentobanco) event.getObject();
        if (outros.getIdmovimentobanco() != null) {
            mensagem mensagem = new mensagem();
            mensagem.faltaInformacao("Salvo com sucesso");
            carregarOutrosLancamentos();
            conciliar();
        }
    }
    
    
    public String consultarLancamentos(ConciliarBean conciliacao) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("closable", false);
        session.setAttribute("conciliacao", conciliacao);
        session.setAttribute("listaTransacao", listaTransacao);
        session.setAttribute("listaLancamentos", listaLacamentos);
        session.setAttribute("banco", banco);
        session.setAttribute("dataInicial", dataInicial);
        session.setAttribute("dataFinal", dataFinal);
        RequestContext.getCurrentInstance().openDialog("consOutrosConciliacao", options, null);
        return "";
    }
    
    
    public void retornoDialogBusca(SelectEvent event){
        Movimentobanco outros = (Movimentobanco) event.getObject();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaTransacao = (List<TransacaoBean>) session.getAttribute("listaTransacao");
        listaLacamentos = (List<Movimentobanco>) session.getAttribute("listaLancamentos");
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("listaTransacao");
        session.removeAttribute("listaLancamentos");
        session.removeAttribute("banco");
        dataInicial = (Date) session.getAttribute("dataInicial");
        dataFinal = (Date) session.getAttribute("dataFinal");
        session.removeAttribute("dataInicial");
        session.removeAttribute("dataFinal");
        if (outros.getIdmovimentobanco() != null) {
            mensagem mensagem = new mensagem();
            mensagem.faltaInformacao("Conciliado com sucesso");
            carregarOutrosLancamentos();
            conciliar();
        }
    }
    

}
