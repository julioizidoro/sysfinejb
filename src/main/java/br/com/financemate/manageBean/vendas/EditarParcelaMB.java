package br.com.financemate.manageBean.vendas;

import br.com.financemate.dao.ContasReceberDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Vendas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class EditarParcelaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Vendas vendas;
    private Contasreceber contasReceber;
    private List<Contasreceber> listarContasreceber;
    private String tipoDocumento;
    private Float valorEditado;
    @EJB
    private ContasReceberDao contasReceberDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        if (contasReceber == null) {
            contasReceber = new Contasreceber();
        } else {
            tipoDocumento = contasReceber.getTipodocumento();
        }
        valorEditado = contasReceber.getValorParcela();
    }

    public Float getValorEditado() {
        return valorEditado;
    }

    public void setValorEditado(Float valorEditado) {
        this.valorEditado = valorEditado;
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

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
    }

    public List<Contasreceber> getListarContasreceber() {
        return listarContasreceber;
    }

    public void setListarContasreceber(List<Contasreceber> listarContasreceber) {
        this.listarContasreceber = listarContasreceber;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public String SalvarParcelaEditada() {
        Float valorDividir;
        Float valorDivido = 0f;
        Integer numeroParcelas;
        Float totalParcela;
        Contasreceber conta = new Contasreceber();
        List<Contasreceber> listarConta = null;
        conta = contasReceberDao.find(contasReceber.getIdcontasReceber());
        conta.setTipodocumento(tipoDocumento);
        conta.setValorParcela(valorEditado);
        contasReceberDao.update(conta);
        listarConta = contasReceberDao.list("Select c From Contasreceber c where c.venda=" + contasReceber.getVenda() + " and c.valorParcela=" + contasReceber.getValorParcela());
        if (listarConta == null) {
            listarConta = new ArrayList<Contasreceber>();
        }
        numeroParcelas = listarConta.size();
        for (int i = 0; i < listarConta.size(); i++) {
            if (contasReceber.getValorParcela() < valorEditado) {
                valorDividir = valorEditado - contasReceber.getValorParcela();
                valorDivido = valorDividir / numeroParcelas;
                totalParcela = contasReceber.getValorParcela() - valorDivido;
                listarConta.get(i).setValorParcela(totalParcela);
            } else {
                valorDividir = contasReceber.getValorParcela() - valorEditado;
                valorDivido = valorDividir / numeroParcelas;
                totalParcela = contasReceber.getValorParcela() + valorDivido;
                listarConta.get(i).setValorParcela(totalParcela);
            }

            contasReceberDao.update(listarConta.get(i));
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        return "gerarParcelas";
    }

}
