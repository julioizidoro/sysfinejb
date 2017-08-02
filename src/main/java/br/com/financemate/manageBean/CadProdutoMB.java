package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ProdutoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadProdutoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Produto produto;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private ProdutoDao produtoDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produto = (Produto) session.getAttribute("produto");
        session.removeAttribute("produto");
        if (produto == null) {
            produto = new Produto();
        } else {
            cliente = produto.getCliente();
        }
        gerarListaCliente();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }
    }

    public void salvar() {
        produto.setCliente(cliente);
        produto = produtoDao.update(produto);
        RequestContext.getCurrentInstance().closeDialog(produto);
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(produto);
        return "";
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

}
