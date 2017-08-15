package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.CalculosContasMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Movimentobanco;
import javax.ejb.EJB;

@Named
@ViewScoped
public class RecebimentoLoteMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<Contasreceber> listaContasSelecionadas;
    private String totalReceberLote;
    private Contasreceber contasReceber;
    private CalculosContasMB calcularContasMB;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Banco banco;
    private Cliente cliente;
    private Date dataPagamento;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private ContasReceberDao contasReceberDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaContasSelecionadas = (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
        totalReceberLote = (String) session.getAttribute("totalReceberLote");
        contasReceber = (Contasreceber) session.getAttribute("contasReceber");
        session.removeAttribute("totalReceberLote");
        session.removeAttribute("contasReceber");
        if (contasReceber == null) {
            contasReceber = new Contasreceber();
        }
        dataPagamento = new Date();
        if (listaContasSelecionadas == null) {
            listaContasSelecionadas = new ArrayList<>();
        }
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CalculosContasMB getCalcularContasMB() {
        return calcularContasMB;
    }

    public void setCalcularContasMB(CalculosContasMB calcularContasMB) {
        this.calcularContasMB = calcularContasMB;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
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

    public String salvarContasReceberLote() {
        String mensagens = validarDadosRecebimentoLote();
        if (mensagens.length() == 0) {
            for (int i = 0; i < listaContasSelecionadas.size(); i++) {
                contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
                listaContasSelecionadas.get(i).setUsuario(usuarioLogadoMB.getUsuario());
                listaContasSelecionadas.get(i).setDataPagamento(dataPagamento);
                listaContasSelecionadas.get(i).setValorPago(listaContasSelecionadas.get(i).getValorParcela());
                contasReceber = contasReceberDao.update(listaContasSelecionadas.get(i));
                lancaOutrosLancamentos(listaContasSelecionadas.get(i));
                RequestContext.getCurrentInstance().closeDialog(contasReceber);
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagens, ""));
        }
        return "";
    }

    public String validarDadosRecebimentoLote() {
        String msg = "";
        if (dataPagamento == null) {
            msg = msg + " Data de recebimento não informado";
        }
        return msg;
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public void lancaOutrosLancamentos(Contasreceber conta) {
        Movimentobanco outroslancamentos = new Movimentobanco();
        outroslancamentos.setBanco(conta.getBanco());
        outroslancamentos.setCliente(conta.getCliente());
        outroslancamentos.setDataVencimento(conta.getDataVencimento());
        outroslancamentos.setDataCompensacao(conta.getDataPagamento());
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setPlanocontas(conta.getPlanocontas());
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        outroslancamentos.setValorEntrada(conta.getValorPago());
        outroslancamentos.setValorSaida(0f);
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setConciliacao("não");
        outroslancamentos.setDescricao("Recebimento através do contas a receber de " + conta.getNomeCliente());
        outroslancamentos.setIdcontasreceber(conta.getIdcontasReceber());
        outrosLancamentosDao.update(outroslancamentos);

    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String editarBanco(Contasreceber conta) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("conta", conta);
        session.setAttribute("totalReceberLote", totalReceberLote);
        return "editarBancoRecebimentoLote";
    }

}
