/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.bean.wsCep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

/**
 *
 * @author Anderson
 */
public class ServicoEndereco implements Serializable{
    
    public EnderecoBean buscarEnderecoPor(String urlJson) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        EnderecoBean retornoEndereco = gson.fromJson(urlJson, EnderecoBean.class);
        return retornoEndereco;
    }
    
}
