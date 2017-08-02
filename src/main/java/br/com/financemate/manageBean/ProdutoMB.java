package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ProdutoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ProdutoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Produto produto;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private List<Produto> listaProduto;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private ProdutoDao produtoDao;
    @EJB
    private ClienteDao clienteDao;

    @PostConstruct
    public void init() {
        gerarListaProdutos();
        gerarListaCliente();
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
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

    public void gerarListaProdutos() {
        if (usuarioLogadoMB.getCliente() == null) {
            listaProduto = produtoDao.list("Select p From Produto p");
        } else {
            listaProduto = produtoDao.list("Select p from Produto p where p.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente());
        }
        if (listaProduto == null) {
            listaProduto = new ArrayList<>();
        }
    }

    public String novoProduto() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadProduto", options, null);
        return "";
    }

    public String editar(Produto produto) {
        if (produto != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("produto", produto);
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 600);
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadProduto", options, null);

        }
        return "";
    }

    public void retornoDialog(SelectEvent event) {
        Produto produto = (Produto) event.getObject();
        if (produto.getIdproduto() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
        gerarListaProdutos();
    }
}
