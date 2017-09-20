package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContasDao;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Planocontas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RecebimentoContaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private List<Banco> listaBanco;
    private Banco banco;
    private Contasreceber contasReceber;
    private Boolean checkRecebimento = false;
    private Boolean disabilitar = true;
    private float valorParcial = 0f;
    private Date dataRecebimentoParcial;
    private List<Contasreceber> listaRecebimentoParial;
    private Float valorPagoParcial;
    private Float valorTotal;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private BancoDao bancoDao;
    private Float valorAnteriorJuros = 0.0f;
    private Float valorAnteriorDesagio= 0.0f;
    @EJB
    private PlanoContasDao planoContasDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contareceber");
        valorPagoParcial = (Float) session.getAttribute("valorPagoParcial");
        cliente = contasReceber.getCliente();
        gerarListaCliente();
        gerarListaBanco();
        cliente = contasReceber.getCliente();
        banco = contasReceber.getBanco();
        listaRecebimentoParial = new ArrayList<>();
        if (valorTotal == null || valorTotal == 0f) {
            valorTotal = contasReceber.getValorParcela();
        }
    }

    public Float getValorPagoParcial() {
        return valorPagoParcial;
    }

    public void setValorPagoParcial(Float valorPagoParcial) {
        this.valorPagoParcial = valorPagoParcial;
    }

    public List<Contasreceber> getListaRecebimentoParial() {
        return listaRecebimentoParial;
    }

    public void setListaRecebimentoParial(List<Contasreceber> listaRecebimentoParial) {
        this.listaRecebimentoParial = listaRecebimentoParial;
    }

    public Date getDataRecebimentoParcial() {
        return dataRecebimentoParcial;
    }

    public void setDataRecebimentoParcial(Date dataRecebimentoParcial) {
        this.dataRecebimentoParcial = dataRecebimentoParcial;
    }

    public float getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(float valorParcial) {
        this.valorParcial = valorParcial;
    }

    public Boolean getDisabilitar() {
        return disabilitar;
    }

    public void setDisabilitar(Boolean disabilitar) {
        this.disabilitar = disabilitar;
    }

    public Boolean getCheckRecebimento() {
        return checkRecebimento;
    }

    public void setCheckRecebimento(Boolean checkRecebimento) {
        this.checkRecebimento = checkRecebimento;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
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

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
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

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null || listaCliente.isEmpty()) {
            listaCliente = new ArrayList<>();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String salvar() {
        String mensagens = validarDadosRecebimentoTotal();
        if (mensagens.length() == 0) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            if (valorParcial > 0f) {
                session.removeAttribute("contareceber");
                RequestContext.getCurrentInstance().closeDialog(contasReceber);
                return "";
            } else {
                contasReceber.setBanco(banco);
                contasReceber.setCliente(cliente);
                contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
                contasReceber.setValorPago(valorTotal);
                contasReceber = contasReceberDao.update(contasReceber);
                lancaOutrosLancamentos(contasReceber);
                session.removeAttribute("contareceber");
                RequestContext.getCurrentInstance().closeDialog(contasReceber);
                return "";
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagens, ""));
        }
        return "";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("listaRecebimentoParial");
        session.removeAttribute("contareceber");
        RequestContext.getCurrentInstance().closeDialog(null);
        contasReceber.setValorPago(0.0f);
        contasReceber.setJuros(0f);
        contasReceber.setDesagio(0f);
        return "";
    }

    public void calcularValoresRecebimento() {
        Float valorReceber = contasReceber.getValorParcela() - contasReceber.getDesagio() + contasReceber.getJuros();
        contasReceber.setValorPago(valorReceber);
    }

    public Boolean habilitarCampoCheckRecebimento() {
        if (checkRecebimento) {
            disabilitar = false;
            return disabilitar;
        } else {
            disabilitar = true;
            return disabilitar;
        }

    }

    public String salvarRecebimentoParcial() {
        String mensagens = validarDadosRecebimentoParcial();
        if (mensagens.length() == 0) {
            if (valorParcial <= contasReceber.getValorParcela()) {
                contasReceber.setBanco(banco);
                contasReceber.setCliente(cliente);
                contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
                contasReceber.setValorPago(contasReceber.getValorPago() + valorParcial);
                contasReceber.setValorParcela(contasReceber.getValorParcela() - valorParcial);
                contasReceber = contasReceberDao.update(contasReceber);
            } else {
                mensagem mensagem = new mensagem();
                mensagem.RecebimentoParcialAcimaValor();
                return "";
            }
            lancaOutrosLancamentosParcial(contasReceber);
            listaRecebimentoParial.add(contasReceber);
            for (int i = 0; i < listaRecebimentoParial.size(); i++) {
                Contasreceber conta = new Contasreceber();
                conta.setBanco(listaRecebimentoParial.get(i).getBanco());
                conta.setCliente(listaRecebimentoParial.get(i).getCliente());
                conta.setUsuario(listaRecebimentoParial.get(i).getUsuario());
                conta.setValorPago(listaRecebimentoParial.get(i).getValorPago());
                conta.setDataPagamento(dataRecebimentoParcial);
                conta.setNumeroDocumento(listaRecebimentoParial.get(i).getNumeroDocumento());
                conta.setPlanocontas(listaRecebimentoParial.get(i).getPlanocontas());
                conta.setVenda(listaRecebimentoParial.get(i).getVenda());
                conta.setJuros(listaRecebimentoParial.get(i).getJuros());
                conta.setDesagio(listaRecebimentoParial.get(i).getDesagio());
                conta.setDataVencimento(listaRecebimentoParial.get(i).getDataVencimento());
                conta.setNomeCliente(listaRecebimentoParial.get(i).getNomeCliente());
                conta.setTipodocumento(listaRecebimentoParial.get(i).getTipodocumento());
                conta.setStatus("RECEBIMENTO-" + listaRecebimentoParial.get(i).getIdcontasReceber());
                contasReceberDao.update(conta);
            }
            mensagem mensagem = new mensagem();
            mensagem.recebidoParcial();

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagens, ""));
        }
        return "";
    }

    public String recebimentoContaParcial(Contasreceber contasreceber) {
        if (contasreceber != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contareceber", contasreceber);
            return "visualizarRecebimentoParcial";
        }
        return "";
    }

    public void lancaOutrosLancamentos(Contasreceber conta) {
        Planocontas planocontas = planoContasDao.find(3);
        Movimentobanco outroslancamentos = new Movimentobanco();
        outroslancamentos.setBanco(conta.getBanco());
        outroslancamentos.setCliente(conta.getCliente());
        outroslancamentos.setDataVencimento(conta.getDataVencimento());
        outroslancamentos.setDataCompensacao(conta.getDataPagamento());
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setPlanocontas(planocontas);
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        outroslancamentos.setValorEntrada(conta.getValorPago());
        outroslancamentos.setValorSaida(0f);
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setDescricao("Recebimento através do contas a receber de " + conta.getNomeCliente());
        outroslancamentos.setIdcontasreceber(conta.getIdcontasReceber());
        outroslancamentos.setConciliacao("não");
        outrosLancamentosDao.update(outroslancamentos);

    }

    public void SomarJuros() {
        if (contasReceber.getJuros() == null && contasReceber.getJuros() == 0) {
            contasReceber.setJuros(0f);
        }
        
        if (valorAnteriorJuros == 0.0f) {
            valorTotal = valorTotal + contasReceber.getJuros();
            valorAnteriorJuros = contasReceber.getJuros(); 
        }else{
            valorTotal = valorTotal - valorAnteriorJuros;
            valorTotal = valorTotal + contasReceber.getJuros();
            valorAnteriorJuros = contasReceber.getJuros();
        }
    }  

    public void DebitarDesagio() {
        if (contasReceber.getDesagio() == null && contasReceber.getDesagio() == 0) {
            contasReceber.setDesagio(0f);
        }
        if (valorAnteriorDesagio == 0.0f) {
            valorTotal = valorTotal - contasReceber.getDesagio();
            valorAnteriorDesagio = contasReceber.getDesagio();
        }else{
            valorTotal = valorTotal + valorAnteriorDesagio;
            valorTotal = valorTotal - contasReceber.getDesagio();
            valorAnteriorDesagio = contasReceber.getDesagio();
        }

    }

    public void lancaOutrosLancamentosParcial(Contasreceber conta) {
        Planocontas planocontas = planoContasDao.find(3);
        Movimentobanco outroslancamentos = new Movimentobanco();
        outroslancamentos.setBanco(conta.getBanco());
        outroslancamentos.setCliente(conta.getCliente());
        outroslancamentos.setDataVencimento(conta.getDataVencimento());
        outroslancamentos.setDataCompensacao(dataRecebimentoParcial);
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setPlanocontas(planocontas);
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        outroslancamentos.setValorEntrada(valorParcial);
        outroslancamentos.setValorSaida(0f);
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setConciliacao("não");
        outroslancamentos.setDescricao("Recebimento parcial através do contas a receber de " + conta.getNomeCliente());
        outroslancamentos.setIdcontasreceber(conta.getIdcontasReceber());
        outrosLancamentosDao.update(outroslancamentos);

    }

    public String validarDadosRecebimentoParcial() {
        String msg = "";
        if (dataRecebimentoParcial == null) {
            msg = msg + "Data de recebimento não informado";
        }
        return msg;
    }

    public String validarDadosRecebimentoTotal() {
        String msg = "";
        if (contasReceber.getDataPagamento() == null) {
            msg = msg + " Data de recebimento não informado";
        }
        return msg;
    }

}
