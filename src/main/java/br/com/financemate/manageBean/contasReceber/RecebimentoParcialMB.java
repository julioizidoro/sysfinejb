package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.ContasReceberDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import javax.ejb.EJB;

@Named
@ViewScoped
public class RecebimentoParcialMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private Contasreceber contasReceber;
    private List<Contasreceber> listaRecebimentoParcial;
    private Float valorPagoParcial;
    @EJB
    private ContasReceberDao contasReceberDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contareceber");
        valorPagoParcial = (Float) session.getAttribute("valorPagoParcial");
        session.removeAttribute("listaRecebimentoParial");
        gerarListaRecebimentoParcial();
        if (listaRecebimentoParcial == null) {
            listaRecebimentoParcial = new ArrayList<Contasreceber>();
        }
    }

    public Float getValorPagoParcial() {
        return valorPagoParcial;
    }

    public void setValorPagoParcial(Float valorPagoParcial) {
        this.valorPagoParcial = valorPagoParcial;
    }

    public List<Contasreceber> getListaRecebimentoParcial() {
        return listaRecebimentoParcial;
    }

    public void setListaRecebimentoParcial(List<Contasreceber> listaRecebimentoParcial) {
        this.listaRecebimentoParcial = listaRecebimentoParcial;
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

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public String voltar() {
        return "recebimentoConta";
    }

    public void gerarListaRecebimentoParcial() {
        String sql = "Select c from Contasreceber c where c.numeroDocumento=" + contasReceber.getNumeroDocumento()
                + " and  c.dataPagamento<>null order by c.dataPagamento";
        listaRecebimentoParcial = contasReceberDao.list(sql);
        if (listaRecebimentoParcial == null) {
            listaRecebimentoParcial = new ArrayList<Contasreceber>();
        }
    }

}
