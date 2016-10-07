package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.TipoAcessoDao;
import br.com.financemate.dao.UsuarioDao;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoacesso;
import br.com.financemate.model.Usuario;
import br.com.financemate.util.Criptografia;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadUsuarioMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Tipoacesso tipoacesso;
    private List<Tipoacesso> listaTipoAcesso;
    private Usuario usuario;
    private boolean habilitarSenha;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private boolean habilitarCliente = false;
    private boolean eCliente;
    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private TipoAcessoDao tipoAcessoDao;
    @EJB
    private ClienteDao clienteDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        usuario = (Usuario) session.getAttribute("usuario");
        session.removeAttribute("usuario");
        if (usuario == null) {
            usuario = new Usuario();
            habilitarSenha = true;
        } else {
            tipoacesso = usuario.getTipoacesso();
            habilitarSenha = false;
            if (usuario.getCliente() > 0) {
                cliente = clienteDao.find(usuario.getCliente());
                habilitarCliente = true;
                eCliente = true;
            }
        }
        gerarListaCliente();
        gerarListaTipoAcesso();
    }

    public Tipoacesso getTipoacesso() {
        return tipoacesso;
    }

    public void setTipoacesso(Tipoacesso tipoacesso) {
        this.tipoacesso = tipoacesso;
    }

    public List<Tipoacesso> getListaTipoAcesso() {
        return listaTipoAcesso;
    }

    public void setListaTipoAcesso(List<Tipoacesso> listaTipoAcesso) {
        this.listaTipoAcesso = listaTipoAcesso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isHabilitarSenha() {
        return habilitarSenha;
    }

    public void setHabilitarSenha(boolean habilitarSenha) {
        this.habilitarSenha = habilitarSenha;
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

    public boolean isHabilitarCliente() {
        return habilitarCliente;
    }

    public void setHabilitarCliente(boolean habilitarCliente) {
        this.habilitarCliente = habilitarCliente;
    }

    public boolean iseCliente() {
        return eCliente;
    }

    public void seteCliente(boolean eCliente) {
        this.eCliente = eCliente;
    }

    public void gerarListaTipoAcesso() {
        listaTipoAcesso = tipoAcessoDao.list("select t from Tipoacesso t order by t.descricao");
        if (listaTipoAcesso == null || listaTipoAcesso.isEmpty()) {
            listaTipoAcesso = new ArrayList<Tipoacesso>();
        }
    }

    public void cancelar() {
        RequestContext.getCurrentInstance().closeDialog(new Usuario());
    }

    public void salvar() {
        boolean naoExisteLogin;
        usuario.setTipoacesso(tipoacesso);
        if (eCliente && cliente != null) {
            usuario.setCliente(cliente.getIdcliente());
        } else {
            usuario.setCliente(0);
        }
        String msg = validarDados();
        if (msg.length() < 5) {
            naoExisteLogin = verificacaoLogin();
            if (usuario.getIdusuario() == null) {
                if (naoExisteLogin) {
                    try {
                        usuario.setSenha(Criptografia.encript(usuario.getSenha()));
                        usuario = usuarioDao.update(usuario);
                        RequestContext.getCurrentInstance().closeDialog(usuario);
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    mensagem mensagem = new mensagem();
                    mensagem.existeLogin();
                }
            } else {
                // TODO Auto-generated catch block

                usuario = usuarioDao.update(usuario);
                RequestContext.getCurrentInstance().closeDialog(usuario);
            }
        } else {
            mensagem mensagem = new mensagem();
            mensagem.notificacao(msg);
        }
    }

    public String validarDados() {
        String mensagem = "";
        if (usuario.getNome().equalsIgnoreCase("")) {
            mensagem = mensagem + " Voc� N�o informou seu nome \r\n";
        }
        if (usuario.getLogin().equalsIgnoreCase("")) {
            mensagem = mensagem + " Voc� n�o informou seu login \r\n";
        }
        if (usuario.getSenha().equalsIgnoreCase("")) {
            mensagem = mensagem + " Voc� n�o informou sua senha \r\n";
        }
        return mensagem;
    }

    public boolean verificacaoLogin() {
        Usuario user = usuarioDao.find("Select u From Usuario u Where u.Login='" + usuario.getLogin() + "'");
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }

    public void gerarListaCliente() {
        // TODO Auto-generated catch block

        listaCliente = clienteDao.list("Select From Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }
    }

    public void habilitarEscolhaCliente() {
        if (eCliente) {
            habilitarCliente = true;
        } else {
            habilitarCliente = false;
        }
    }

}
