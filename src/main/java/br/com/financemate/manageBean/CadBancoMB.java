package br.com.financemate.manageBean;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadBancoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Banco banco;
    private int idCliente;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    private boolean habilitarUnidade = false;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("banco");
        gerarListaCliente();
        if (banco == null) {
            banco = new Banco();
            if (usuarioLogadoMB.getCliente() != null) {
                cliente = usuarioLogadoMB.getCliente();
            }
        } else {
            cliente = banco.getCliente();
        }
        desabilitarUnidade();
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public boolean isHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }
    
    

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void salvar() {
        banco.setCliente(cliente);
        banco = bancoDao.update(banco);
        RequestContext.getCurrentInstance().closeDialog(banco);
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Banco());
        return "";
    }

    public void gerarListaCliente() {
        // TODO Auto-generated catch block

        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }
    }
    
    
      public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }


}
