package br.com.financemate.manageBean;

import br.com.financemate.dao.UsuarioDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.model.Usuario;
import br.com.financemate.util.Criptografia;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

@Named
@ViewScoped
public class UsuarioMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private Usuario usuario;
    private List<Usuario> listaUsuario;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private UsuarioDao usuarioDao;

    @PostConstruct
    public void init() {
        gerarListaUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public void gerarListaUsuario() {
        // TODO Auto-generated catch block
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            listaUsuario = usuarioDao.list("Select u From Usuario u Where u.cliente=" + usuarioLogadoMB.getUsuario().getCliente());
        }else{
            listaUsuario = usuarioDao.list("Select u From Usuario u");
        }
        if (listaUsuario == null || listaUsuario.isEmpty()) {
            listaUsuario = new ArrayList<Usuario>();
        }
    }

    public void retornoDialogNovo(SelectEvent event) {
        Usuario usuario = (Usuario) event.getObject();
        if (usuario.getIdusuario() != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
            gerarListaUsuario();
        }
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public String editar(Usuario usuario) {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getAusuario()) {
            if (usuario != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
                session.setAttribute("usuario", usuario);
                Map<String, Object> options = new HashMap<String, Object>();
                options.put("contentWidth", 450);
                options.put("closable", false);
                RequestContext.getCurrentInstance().openDialog("cadUsuario", options, null);
            }
        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";
    }

    public String novo() {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getIusuario()) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 450);
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadUsuario", options, null);
            return "";

        } else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    
    public void resetarSenhaUsuario(Usuario usuario) {
        String senhaResetada;
        try {
            senhaResetada = Criptografia.encript("senha");
            if (usuario != null) {
                usuario.setSenha(senhaResetada);
                usuarioDao.update(usuario);
                mensagem mensagem = new mensagem();
                mensagem.resetaSenha();
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
