package br.com.financemate.manageBean;

import br.com.financemate.dao.FtpDadosDao;
import br.com.financemate.dao.NomeArquivoDao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Ftpdados;
import br.com.financemate.model.Nomearquivo;
import br.com.financemate.util.Ftp;
import javax.ejb.EJB;

@Named
@ViewScoped
public class consultaArquivoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Contaspagar contaspagar;
    private Nomearquivo nomeArquivo;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private StreamedContent file;
    @EJB
    private FtpDadosDao ftpDadosDao;
    @EJB
    private NomeArquivoDao nomeArquivoDao;
    private Ftpdados ftpdados;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        consultarArquivos();
        ftpdados = ftpDadosDao.find(1);
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Contaspagar getContaspagar() {
        return contaspagar;
    }

    public void setContaspagar(Contaspagar contaspagar) {
        this.contaspagar = contaspagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Nomearquivo getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(Nomearquivo nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Ftpdados getFtpdados() {
        return ftpdados;
    }

    public void setFtpdados(Ftpdados ftpdados) {
        this.ftpdados = ftpdados;
    }
    
    

    public void consultarArquivos() {
        // TODO Auto-generated catch block

        nomeArquivo = nomeArquivoDao.find("Select n From Nomearquivo n Where n.contaspagar.idcontasPagar=" + contaspagar.getIdcontasPagar());
        if (nomeArquivo == null) {
            nomeArquivo = new Nomearquivo();
            nomeArquivo.setNomearquivo01("Não existe arquivo anexado");
        }
    }

    public String voltar() {
        nomeArquivo = null;
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    public boolean salvarArquivoFTP(String nomeArquivoLocal, String nomeArquivoFTP) {
        Ftpdados dadosFTP = null;
        dadosFTP = ftpDadosDao.find("Select f From Ftpdados f");
        if (dadosFTP == null) {
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()) {
                mostrarMensagem(null, "Erro conectar FTP", "Erro");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
            ftp.receberArquivo(nomeArquivoLocal, nomeArquivoFTP);
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Salvar Arquivo", "Erro");
            return false;
        }
        try {
            ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
            return false;
        }
        mensagem mensagem = new mensagem();
        mensagem.downloadSucesso();
        return true;

    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public boolean desabilitarDownload() {
        if (nomeArquivo.getNomearquivo01().equalsIgnoreCase("Não existe arquivo anexado")) {
            return false;
        } else {
            return true;
        }
    }
    
    
    public void pegarFtpDados(){
        ftpdados = ftpDadosDao.find(1);
    }
    
    
    public String pegarCaminho(){
        ftpdados = ftpDadosDao.find(1);
        return "//http://" + ftpdados.getHost() + ":82/ftproot/sysfin/contasPagar/ContasPagar-ContratoEstagioAustralia-8.pdf";
    }

}
