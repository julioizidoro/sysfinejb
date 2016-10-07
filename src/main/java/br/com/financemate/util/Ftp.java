package br.com.financemate.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.model.UploadedFile;


public class Ftp {
	
	private FTPClient ftpClient;
    private String host;
    private String user;
    private String password;

    public Ftp(String host, String user, String password)  {
        ftpClient = new FTPClient();
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public boolean conectar() throws IOException{
        ftpClient.connect(host);
        ftpClient.login(user, password);
        if (ftpClient.isConnected()){
            return true;
        }else return false;
    }
    
    public String enviarArquivo(UploadedFile uploadedFile, String arquivoFTP) throws IOException{
        ftpClient.changeWorkingDirectory("/sysfin/contasPagar"); 
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = (FileInputStream) uploadedFile.getInputstream();
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
        	ftpClient.getReplyString();
        	desconectar();
            return "Arquivo: "+ arquivoFTP + " salvo com Sucesso";
        } else {
        	System.out.println(ftpClient.getReplyString());
            return "Erro Salvar Arquivo";
        }
    }
    
    public void desconectar() throws IOException{
        ftpClient.logout();
        ftpClient.disconnect();  
    }
    
    public void receberArquivo(String arquivoSalvar, String arquivoFTP) throws IOException{
        ftpClient.changeWorkingDirectory("/sysfin/contasPagar");
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        String nomeUsuario = System.getProperty("user.name");
        FileOutputStream os = new FileOutputStream("/Users/"+nomeUsuario+"/Downloads/"+arquivoSalvar);
        ftpClient.retrieveFile(arquivoFTP, os);
        os.close(); 
    } 
}
