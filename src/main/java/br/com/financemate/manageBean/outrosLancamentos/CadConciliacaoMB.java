package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadConciliacaoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Planocontas planoconta;
    private List<Planocontas> listaPlanoConta;
    private ConciliacaoBean conciliacaoBean;
    private TransacaoBean transacaoBean;
    private Movimentobanco outroslancamentos;
    private Banco banco;
    private String campoNomeValor;
    private Float valor;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    private Cliente cliente;
    private Planocontatipo plaoncontatipo;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    private List<Planocontatipo> listaPlanoContaTipo;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conciliacaoBean = (ConciliacaoBean) session.getAttribute("conciliacaoBean");
        transacaoBean = (TransacaoBean) session.getAttribute("transacaoBean");
        outroslancamentos = (Movimentobanco) session.getAttribute("outroslancamentos");
        banco = (Banco) session.getAttribute("banco");
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("banco");
        session.removeAttribute("transacaoBean");
        session.removeAttribute("conciliacaoBean");
        session.removeAttribute("cliente");
        carregarPlanoConta();
        if (outroslancamentos == null) {
            outroslancamentos = new Movimentobanco();
        }
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getCampoNomeValor() {
        return campoNomeValor;
    }

    public void setCampoNomeValor(String campoNomeValor) {
        this.campoNomeValor = campoNomeValor;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Movimentobanco getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Movimentobanco outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public TransacaoBean getTransacaoBean() {
        return transacaoBean;
    }

    public void setTransacaoBean(TransacaoBean transacaoBean) {
        this.transacaoBean = transacaoBean;
    }

    public Planocontas getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planocontas planoconta) {
        this.planoconta = planoconta;
    }

    public List<Planocontas> getListaPlanoConta() {
        return listaPlanoConta;
    }

    public void setListaPlanoConta(List<Planocontas> listaPlanoConta) {
        this.listaPlanoConta = listaPlanoConta;
    }

    public ConciliacaoBean getConciliacaoBean() {
        return conciliacaoBean;
    }

    public void setConciliacaoBean(ConciliacaoBean conciliacaoBean) {
        this.conciliacaoBean = conciliacaoBean;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Planocontatipo getPlaoncontatipo() {
        return plaoncontatipo;
    }

    public void setPlaoncontatipo(Planocontatipo plaoncontatipo) {
        this.plaoncontatipo = plaoncontatipo;
    }

    public List<Planocontatipo> getListaPlanoContaTipo() {
        return listaPlanoContaTipo;
    }

    public void setListaPlanoContaTipo(List<Planocontatipo> listaPlanoContaTipo) {
        this.listaPlanoContaTipo = listaPlanoContaTipo;
    }
    
    
    
    
     public void carregarPlanoConta() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<>();
            }
            listaPlanoConta = new ArrayList<>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoConta.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
    }

    public String salvarConciliacao() {
        outroslancamentos.setBanco(banco);
        outroslancamentos.setPlanocontas(planoconta);
        outroslancamentos.setDataCompensacao(transacaoBean.getData());
        outroslancamentos.setDescricao(transacaoBean.getDescricao());
        outroslancamentos.setCliente(cliente);
        outroslancamentos.setConciliacao("0");
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        if (transacaoBean.getValorEntrada() > 0) {
            outroslancamentos.setValorEntrada(valor);
            outroslancamentos.setValorSaida(0f);
        } else {
            outroslancamentos.setValorSaida(valor);
            outroslancamentos.setValorEntrada(0f);
        }
        outroslancamentos = outrosLancamentosDao.create(outroslancamentos);
        RequestContext.getCurrentInstance().closeDialog(outroslancamentos);
        return "";
    }

    public String nomeCampoValor() {
        if (transacaoBean.getValorEntrada() > 0) {
            campoNomeValor = "Valor Entrada";
            valor = transacaoBean.getValorEntrada();
            return campoNomeValor;
        } else {
            valor = transacaoBean.getValorSaida();
            campoNomeValor = "Valor Saida";
            return campoNomeValor;
        }
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    
    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<>();
            }
            listaPlanoConta = new ArrayList<>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoConta.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
    }

}
