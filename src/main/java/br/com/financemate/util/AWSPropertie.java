package br.com.financemate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AWSPropertie {
	
	private String bucketOrcamento;
	private String bucketArquivo;
	private String bucketImagem;
	private String bucketRemessa;
	private String bucketDocs;
	private String bucketTreinamento;
	private String bucketLocal;
	private String bucketSysfin;
	private String clientRegion;
	private String accesskey;
	private String secretaccesskey;

	public AWSPropertie() {
		
	}

	public String getBucketOrcamento() {
		return bucketOrcamento;
	}

	public void setBucketOrcamento(String bucketOrcamento) {
		this.bucketOrcamento = bucketOrcamento;
	}

	public String getBucketArquivo() {
		return bucketArquivo;
	}

	public void setBucketArquivo(String bucketArquivo) {
		this.bucketArquivo = bucketArquivo;
	}

	public String getBucketImagem() {
		return bucketImagem;
	}

	public void setBucketImagem(String bucketImagem) {
		this.bucketImagem = bucketImagem;
	}

	public String getBucketRemessa() {
		return bucketRemessa;
	}

	public void setBucketRemessa(String bucketRemessa) {
		this.bucketRemessa = bucketRemessa;
	}

	public String getBucketDocs() {
		return bucketDocs;
	}

	public void setBucketDocs(String bucketDocs) {
		this.bucketDocs = bucketDocs;
	}

	public String getClientRegion() {
		return clientRegion;
	}

	public void setClientRegion(String clientRegion) {
		this.clientRegion = clientRegion;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getSecretaccesskey() {
		return secretaccesskey;
	}

	public void setSecretaccesskey(String secretaccesskey) {
		this.secretaccesskey = secretaccesskey;
	}
	
	public String getBucketTreinamento() {
		return bucketTreinamento;
	}

	public void setBucketTreinamento(String bucketTreinamento) {
		this.bucketTreinamento = bucketTreinamento;
	}

	public String getBucketLocal() {
		return bucketLocal;
	}

	public void setBucketLocal(String bucketLocal) {
		this.bucketLocal = bucketLocal;
	}

	public String getBucketSysfin() {
		return bucketSysfin;
	}

	public void setBucketSysfin(String bucketSysfin) {
		this.bucketSysfin = bucketSysfin;
	}

	public void carregarInformacoes(String caminho) {
		File file = new File(caminho);
        Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            props.load(fis);
            fis.close();
            setBucketOrcamento(props.getProperty("bucketOrcamento"));
        	setBucketArquivo(props.getProperty("bucketArquivo"));
        	setBucketImagem(props.getProperty("bucketImagem"));
        	setBucketRemessa(props.getProperty("bucketRemessa"));
        	setBucketDocs(props.getProperty("bucketDocs"));
        	setBucketTreinamento(props.getProperty("bucketTreinamento"));
        	setBucketLocal(props.getProperty("bucketLocal"));
        	setBucketSysfin("bucketSysfin");
        	setClientRegion(props.getProperty("clientRegion"));
        	setAccesskey(props.getProperty("accesskey"));
        	setSecretaccesskey(props.getProperty("secretaccesskey"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
	}

}
