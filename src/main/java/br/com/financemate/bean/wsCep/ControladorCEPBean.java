/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.bean.wsCep;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anderson
 */
public class ControladorCEPBean implements Serializable {

    private List<EnderecoBean> listagem = new ArrayList<EnderecoBean>();
    private EnderecoBean endereco;
    private String cep;
    private ServicoEndereco servico = new ServicoEndereco();

    public EnderecoBean carregarEndereco() {
        endereco = new EnderecoBean();
        Client c = Client.create();
        WebResource wr = c.resource("http://viacep.com.br/ws/" + this.getCep() + "/json/");
        this.endereco = servico.buscarEnderecoPor(wr.get(String.class));
        String JSON = wr.get(String.class);
        return this.endereco;
    }

    public List<EnderecoBean> getListagem() {
        return listagem;
    }

    public void setListagem(List<EnderecoBean> listagem) {
        this.listagem = listagem;
    }

    public EnderecoBean getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoBean endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public ServicoEndereco getServico() {
        return servico;
    }

    public void setServico(ServicoEndereco servico) {
        this.servico = servico;
    }
}
