package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.model.Contasreceber;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioCobrancaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<RelatorioCobrancaBean> listaRelatorio;
    private List<Contasreceber> listaContasReceber;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaRelatorio = (List<RelatorioCobrancaBean>) session.getAttribute("listaRelatorio");
        session.removeAttribute("listaRelatorio");
    }

    public List<RelatorioCobrancaBean> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<RelatorioCobrancaBean> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public String fechar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

    public String valortotal(Float valorRS) {
        String valor = Formatacao.formatarFloatString(valorRS);
        return valor;
    }

}
