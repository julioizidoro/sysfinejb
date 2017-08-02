package br.com.financemate.manageBean;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.OperacaoUsuarioDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import java.io.Serializable;
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

import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Movimentobanco;
import javax.ejb.EJB;

@Named
@ViewScoped
public class LiberarContasPagarMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<Contaspagar> listaContasPagar;
    private Contaspagar contasPagar;
    private String totalLiberadas;
    private List<Contaspagar> listaContasSelecionadas;
    private Date dataLiberacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @Inject
    private CalculosContasMB calculosContasMB;
    private String liberadas;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private OperacaoUsuarioDao operacaoUsuarioDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaContasSelecionadas = (List<Contaspagar>) session.getAttribute("listaContasSelecionadas");
        totalLiberadas = (String) session.getAttribute("totalLiberadas");
        contasPagar = (Contaspagar) session.getAttribute("contasPagar");
        session.removeAttribute("contasPagar");
        if (contasPagar == null) {
            contasPagar = new Contaspagar();
        }
    }

    public String getLiberadas() {
        return liberadas;
    }

    public void setLiberadas(String liberadas) {
        this.liberadas = liberadas;
    }

    public CalculosContasMB getCalculosContasMB() {
        return calculosContasMB;
    }

    public void setCalculosContasMB(CalculosContasMB calculosContasMB) {
        this.calculosContasMB = calculosContasMB;
    }

    public List<Contaspagar> getListaContasPagar() {
        return listaContasPagar;
    }

    public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
        this.listaContasPagar = listaContasPagar;
    }

    public Contaspagar getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(Contaspagar contasPagar) {
        this.contasPagar = contasPagar;
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

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Date dataLibercao() {
        dataLiberacao = new Date();
        contasPagar.setDataLiberacao(dataLiberacao);
        return dataLiberacao;
    }

    public String salvarContasLiberadas(Contaspagar conta) {
        mensagem msg = new mensagem();
        for (int i = 0; i < listaContasSelecionadas.size(); i++) {
            String mensagem = validarDados(listaContasSelecionadas.get(i));
            if (mensagem.length() == 0) {
                if (listaContasSelecionadas.get(i).getAutorizarPagamento().equals("S")) {

                    salvarContaLiberadasMovimentoBanco(listaContasSelecionadas.get(i));
                    salvarOperacaoUsuarioLiberou(listaContasSelecionadas.get(i));
                    msg.liberar();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                    session.removeAttribute("totalLiberadas");
                } else {
                    msg.naoLiberar();
                    RequestContext.getCurrentInstance().closeDialog(contasPagar);
                    return "";
                }
            } else {
                msg.competencia();
                return "";
            }
            calculosContasMB.calcularTotalContasPagar();
        }
        RequestContext.getCurrentInstance().closeDialog(contasPagar);
        return "";
    }

    public void salvarContaLiberadasMovimentoBanco(Contaspagar conta) {
        conta.setDataLiberacao(dataLiberacao);
        conta.setContaPaga("S");
        Movimentobanco outroslancamentos = new Movimentobanco();
        outroslancamentos.setBanco(conta.getBanco());
        outroslancamentos.setCliente(conta.getCliente());
        outroslancamentos.setDataVencimento(conta.getDataVencimento());
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setPlanocontas(conta.getPlanocontas());
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        outroslancamentos.setValorEntrada(0.0f);
        outroslancamentos.setValorSaida(conta.getValor());
        if (conta.getDataCompensacao() == null) {
            outroslancamentos.setDataCompensacao(new Date());
        } else {
            outroslancamentos.setDataCompensacao(conta.getDataCompensacao());
        }
        outroslancamentos.setTipoDocumento(conta.getTipoDocumento());
        outroslancamentos.setDescricao(conta.getDescricao());
        outroslancamentos.setCompentencia(conta.getCompetencia());
        conta = contasPagarDao.update(conta);
        outroslancamentos.setIdcontaspagar(conta.getIdcontasPagar());
        outrosLancamentosDao.update(outroslancamentos);

    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String validarDados(Contaspagar contaspagar) {
        String mensagem = "";
        if (contaspagar.getCompetencia().equalsIgnoreCase("")) {
            mensagem = mensagem + "Competência não informada \r\n";
        }
        if (contaspagar.getPlanocontas() == null) {
            mensagem = mensagem + "Plano de contas não informado \r\n";
        }
        if (contaspagar.getDataCompensacao() == null) {
            mensagem = mensagem + "Data de Compensação não informado \r\n";
        }
        if (contaspagar.getDataAgendamento() == null) {
            mensagem = mensagem + "Data de Agendamento não informado \r\n";
        }
        return mensagem;
    }

    public String editarBanco(Contaspagar conta) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("conta", conta);
        return "editarBanco";
    }

    public void salvarOperacaoUsuarioLiberou(Contaspagar contaspagar) {
        if (contaspagar != null) {
            Operacaousuairo operacaousuairo = new Operacaousuairo();
            operacaousuairo.setContaspagar(contaspagar);
            operacaousuairo.setData(new Date());
            operacaousuairo.setTipooperacao("Usuário Liberou");
            operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
            operacaoUsuarioDao.update(operacaousuairo);
        }
    }
}
