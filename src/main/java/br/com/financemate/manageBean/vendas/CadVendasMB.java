package br.com.financemate.manageBean.vendas;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.FormaPagamentoDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.dao.ProdutoDao;
import br.com.financemate.dao.VendasDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.CadContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Emissaonota;
import br.com.financemate.model.Formapagamento;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.model.Produto;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadVendasMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Vendas vendas;
    private Boolean habilitarUnidade;
    private Produto produto;
    private List<Produto> listaProduto;
    private Planocontas planocontas;
    private List<Planocontas> listaPlanocontas;
    private String TipoDocumento;
    private Formapagamento formapagamento;
    private Boolean habilitarCampos = true;
    private Float valorAddConta;
    private String competencia;
    private Banco banco;
    private Float saldoRestante;
    private Emissaonota emissaonota;
    private List<Formapagamento> listaFormaPagamento;
    private Float valorPagarReceber;
    private List<Formapagamento> listaSelecionadosFormaPagamentos;
    private String corPagarReceber = "color:black;";
    private List<VendasSystmBean> listaVendasSystm;
    private VendasSystmBean vendasSystmBean;
    private List<ListaVendasSystmBean> listaImportada;
    private Date dataInicial;
    private Date dataFinal;
    private Boolean importadoSystm = false;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private FormaPagamentoDao formaPagamentoDao;
    @EJB
    private PlanoContasDao planoContasDao;
    @EJB
    private ProdutoDao produtoDao;
    @EJB
    private VendasDao vendasDao;
    private Cliente clienteImportacao;
    private Planocontatipo planocontatipo;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    private List<Planocontatipo> listaPlanoContaTipo;
    private Formapagamento formapagamentoAnterior;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        cliente = (Cliente) session.getAttribute("cliente");
        produto = (Produto) session.getAttribute("produto");
        formapagamento = (Formapagamento) session.getAttribute("formapagamento");
        listaFormaPagamento = (List<Formapagamento>) session.getAttribute("listaFormaPagamento");
        valorPagarReceber = (Float) session.getAttribute("valorPagarReceber");
        saldoRestante = (Float) session.getAttribute("saldoRestante");
        corPagarReceber = (String) session.getAttribute("corPagarReceber");
        listaVendasSystm = (List<VendasSystmBean>) session.getAttribute("listaVendasSystm");
        listaImportada = (List<ListaVendasSystmBean>) session.getAttribute("listaImportada");
        importadoSystm = (Boolean) session.getAttribute("importadoSystm");
        session.removeAttribute("formapagamento");
        session.removeAttribute("saldoRestante");
        session.removeAttribute("corPagarReceber");
        session.removeAttribute("listaVendasSystm");
        session.removeAttribute("listaImportada");
        gerarListaCliente();
        if (vendas == null) {
            vendas = new Vendas();
            vendas.setValorDesconto(0f);
            vendas.setValorBruto(0f);
            vendas.setValorJuros(0f);
        } else {
            if (cliente == null) {
                cliente = vendas.getCliente();
            } else {
                setandoValoresEmissaoNota();
            }
            gerarListaProduto();
            if (produto == null) {
                produto = vendas.getProduto();
            }
            planocontas = vendas.getPlanocontas();
            if (vendas.getIdvendas() != null) {
                ConsultarEmissaoNota();
            }
            gerarListaFormaPagamento();
        }
        if (emissaonota == null) {
            emissaonota = new Emissaonota();
        }
        if (formapagamento == null) {
            formapagamento = new Formapagamento();
        } else {
            TipoDocumento = formapagamento.getTipoDocumento();
        }
        if (importadoSystm == null) {
            importadoSystm = false;
        }
        if (cliente != null) {
            gerarListaPlanoContas();
        }
        desabilitarUnidade();
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            cliente = clienteDao.find(usuarioLogadoMB.getUsuario().getCliente());
            gerarListaProduto();
        }
    }

    public List<Formapagamento> getListaSelecionadosFormaPagamentos() {
        return listaSelecionadosFormaPagamentos;
    }

    public void setListaSelecionadosFormaPagamentos(List<Formapagamento> listaSelecionadosFormaPagamentos) {
        this.listaSelecionadosFormaPagamentos = listaSelecionadosFormaPagamentos;
    }

    public Float getValorPagarReceber() {
        return valorPagarReceber;
    }

    public void setValorPagarReceber(Float valorPagarReceber) {
        this.valorPagarReceber = valorPagarReceber;
    }

    public List<Formapagamento> getListaFormaPagamento() {
        return listaFormaPagamento;
    }

    public void setListaFormaPagamento(List<Formapagamento> listaFormaPagamento) {
        this.listaFormaPagamento = listaFormaPagamento;
    }

    public Emissaonota getEmissaonota() {
        return emissaonota;
    }

    public void setEmissaonota(Emissaonota emissaonota) {
        this.emissaonota = emissaonota;
    }

    public Float getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(Float saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Float getValorAddConta() {
        return valorAddConta;
    }

    public void setValorAddConta(Float valorAddConta) {
        this.valorAddConta = valorAddConta;
    }

    public Boolean getHabilitarCampos() {
        return habilitarCampos;
    }

    public void setHabilitarCampos(Boolean habilitarCampos) {
        this.habilitarCampos = habilitarCampos;
    }

    public Formapagamento getFormapagamento() {
        return formapagamento;
    }

    public void setFormapagamento(Formapagamento formapagamento) {
        this.formapagamento = formapagamento;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public Planocontas getPlanocontas() {
        return planocontas;
    }

    public void setPlanocontas(Planocontas planocontas) {
        this.planocontas = planocontas;
    }

    public List<Planocontas> getListaPlanocontas() {
        return listaPlanocontas;
    }

    public void setListaPlanocontas(List<Planocontas> listaPlanocontas) {
        this.listaPlanocontas = listaPlanocontas;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
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

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
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

    public String getCorPagarReceber() {
        return corPagarReceber;
    }

    public void setCorPagarReceber(String corPagarReceber) {
        this.corPagarReceber = corPagarReceber;
    }

    public VendasSystmBean getVendasSystmBean() {
        return vendasSystmBean;
    }

    public void setVendasSystmBean(VendasSystmBean vendasSystmBean) {
        this.vendasSystmBean = vendasSystmBean;
    }

    public void setListaVendasSystm(List<VendasSystmBean> listaVendasSystm) {
        this.listaVendasSystm = listaVendasSystm;
    }

    public List<ListaVendasSystmBean> getListaImportada() {
        return listaImportada;
    }

    public void setListaImportada(List<ListaVendasSystmBean> listaImportada) {
        this.listaImportada = listaImportada;
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

    public Boolean getImportadoSystm() {
        return importadoSystm;
    }

    public void setImportadoSystm(Boolean importadoSystm) {
        this.importadoSystm = importadoSystm;
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

    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public FormaPagamentoDao getFormaPagamentoDao() {
        return formaPagamentoDao;
    }

    public void setFormaPagamentoDao(FormaPagamentoDao formaPagamentoDao) {
        this.formaPagamentoDao = formaPagamentoDao;
    }

    public PlanoContasDao getPlanoContasDao() {
        return planoContasDao;
    }

    public void setPlanoContasDao(PlanoContasDao planoContasDao) {
        this.planoContasDao = planoContasDao;
    }

    public ProdutoDao getProdutoDao() {
        return produtoDao;
    }

    public void setProdutoDao(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public VendasDao getVendasDao() {
        return vendasDao;
    }

    public void setVendasDao(VendasDao vendasDao) {
        this.vendasDao = vendasDao;
    }

    public Cliente getClienteImportacao() {
        return clienteImportacao;
    }

    public void setClienteImportacao(Cliente clienteImportacao) {
        this.clienteImportacao = clienteImportacao;
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

    public void gerarListaProduto() {
        if (cliente != null) {
            listaProduto = produtoDao.list("Select p From Produto p Where p.cliente.idcliente=" + cliente.getIdcliente());
            if (listaProduto == null) {
                listaProduto = new ArrayList<Produto>();
            }

        } else {
            listaProduto = new ArrayList<Produto>();
        }
    }

    public String backOffice() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        session.setAttribute("corPagarReceber", corPagarReceber);
        session.setAttribute("importadoSystm", importadoSystm);
        return "cadBackOffice";
    }

    public String dadosVenda() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        session.setAttribute("corPagarReceber", corPagarReceber);
        session.setAttribute("importadoSystm", importadoSystm);
        return "cadVendas";
    }

    public String adicionarConta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        if (corPagarReceber == null) {
            corPagarReceber = "color:red;";
        }
        session.setAttribute("corPagarReceber", corPagarReceber);
        return "adicionarConta";
    }

    public String recebimento() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        if (listaFormaPagamento == null) {
            listaFormaPagamento = new ArrayList<Formapagamento>();
        }
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
        session.setAttribute("listaFormaPagamento", listaFormaPagamento);
        session.setAttribute("corPagarReceber", corPagarReceber);
        session.setAttribute("importadoSystm", importadoSystm);
        return "cadRecebimento";
    }


    public String nomeConta() {
        if (corPagarReceber.equalsIgnoreCase("color:red;")) {
            return "Lançar Conta a Pagar";
        } else if (corPagarReceber.equalsIgnoreCase("color:blue;")) {
            return "Lançar Conta a Receber";
        } else {
            return "Valor Zerado";
        }
    }

    public String voltarCadastro() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("corPagarReceber", corPagarReceber);
        return "cadBackOffice";
    }

    public String formaPagamento() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("saldoRestante", saldoRestante);
        session.setAttribute("corPagarReceber", corPagarReceber);
        session.setAttribute("importadoSystm", importadoSystm);
        return "lancaFormaPagamento";
    }

    public String notaFiscal() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
        session.setAttribute("corPagarReceber", corPagarReceber);
        session.setAttribute("importadoSystm", importadoSystm);
        return "notaFiscal";
    }

    public Boolean desabilitarCampos() {
        if (TipoDocumento.equalsIgnoreCase("Boleto")) {
            habilitarCampos = false;
            return habilitarCampos;
        }
        habilitarCampos = true;
        return habilitarCampos;
    }

    public void calculoTotalVenda() {
        if (vendas.getValorBruto() == null) {
            vendas.setValorBruto(0f);
        }
        if (vendas.getValorDesconto() == null) {
            vendas.setValorDesconto(0f);
        }
        if (vendas.getValorJuros() == null) {
            vendas.setValorJuros(0f);
        }
        vendas.setValorLiquido((vendas.getValorBruto() - vendas.getValorDesconto()) + vendas.getValorJuros());
        vendas.setLiquidoReceber(vendas.getValorLiquido());
        if (vendas.getComissaoLiquidaTotal() == null) {
            vendas.setComissaoLiquidaTotal(0f);
        }
        if (vendas.getValorPagoFornecedor() == null) {
            vendas.setValorPagoFornecedor(0f);
        }
        valorPagarReceber = vendas.getValorBruto() - (vendas.getComissaoLiquidaTotal() + vendas.getValorPagoFornecedor());
    }

    public void calculoValoresBackOffice() {
        if (vendas.getValorBruto() == null) {
            vendas.setValorBruto(0f);
        }
        if (vendas.getComissaoLiquidaTotal() == null) {
            vendas.setComissaoLiquidaTotal(0f);
        }
        if (vendas.getComissaoFuncionarios() == null) {
            vendas.setComissaoFuncionarios(0f);
        }
        if (vendas.getComissaoTerceiros() == null) {
            vendas.setComissaoTerceiros(0f);
        }
        if (vendas.getValorPagoFornecedor() == null) {
            vendas.setValorPagoFornecedor(0f);
        }
        if (vendas.getDespesasFinanceiras() == null) {
            vendas.setDespesasFinanceiras(0f);
        }
        if (vendas.getValorDesconto() == null) {
            vendas.setValorDesconto(0f);
        }

        vendas.setLiquidoVendas(vendas.getComissaoLiquidaTotal() - (vendas.getValorDesconto() + vendas.getDespesasFinanceiras() + vendas.getComissaoFuncionarios() + vendas.getComissaoTerceiros()));
        valorPagarReceber = vendas.getValorBruto() - (vendas.getComissaoLiquidaTotal() + vendas.getValorPagoFornecedor());
        vendas.setLiquidoReceber(vendas.getValorLiquido() - vendas.getValorPagoFornecedor());
        escolherCorPagarReceber();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        session.setAttribute("vendas", vendas);
        session.setAttribute("importadoSystm", importadoSystm);
    }

    public void escolherCorPagarReceber() {
        if (valorPagarReceber == null) {
            valorPagarReceber = 0f;
        }
        if (valorPagarReceber > 0) {
            corPagarReceber = "color:red;";
        } else if (valorPagarReceber < 0) {
            valorPagarReceber = valorPagarReceber * (-1);
            corPagarReceber = "color:blue;";
        } else {
            corPagarReceber = "color:black;";
        }
    }

    public String salvarConta() {
        if (corPagarReceber.equalsIgnoreCase("color:red;")) {
            Contaspagar contaspagar = new Contaspagar();
            contaspagar.setDataEnvio(vendas.getDataVenda());
            contaspagar.setValor(valorPagarReceber);
            contaspagar.setPlanocontas(planocontas);
            contaspagar.setTipoDocumento(TipoDocumento);
            contaspagar.setCompetencia(competencia);
            contaspagar.setDescricao(vendas.getNomeCliente());
            contaspagar.setContaPaga("N");
            contaspagar.setDataVencimento(vendas.getDataVenda());
            contaspagar.setFornecedor(vendas.getNomeFornecedor());
            contaspagar.setFormaPagamento(TipoDocumento);
            contaspagar.setStatus("Ativo");
            contaspagar.setAutorizarPagamento("N");
            if (usuarioLogadoMB.getCliente() != null) {
                banco = bancoDao.find("Select b From Banco b Where b.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente()
                        + " and b.nome='Nenhum'");
                contaspagar.setBanco(banco);
                contaspagar.setCliente(usuarioLogadoMB.getCliente());
            } else {
                banco = bancoDao.find("Select b From Banco b Where b.cliente.idcliente=8 and b.nome='Nenhum'");
                contaspagar.setBanco(banco);
                cliente = clienteDao.find(8);
                contaspagar.setCliente(cliente);
            }
            contaspagar = contasPagarDao.update(contaspagar);
        } else if (corPagarReceber.equalsIgnoreCase("color:blue;")) {
            Contasreceber contasreceber = new Contasreceber();
            contasreceber.setDataVencimento(vendas.getDataVenda());
            contasreceber.setValorParcela(valorPagarReceber);
            contasreceber.setPlanocontas(planocontas);
            contasreceber.setTipodocumento(TipoDocumento);
            contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
            contasreceber.setNomeCliente(vendas.getNomeCliente());
            contasreceber.setJuros(0f);
            contasreceber.setDesagio(0f);
            contasreceber.setValorPago(0f);
            contasreceber.setNumeroParcela("1/1");
            contasreceber.setStatus("Ativo");

            if (usuarioLogadoMB.getCliente() != null) {
                banco = bancoDao.find("Select b From Banco b Where b.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente()
                        + " and b.nome='Nenhum'");
                contasreceber.setBanco(banco);
                contasreceber.setCliente(usuarioLogadoMB.getCliente());
            } else {
                banco = bancoDao.find("Select b From Banco b Where b.cliente.idcliente=8 and b.nome='Nenhum'");
                contasreceber.setBanco(banco);
                cliente = clienteDao.find(8);
                contasreceber.setCliente(cliente);
            }
            contasreceber = contasReceberDao.update(contasreceber);
            contasreceber.setNumeroDocumento("" + contasreceber.getIdcontasReceber());
            contasreceber = contasReceberDao.update(contasreceber);
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("importadoSystm", importadoSystm);
        return "cadBackOffice";
    }

    public Float saldoRestante() {
        float valorTotalForma = 0f;
        for (int i = 0; i < listaFormaPagamento.size(); i++) {
            valorTotalForma = valorTotalForma + listaFormaPagamento.get(i).getValor();
        }
        if (vendas.getLiquidoReceber() == null) {
            saldoRestante = valorTotalForma - 0;
        } else {
            saldoRestante = vendas.getLiquidoReceber() - valorTotalForma;
        }
        return saldoRestante;
    }

    public String salvarVenda() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        planocontas = (Planocontas) session.getAttribute("planocontas");
        if (produto == null) {
            produto = (Produto) session.getAttribute("produto");
        }
        vendas.setUsuario(usuarioLogadoMB.getUsuario());
        vendas.setProduto(produto);
        if (planocontas == null) {
            planocontas = planoContasDao.find(1);
            vendas.setPlanocontas(planocontas);
        } else {
            vendas.setPlanocontas(planocontas);
        }
        if (vendas.getValorLiquido() < 0) {
            vendas.setValorLiquido(vendas.getValorLiquido() * (-1));
        }
        if (listaFormaPagamento == null || listaFormaPagamento.isEmpty() == true) {
            vendas.setSituacao("vermelho");
        } else {
            vendas.setSituacao("amarelo");
        }
//		if (usuarioLogadoMB.getCliente() != null) {
//			vendas.setCliente(usuarioLogadoMB.getCliente());
//		}else{
//			try {
//				cliente = clienteFacade.consultar(8);
//				vendas.setCliente(cliente);
//			} catch (SQLException ex) {
//				Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
//				mostrarMensagem(ex, "Erro ao consultar um cliente:", "Erro");
//			}
//		}
        vendas.setCliente(cliente);
        String mensagem = validarDados();
        if (mensagem == "") {
            if (listaFormaPagamento == null || listaFormaPagamento.isEmpty() == true) {
                vendas.setSituacao("vermelho");
            } else {
                vendas.setSituacao("amarelo");
            }
            vendas = vendasDao.update(vendas);
            if (emissaonota != null) {
                emissaonota.setVendas(vendas);
                try {
                    emissaonota = vendasDao.salvar(emissaonota);
                } catch (SQLException ex) {
                    Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (listaFormaPagamento != null) {
                for (int i = 0; i < listaFormaPagamento.size(); i++) {
                    listaFormaPagamento.get(i).setVendas(vendas);
                    formaPagamentoDao.update(listaFormaPagamento.get(i));
                }
            }
            session.removeAttribute("vendas");
            session.removeAttribute("planocontas");
            session.removeAttribute("produto");
            session.removeAttribute("cliente");
            session.removeAttribute("listaFormaPagamento");
            if (importadoSystm) {
                importaVendasBean importaVendasBean = new importaVendasBean();
                try {
                    importaVendasBean.salvarVendaImportada(vendas.getIdVendaSystm());
                    getListaVendasSystm();
                } catch (JAXBException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                session.removeAttribute("importadoSystm");
                return "importarVenda";
            }
            session.removeAttribute("importadoSystm");
            RequestContext.getCurrentInstance().closeDialog(vendas);
            return "";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }
        return "";
    }

    public String validarDados() {
        String mensagem = "";
        if (vendas.getNomeCliente() == null) {
            mensagem = mensagem + "Cliente não informado \r\n";
        }
        if (vendas.getProduto().getIdproduto() == null) {
            mensagem = mensagem + " Produto não informado \r\n";
        }
        if (vendas.getValorBruto() == null) {
            mensagem = mensagem + " Valor bruto não informado \r\n";
        }
        if (vendas.getDataVenda() == null) {
            mensagem = mensagem + " Data da venda não informada \r\n";
        }
        return mensagem;
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("vendas");
        session.removeAttribute("listaFormaPagamento");
        session.removeAttribute("planocontas");
        session.removeAttribute("produto");
        session.removeAttribute("cliente");
        session.removeAttribute("valorPagarReceber");
        session.removeAttribute("importadoSystm");
        RequestContext.getCurrentInstance().closeDialog(new Vendas());
        return null;
    }

    public String fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public String excluir(Formapagamento formapagamento) {
        listaFormaPagamento.remove(formapagamento);
        mensagem msg = new mensagem();
        msg.excluiMessagem();
        gerarListaFormaPagamento();
        salvarVendaSemFormaRecebimento();
        return "";
    }

    public String SalvarFormaPagamento() {
        if (listaFormaPagamento == null) {
            listaFormaPagamento = new ArrayList<>();
        }
        if (saldoRestante >= formapagamento.getValor()) {
            if (!TipoDocumento.equalsIgnoreCase("sn")) {
                if (TipoDocumento.equalsIgnoreCase("Boleto")) {
                    String mensagens = validaDadosDocumentoBoleto();
                    if (mensagens == "") {
                        if (formapagamento.getDataVencimento() != null) {
                            formapagamento.setTipoDocumento(TipoDocumento);
                            listaFormaPagamento.add(formapagamento);
                            return "cadRecebimento";
                        } else {
                            mensagem mensagem = new mensagem();
                            mensagem.dataNaoFormaPagamento();
                            return "";
                        }
                    } else {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(mensagens, ""));
                    }

                } else if (formapagamento.getDataVencimento() != null) {
                    formapagamento.setTipoDocumento(TipoDocumento);
                    listaFormaPagamento.add(formapagamento);
                    return "cadRecebimento";
                } else {
                    mensagem mensagem = new mensagem();
                    mensagem.dataNaoFormaPagamento();
                    return "";
                }

            } else {
                mensagem mensagem = new mensagem();
                mensagem.tipoDocumentoNaoFormaPagamento();
            }

        } else {
            mensagem mensagem = new mensagem();
            mensagem.valorSuperiorAoRestante();
        }
        return "";
    }

    public String validaDadosDocumentoBoleto() {
        String msg = "";
        if (formapagamento.getCpf() == "") {
            msg = msg + "Cpf não informado \r \n";
        }

        if (formapagamento.getTipoLogradouro() == "") {
            msg = msg + " Tipo Logradouro não informado \r \n";
        }

        if (formapagamento.getLogradouro() == "") {
            msg = msg + " Logradouro não informado \r \n";
        }

        if (formapagamento.getCep() == "") {
            msg = msg + " Cep não informado \r \n";
        }

        if (formapagamento.getNumero() == "") {
            msg = msg + " Número do endereço não informado \r \n";
        }

        if (formapagamento.getBairro() == "") {
            msg = msg + " Bairro não informado \r \n";
        }

        if (formapagamento.getCidade() == "") {
            msg = msg + " Cidade não informado \r \n";
        }

        if (formapagamento.getComplemento() == "") {
            msg = msg + " Complemento não informado";
        }

        if (formapagamento.getEstado() == "") {
            msg = msg + " Estado não informado";
        }

        return msg;
    }

    public String voltarRecebimento() {
        return "cadRecebimento";
    }

    public void gerarListaFormaPagamento() {
        Vendas nVenda = null;
        if (vendas.getIdvendas() == null) {
            nVenda = vendasDao.find(1);
        } else {
            nVenda = vendas;
        }
        if (nVenda != null) {
            listaFormaPagamento = formaPagamentoDao.list("select f from Formapagamento f where f.vendas.idvendas=" + nVenda.getIdvendas());
        }
        if (listaFormaPagamento == null) {
            listaFormaPagamento = new ArrayList<Formapagamento>();
        }
    }

    public String salvarVendaSemFormaRecebimento() {
        if (listaFormaPagamento == null || listaFormaPagamento.isEmpty()) {
            return "A forma de recebimento ainda não foi informada, deseja continuar?";
        } else {
            return "Deseja finalizar o cadastro de uma venda?";
        }
    }

    public void ConsultarEmissaoNota() {
        try {
            emissaonota = vendasDao.getEmissao(vendas.getIdvendas());
        } catch (SQLException ex) {
            Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calcularValorParcelaLancaFormaPagamento() {
        formapagamento.setValorParcela(formapagamento.getValor() / formapagamento.getNumeroParcelas());
    }

    public String selecionarForma() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String vezes = "";
        Float valorParcela = 0f;
        String tipoDocumento = "";
        Date dataVencimento = null;
        for (int i = 0; i < listaFormaPagamento.size(); i++) {
            if (listaFormaPagamento.get(i).isSelecionado()) {
                vezes = "" + listaFormaPagamento.get(i).getNumeroParcelas();
                valorParcela = listaFormaPagamento.get(i).getValor();
                tipoDocumento = listaFormaPagamento.get(i).getTipoDocumento();
                dataVencimento = listaFormaPagamento.get(i).getDataVencimento();
            }
            session.setAttribute("vezes", vezes);
            session.setAttribute("tipoDocumento", tipoDocumento);
            session.setAttribute("dataVencimento", dataVencimento);
            session.setAttribute("valorParcela", valorParcela);
            session.setAttribute("importadoSystm", importadoSystm);
        }
        session.setAttribute("vendas", vendas);
        session.removeAttribute("listaFormaPagamento");
        session.setAttribute("importadoSystm", importadoSystm);
        return "gerarParcelas";
    }

    public String voltarGerarParcelas() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.removeAttribute("listaFormaPagamento");
        return "gerarParcelas";
    }

    public String editarFormaPagamento(Formapagamento formapagamento) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("formapagamento", formapagamento);
        session.setAttribute("importadoSystm", importadoSystm);
        session.setAttribute("saldoRestante", saldoRestante);
        return "lancaFormaPagamento";
    }

    public void setandoValoresEmissaoNota() {
        if (emissaonota == null) {
            emissaonota = new Emissaonota();
        }
        emissaonota.setBairro(cliente.getBairro());
        emissaonota.setCep(cliente.getCep());
        emissaonota.setCidade(cliente.getCidade());
        emissaonota.setComplemento(cliente.getComplemento());
        emissaonota.setCpnj(cliente.getCnpj());
        emissaonota.setEndereco(cliente.getTipoLogradouro() + " " + cliente.getLogradouro());
        emissaonota.setEstado(cliente.getEstado());
    }

    /* -----PARA PEGAR Só 1 VENDA------
	
	public void importaVenda(){
		importaVendasBean importaVendasBean = new importaVendasBean();
		VendasSystmBean vendasSystmBean = new VendasSystmBean();
		ClienteFacade clienteFacade = new ClienteFacade();
		ProdutoFacade produtoFacade = new ProdutoFacade();
		try {
			vendasSystmBean = importaVendasBean.pegarInformacao();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cliente = clienteFacade.consultarUnidade(vendasSystmBean.getIdUnidade());
			if (cliente != null) {
				gerarListaProduto();
				produto = produtoFacade.consultarProduto(vendasSystmBean.getIdProduto(), cliente.getIdcliente());
			}
			vendas.setNomeFornecedor(vendasSystmBean.getFornecedor());
			vendas.setConsultor(vendasSystmBean.getConsultor());
			vendas.setNomeCliente(vendasSystmBean.getNomeCliente());
			vendas.setValorBruto(vendasSystmBean.getValorBruto());
			calculoTotalVenda();
			vendas.setDataVenda(vendasSystmBean.getDataVenda());
			vendas.setComissaoLiquidaTotal(vendasSystmBean.getLiquidoFranquia());
			calculoValoresBackOffice();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
     */
    public void getListaVendasSystm() {
        importaVendasBean importaVendasBean = new importaVendasBean();
        ListaVendasSystmBean vendaImportada;
        listaImportada = new ArrayList<ListaVendasSystmBean>();
        try {
            listaVendasSystm = importaVendasBean.pegarListaVendasSystm();
            if (listaVendasSystm == null || listaVendasSystm.isEmpty()) {
                listaVendasSystm = new ArrayList<VendasSystmBean>();
            }
            for (int i = 0; i < listaVendasSystm.size(); i++) {
                vendaImportada = new ListaVendasSystmBean();
                vendaImportada.setConsultor(listaVendasSystm.get(i).getConsultor());
                vendaImportada.setDataVenda("" + Formatacao.ConvercaoDataPadrao(listaVendasSystm.get(i).getDataVenda()));
                vendaImportada.setFornecedor(listaVendasSystm.get(i).getFornecedor());
                vendaImportada.setIdCliente("" + listaVendasSystm.get(i).getIdCliente());
                vendaImportada.setValorBruto("" + listaVendasSystm.get(i).getValorBruto());
                vendaImportada.setNomeCliente(listaVendasSystm.get(i).getNomeCliente());
                vendaImportada.setIdVenda("" + listaVendasSystm.get(i).getIdVenda());
                vendaImportada.setIdProduto("" + listaVendasSystm.get(i).getIdProduto());
                vendaImportada.setIdUnidade("" + listaVendasSystm.get(i).getIdUnidade());
                vendaImportada.setIdUsuario("" + listaVendasSystm.get(i).getIdUsuario());
                vendaImportada.setLiquidoFranquia("" + listaVendasSystm.get(i).getLiquidoFranquia());
                if (vendaImportada.getValorBruto() == null || vendaImportada.getValorBruto().equalsIgnoreCase("null")) {
                    vendaImportada.setValorBruto("0.0");
                }
                vendaImportada.setVendasSystmBean(listaVendasSystm.get(i));
                listaImportada.add(vendaImportada);
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("listaImportada", listaImportada);
    }

    public String importaVenda(ListaVendasSystmBean vendaImportada) {
        if (vendas == null) {
            vendas = new Vendas();
        }
        List<Cliente> lista = clienteDao.list("Select c From Cliente c Where c.codigosystm=" + vendaImportada.getVendasSystmBean().getIdUnidade());
        for (int i = 0; i < lista.size(); i++) {
            cliente = lista.get(i);
        }
        if (cliente != null) {
            gerarListaProduto();
            List<Produto> listaProduto = produtoDao.list("Select p From Produto p Where p.codigosystm=" + vendaImportada.getVendasSystmBean().getIdProduto()
                    + " and p.cliente.idcliente=" + cliente.getIdcliente());
            for (int i = 0; i < listaProduto.size(); i++) {
                produto = listaProduto.get(i);
            } 
        } 
        vendas.setNomeFornecedor(vendaImportada.getVendasSystmBean().getFornecedor());
        vendas.setConsultor(vendaImportada.getVendasSystmBean().getConsultor());
        vendas.setNomeCliente(vendaImportada.getVendasSystmBean().getNomeCliente());
        vendas.setValorBruto(vendaImportada.getVendasSystmBean().getValorBruto());
        calculoTotalVenda();
        vendas.setDataVenda(vendaImportada.getVendasSystmBean().getDataVenda());
        vendas.setComissaoLiquidaTotal(vendaImportada.getVendasSystmBean().getLiquidoFranquia());
        vendas.setIdVendaSystm(Formatacao.formatarStringInteger(vendaImportada.getIdVenda()));
        calculoValoresBackOffice();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("cliente", cliente);
        session.setAttribute("produto", produto);
        return "cadVendas";
    }

    public void voltarImportacao() {
        RequestContext.getCurrentInstance().closeDialog(new Vendas());
    }

    public void filtroListaVendasSystm() {
        importaVendasBean importaVendasBean = new importaVendasBean();
        ListaVendasSystmBean vendaImportada;
        listaImportada = new ArrayList<ListaVendasSystmBean>();
        try {
            listaVendasSystm = importaVendasBean.pegarListaVendasSystm();
            if (listaVendasSystm == null || listaVendasSystm.isEmpty()) {
                listaVendasSystm = new ArrayList<VendasSystmBean>();
            }
            for (int i = 0; i < listaVendasSystm.size(); i++) {
                if (clienteImportacao != null) {
                    if (clienteImportacao.getCodigosystm() == listaVendasSystm.get(i).getIdUnidade()) {
                        if (dataInicial == null && dataFinal == null) {
                            vendaImportada = setandoVenda(listaVendasSystm.get(i));
                            listaImportada.add(vendaImportada);
                        } else {
                            String dataSystmImportada = Formatacao.ConvercaoDataSql(listaVendasSystm.get(i).getDataVenda());
                            String dataInicialFiltro = Formatacao.ConvercaoDataSql(dataInicial);
                            String dataFinalFiltro = Formatacao.ConvercaoDataSql(dataFinal);
                            if ((dataSystmImportada.compareTo(dataInicialFiltro) >= 0) && (dataSystmImportada.compareTo(dataFinalFiltro) <= 0)) {
                                vendaImportada = setandoVenda(listaVendasSystm.get(i));
                                listaImportada.add(vendaImportada);
                            }
                        }

                    }
                } else {
                    if (dataInicial == null && dataFinal == null) {
                        vendaImportada = setandoVenda(listaVendasSystm.get(i));
                        listaImportada.add(vendaImportada);
                    } else {
                        String dataSystmImportada = Formatacao.ConvercaoDataSql(listaVendasSystm.get(i).getDataVenda());
                        String dataInicialFiltro = Formatacao.ConvercaoDataSql(dataInicial);
                        String dataFinalFiltro = Formatacao.ConvercaoDataSql(dataFinal);
                        if ((dataSystmImportada.compareTo(dataInicialFiltro) >= 0) && (dataSystmImportada.compareTo(dataFinalFiltro) <= 0)) {
                            vendaImportada = setandoVenda(listaVendasSystm.get(i));
                            listaImportada.add(vendaImportada);
                        }
                    }
                }
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ListaVendasSystmBean setandoVenda(VendasSystmBean vendasSystmBean) {
        List<ListaVendasSystmBean> listaSystm = new ArrayList<>();
        ListaVendasSystmBean vendaImportada = new ListaVendasSystmBean();
        vendaImportada.setConsultor(vendasSystmBean.getConsultor());
        vendaImportada.setDataVenda("" + Formatacao.ConvercaoDataPadrao(vendasSystmBean.getDataVenda()));
        vendaImportada.setFornecedor(vendasSystmBean.getFornecedor());
        vendaImportada.setIdCliente("" + vendasSystmBean.getIdCliente());
        vendaImportada.setValorBruto("" + vendasSystmBean.getValorBruto());
        vendaImportada.setNomeCliente(vendasSystmBean.getNomeCliente());
        vendaImportada.setIdVenda("" + vendasSystmBean.getIdVenda());
        vendaImportada.setIdProduto("" + vendasSystmBean.getIdProduto());
        vendaImportada.setIdUnidade("" + vendasSystmBean.getIdUnidade());
        vendaImportada.setIdUsuario("" + vendasSystmBean.getIdUsuario());
        vendaImportada.setLiquidoFranquia("" + vendasSystmBean.getLiquidoFranquia());
        if (vendaImportada.getValorBruto() == null || vendaImportada.getValorBruto().equalsIgnoreCase("null")) {
            vendaImportada.setValorBruto("0.0");
        }
        vendaImportada.setVendasSystmBean(vendasSystmBean);
        return vendaImportada;
    }

    
    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<Planocontatipo>();
            }
            listaPlanocontas = new ArrayList<Planocontas>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanocontas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
