package br.com.financemate.manageBean;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.UsuarioDao;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.util.Criptografia;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Usuario;
import javax.ejb.EJB;

@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private Cliente cliente;
    private String novaSenha;
    private String senhaAtual;
    private String confirmaNovaSenha;
    private String nomeCliente;
    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private ClienteDao clienteDao;
    private String login;
    private String senha;

    public UsuarioLogadoMB() {
        this.usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaNovaSenha() {
        return confirmaNovaSenha;
    }

    public void setConfirmaNovaSenha(String confirmaNovaSenha) {
        this.confirmaNovaSenha = confirmaNovaSenha;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    

    public String validarUsuario() {
        usuario = new Usuario();
        if ((login != null) && (senha == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            String senha = "";
            try {
                
                senha = Criptografia.encript(this.senha);
                this.senha = "";
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
            usuario = usuarioDao.find("Select u From Usuario u Where u.login='" + login + "' and u.senha='" + senha + "'");
            login = "";
            if (usuario == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "Acesso Negado."));
            } else {
                if (usuario.getCliente() > 0) {
                    cliente = clienteDao.find(usuario.getCliente());
                    nomeCliente = cliente.getNomeFantasia();
                } else {
                    cliente = null;
                    nomeCliente = "FINANCEMATE - Assessoria Contavel & Financeira";
                }
                return "principal";
            }
        }
        return "";
    }

    public void erroLogin(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensagem, ""));
    }

    public void validarTrocarSenha() {
        if (usuario == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
        } else {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 300);
            options.put("closable", false);
            RequestContext.getCurrentInstance().openDialog("cadNovaSenha", options, null);
        }
    }
    
    public void validarTrocarSenhas() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 300);
        options.put("closable", false);
        RequestContext.getCurrentInstance().openDialog("cadNovaSenhas", options, null);
    }

    public String confirmaNovaSenha() {
        String repetirSenhaAtual = "";
        try {
            repetirSenhaAtual = Criptografia.encript(senhaAtual);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (repetirSenhaAtual.equalsIgnoreCase(usuario.getSenha())) {

            if ((novaSenha.length() > 0) && (confirmaNovaSenha.length() > 0)) {
                if (novaSenha.equalsIgnoreCase(confirmaNovaSenha)) {
                    String senha = "";
                    try {
                        senha = Criptografia.encript(novaSenha);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                        FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                        FacesContext.getCurrentInstance().addMessage(null, mensagem);
                    }
                    usuario.setSenha(senha);
                    usuario = usuarioDao.update(usuario);
                    novaSenha = "";
                    confirmaNovaSenha = "";
                    RequestContext.getCurrentInstance().closeDialog(usuario);
                    return "";

                } else {
                    novaSenha = "";
                    confirmaNovaSenha = "";
                    senhaAtual = "";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
            }
        } else {
            mensagem mensagem = new mensagem();
            mensagem.alteracao();
            senhaAtual = "";
            novaSenha = "";
            confirmaNovaSenha = "";
        }
        return "";
    }

    public String cancelarTrocaSenha() {
        novaSenha = "";
        confirmaNovaSenha = "";
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

    public void retornoDialogAlteracaoSenha(SelectEvent event) {
        Usuario usuario = (Usuario) event.getObject();
        if (usuario != null) {
            mensagem mensagem = new mensagem();
            mensagem.saveMessagem();
        }
    }

    public String deslogar() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.clear();
        return "index";
    }
    
    
    public boolean habilitarAcessoCliente(){
        if (usuario.getCliente() > 0) {
            return false;
        }else{
            return true;
        }
    }
}
