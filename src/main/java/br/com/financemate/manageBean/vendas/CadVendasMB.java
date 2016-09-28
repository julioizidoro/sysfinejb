package br.com.financemate.manageBean.vendas;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.FormaPagamentoDao;
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
import br.com.financemate.model.Produto;
import br.com.financemate.model.Vendas;
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
        session.removeAttribute("formapagamento");
        session.removeAttribute("saldoRestante");
        session.removeAttribute("corPagarReceber");
        gerarListaCliente();
        if (vendas == null) {
            vendas = new Vendas();
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
        gerarListaPlanoContas();
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

    public PlanoContasDao getPlanoContasDao() {
        return planoContasDao;
    }

    public void setPlanoContasDao(PlanoContasDao planoContasDao) {
        this.planoContasDao = planoContasDao;
    }

    public FormaPagamentoDao getFormaPagamentoDao() {
        return formaPagamentoDao;
    }

    public void setFormaPagamentoDao(FormaPagamentoDao formaPagamentoDao) {
        this.formaPagamentoDao = formaPagamentoDao;
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

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c where c.nomeFantasia like '%" + "" + "%' order by c.razaoSocial");
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
            listaProduto = produtoDao.list("Select p from Produto p where p.cliente.idcliente=" + cliente.getIdcliente());
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
        return "cadVendas";
    }

    public String adicionarConta() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
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
        session.setAttribute("listaFormaPagamento", listaFormaPagamento);
        session.setAttribute("corPagarReceber", corPagarReceber);
        return "cadRecebimento";
    }

    public void gerarListaPlanoContas() {
        try {
            listaPlanocontas = planoContasDao.list("Select p from Planocontas p  order by p.descricao");
            if (listaPlanocontas == null) {
                listaPlanocontas = new ArrayList<Planocontas>();
            }
        } catch (Exception ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
        }

    }

    public String nomeConta() {
        if (corPagarReceber.equalsIgnoreCase("color:red;")) {
            return "Lan�ar Conta a Pagar";
        } else if (corPagarReceber.equalsIgnoreCase("color:blue;")) {
            return "Lan�ar Conta a Receber";
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

        vendas.setLiquidoVendas(vendas.getComissaoLiquidaTotal() - (vendas.getValorDesconto() + vendas.getDespesasFinanceiras() + vendas.getComissaoFuncionarios() + vendas.getComissaoTerceiros()));
        valorPagarReceber = vendas.getValorBruto() - (vendas.getComissaoLiquidaTotal() + vendas.getValorPagoFornecedor());
        vendas.setLiquidoReceber(vendas.getValorLiquido() - vendas.getValorPagoFornecedor());
        escolherCorPagarReceber();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        session.setAttribute("vendas", vendas);
    }

    public void escolherCorPagarReceber() {
        if (valorPagarReceber == null) {
            valorPagarReceber = 0f;
        }
        if (valorPagarReceber > 0) {
            corPagarReceber = "color:blue;";
        } else if (valorPagarReceber < 0) {
            valorPagarReceber = valorPagarReceber * (-1);
            corPagarReceber = "color:red;";
        } else {
            corPagarReceber = "color:black;";
        }
    }

    public String salvarConta() {
        if (valorPagarReceber > 0) {
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
                banco = bancoDao.find("Select b from Banco b where b.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente()
                        + " and b.nome='" + "Nenhum" + "'");
                contaspagar.setBanco(banco);
                contaspagar.setCliente(usuarioLogadoMB.getCliente());
            } else {
                banco = bancoDao.find("Select b from Banco b where b.cliente.idcliente=" + 8
                        + " and b.nome='" + "Nenhum" + "'");
                contaspagar.setBanco(banco);
                cliente = clienteDao.find(8);
                contaspagar.setCliente(cliente);
            }
            contaspagar = contasPagarDao.update(contaspagar);
        } else if (valorPagarReceber < 0) {
            Contasreceber contasreceber = new Contasreceber();
            contasreceber.setDataVencimento(vendas.getDataVenda());
            contasreceber.setValorParcela(valorPagarReceber * (-1));
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
                banco = bancoDao.find("Select b from Banco b where b.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente()
                        + " and b.nome='" + "Nenhum" + "'");
                contasreceber.setBanco(banco);
                contasreceber.setCliente(usuarioLogadoMB.getCliente());
            } else {
                banco = bancoDao.find("Select b from Banco b where b.cliente.idcliente=" + 8
                        + " and b.nome='" + "Nenhum" + "'");
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

    public void salvarVenda() {
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
        if (usuarioLogadoMB.getCliente() != null) {
            vendas.setCliente(usuarioLogadoMB.getCliente());
        } else {
            cliente = clienteDao.find(8);
            vendas.setCliente(cliente);
        }
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
            RequestContext.getCurrentInstance().closeDialog(vendas);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }
    }

    public String validarDados() {
        String mensagem = "";
        if (vendas.getNomeCliente() == null) {
            mensagem = mensagem + "Cliente n�o informado \r\n";
        }
        if (vendas.getProduto().getIdproduto() == null) {
            mensagem = mensagem + " Produto n�o informado \r\n";
        }
        if (vendas.getValorBruto() == null) {
            mensagem = mensagem + " Valor bruto n�o informado \r\n";
        }
        if (vendas.getDataVenda() == null) {
            mensagem = mensagem + " Data da venda n�o informada \r\n";
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
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public String fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public String excluir() {
        formaPagamentoDao.remove(formapagamento.getIdformaPagamento());
        listaFormaPagamento.remove(formapagamento.getIdformaPagamento());
        mensagem msg = new mensagem();
        msg.excluiMessagem();
        gerarListaFormaPagamento();
        salvarVendaSemFormaRecebimento();
        return "";
    }

    public String SalvarFormaPagamento() {
        Vendas nVenda;
        if (saldoRestante >= formapagamento.getValor()) {
            if (vendas.getIdvendas() == null) {
                nVenda = vendasDao.find(1);
            } else {
                nVenda = vendas;
            }
            if (!TipoDocumento.equalsIgnoreCase("sn")) {
                if (TipoDocumento.equalsIgnoreCase("Boleto")) {
                    String mensagens = validaDadosDocumentoBoleto();
                    if (mensagens == "") {
                        if (formapagamento.getDataVencimento() != null) {
                            formapagamento.setTipoDocumento(TipoDocumento);
                            formapagamento.setVendas(nVenda);
                            formapagamento = formaPagamentoDao.update(formapagamento);
                            gerarListaFormaPagamento();
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
                    formapagamento.setVendas(nVenda);
                    formapagamento = formaPagamentoDao.update(formapagamento);
                    gerarListaFormaPagamento();
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
            msg = msg + "Cpf n�o informado \r \n";
        }

        if (formapagamento.getTipoLogradouro() == "") {
            msg = msg + " Tipo Logradouro n�o informado \r \n";
        }

        if (formapagamento.getLogradouro() == "") {
            msg = msg + " Logradouro n�o informado \r \n";
        }

        if (formapagamento.getCep() == "") {
            msg = msg + " Cep n�o informado \r \n";
        }

        if (formapagamento.getNumero() == "") {
            msg = msg + " N�mero do endere�o n�o informado \r \n";
        }

        if (formapagamento.getBairro() == "") {
            msg = msg + " Bairro n�o informado \r \n";
        }

        if (formapagamento.getCidade() == "") {
            msg = msg + " Cidade n�o informado \r \n";
        }

        if (formapagamento.getComplemento() == "") {
            msg = msg + " Complemento n�o informado";
        }

        if (formapagamento.getEstado() == "") {
            msg = msg + " Estado n�o informado";
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
        listaFormaPagamento = formaPagamentoDao.list("select f from Formapagamento f where f.vendas.idvendas=" + nVenda.getIdvendas());
        if (listaFormaPagamento == null) {
            listaFormaPagamento = new ArrayList<Formapagamento>();
        }
    }

    public String salvarVendaSemFormaRecebimento() {
        if (listaFormaPagamento == null || listaFormaPagamento.size() == 0) {
            return "A forma de recebimento ainda n�o foi informada, deseja continuar?";
        } else {
            return "Deseja finalizar o cadastro de uma venda?";
        }
    }

    public void ConsultarEmissaoNota() {
        try {
            // TODO Auto-generated catch block

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
        }
        session.setAttribute("vendas", vendas);
        session.removeAttribute("listaFormaPagamento");
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

}
