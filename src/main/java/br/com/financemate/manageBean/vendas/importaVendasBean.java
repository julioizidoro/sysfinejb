package br.com.financemate.manageBean.vendas;

import br.com.financemate.manageBean.mensagem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class importaVendasBean {

    private static int HTTP_COD_SUCESSO = 200;

    public VendasSystmBean pegarInformacao() throws JAXBException {
        VendasSystmBean vendaImportada = new VendasSystmBean();
        try {

            URL url = new URL("http://systm.com.br:8087/wssysfin/webresources/generic/retornoVenda");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() != HTTP_COD_SUCESSO) {
                throw new RuntimeException("HTTP error code : " + con.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            Gson gson = new Gson();
            JsonReader j = new JsonReader(br);
            j.setLenient(true);
            vendaImportada = gson.fromJson(j, VendasSystmBean.class);
            br.close();
            con.disconnect();

        } catch (MalformedURLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        } catch (IOException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        return vendaImportada;
    }

    public List<VendasSystmBean> pegarListaVendasSystm() throws JAXBException {
        List<VendasSystmBean> listaVendas = new ArrayList<>();
        VendasSystmBean[] vendasSystmBean;
        try {

            URL url = new URL("http://systm.com.br:8087/wssysfin/webresources/generic/listaVenda");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() != HTTP_COD_SUCESSO) {
                throw new RuntimeException("HTTP error code : " + con.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            Gson gson = new Gson();
            JsonReader j = new JsonReader(br);
            j.setLenient(true);
            vendasSystmBean = gson.fromJson(j, VendasSystmBean[].class);
            for (int i = 0; i < vendasSystmBean.length; i++) {
                listaVendas.add(vendasSystmBean[i]);
            }
            br.close();
            con.disconnect();

        } catch (MalformedURLException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        } catch (IOException e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
        return listaVendas;
    }

    public void salvarVendaImportada(int idVendaSystm) throws JAXBException {
        try {

            Gson gson = new Gson();
            String vendasSystmJSon = gson.toJson(idVendaSystm);

            try {
                URL url = new URL("http://systm.com.br:8087/wssysfin/webresources/generic/salvarImportacao");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(vendasSystmJSon);
                out.close();
                int http_status = connection.getResponseCode();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                mensagem m = new mensagem();
                m.faltaInformacao("" + e);
            }
        } catch (Exception e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
    }
}
