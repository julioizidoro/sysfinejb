package br.com.financemate.manageBean.vendas;

import java.util.Date;
import java.util.List;

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
    private String tipoPagamento;
    private int nParcela;
    private Date dataPagamento;
    private Float valorParcelamento;
    private Float valorParcela;
    private List<VendasSystmBean> lista;

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

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public int getnParcela() {
        return nParcela;
    }

    public void setnParcela(int nParcela) {
        this.nParcela = nParcela;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Float getValorParcelamento() {
        return valorParcelamento;
    }

    public void setValorParcelamento(Float valorParcelamento) {
        this.valorParcelamento = valorParcelamento;
    }

    public Float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public List<VendasSystmBean> getLista() {
        return lista;
    }

    public void setLista(List<VendasSystmBean> lista) {
        this.lista = lista;
    }

}
