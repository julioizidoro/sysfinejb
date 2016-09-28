package br.com.financemate.manageBean.vendas;

import java.util.Date;

public class VendasSystmBean {
	
	 private int idProduto;
	    private Date dataVenda;
	    private int idCliente;
	    private String fornecedor;
	    private Float valorBruto;
	    private Float liquidoFranquia;
	    private Float valorFormaPagamento;
	    private String nomeCliente;
	    private String consultor;
	    private int idVenda;
	    private int idUsuario;
	    private int idUnidade;
	    

	    public int getIdProduto() {
	        return idProduto;
	    }

	    public void setIdProduto(int idProduto) {
	        this.idProduto = idProduto;
	    }

	    public Date getDataVenda() {
	        return dataVenda;
	    }

	    public void setDataVenda(Date dataVenda) {
	        this.dataVenda = dataVenda;
	    }

	    public int getIdCliente() {
	        return idCliente;
	    }

	    public void setIdCliente(int idCliente) {
	        this.idCliente = idCliente;
	    }

	    public String getFornecedor() {
	        return fornecedor;
	    }

	    public void setFornecedor(String fornecedor) {
	        this.fornecedor = fornecedor;
	    }

	    public Float getValorBruto() {
	        return valorBruto;
	    }

	    public void setValorBruto(Float valorBruto) {
	        this.valorBruto = valorBruto;
	    }

	    public Float getLiquidoFranquia() {
	        return liquidoFranquia;
	    }

	    public void setLiquidoFranquia(Float liquidoFranquia) {
	        this.liquidoFranquia = liquidoFranquia;
	    }

	    public Float getValorFormaPagamento() {
	        return valorFormaPagamento;
	    }

	    public void setValorFormaPagamento(Float valorFormaPagamento) {
	        this.valorFormaPagamento = valorFormaPagamento;
	    }

	    public String getNomeCliente() {
	        return nomeCliente;
	    }

	    public void setNomeCliente(String nomeCliente) {
	        this.nomeCliente = nomeCliente;
	    }

	    public String getConsultor() {
	        return consultor;
	    }

	    public void setConsultor(String consultor) {
	        this.consultor = consultor;
	    }

	    public int getIdVenda() {
	        return idVenda;
	    }

	    public void setIdVenda(int idVenda) {
	        this.idVenda = idVenda;
	    }

	    public int getIdUsuario() {
	        return idUsuario;
	    }

	    public void setIdUsuario(int idUsuario) {
	        this.idUsuario = idUsuario;
	    }
	    
	    public int getIdUnidade() {
	        return idUnidade;
	    }

	    public void setIdUnidade(int idUnidade) {
	        this.idUnidade = idUnidade;
	    }

}
