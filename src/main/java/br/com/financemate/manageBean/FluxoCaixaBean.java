package br.com.financemate.manageBean;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.FluxoCaixaDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Fluxocaixa;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

public class FluxoCaixaBean {

    private List<Fluxocaixa> listaFluxo;
    private Cliente cliente;
    private float saldo = 0.0f;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private FluxoCaixaDao fluxoCaixaDao;
    @EJB 
    private ContasReceberDao contasReceberDao;
    private List<Contaspagar> listaContasPagar;
    private List<Contasreceber> listaContasReceber;

    public FluxoCaixaBean(Date dataInicial, Date dataFinal, Cliente cliente, String tipo, List<Contaspagar> listaContasPagar, List<Contasreceber> listaContasReceber) {
        this.cliente = cliente;
        this.listaContasPagar = listaContasPagar;
        this.listaContasReceber = listaContasReceber;
        carregarDias(dataInicial, dataFinal);
        gerarListaContasPagar(dataInicial, dataFinal, cliente);
        gerarListaContasReceber(dataInicial, dataFinal, cliente);
        salvarLista();
    }

    public void carregarDias(Date dataInicial, Date dataFinal) {
        listaFluxo = new ArrayList<Fluxocaixa>();
        Date dataAtual = dataInicial;
        while (!dataAtual.after(dataFinal)) {
            Fluxocaixa fluxo = new Fluxocaixa();
            fluxo.setCliente(cliente);
            fluxo.setData(dataAtual);
            fluxo.setValorContasPagar(0.0f);
            fluxo.setValorContasReceber(0.0f);
            fluxo.setSaldo(0.0f);
            listaFluxo.add(fluxo);
            dataAtual = Formatacao.SomarDiasData(dataAtual, 1);
        }
    }

    public void gerarListaContasPagar(Date dataInicial, Date dataFinal, Cliente cliente) {
        if (listaContasPagar != null) {
            for (int i = 0; i < listaContasPagar.size(); i++) {
                acharDataContasPagar(listaContasPagar.get(i));
            }
        }
    }

    public void acharDataContasPagar(Contaspagar contasPagar) {
        for (int i = 0; i < listaFluxo.size(); i++) {
            String data1 = Formatacao.ConvercaoDataPadrao(listaFluxo.get(i).getData());
            String data2 = Formatacao.ConvercaoDataPadrao(contasPagar.getDataVencimento());
            if (data1.equalsIgnoreCase(data2)) {
                listaFluxo.get(i).setValorContasPagar(contasPagar.getValor().floatValue());
                i = listaFluxo.size() + 10;
            }
        }
    }

    public void gerarListaContasReceber(Date dataInicial, Date dataFinal, Cliente cliente) {
        if (listaContasReceber != null) {
            for (int i = 0; i < listaContasReceber.size(); i++) {
                acharDataContasReceber(listaContasReceber.get(i));
            }
        }
    }

    public void acharDataContasReceber(Contasreceber contasReceber) {
        for (int i = 0; i < listaFluxo.size(); i++) {
            String data1 = Formatacao.ConvercaoDataPadrao(listaFluxo.get(i).getData());
            String data2 = Formatacao.ConvercaoDataPadrao(contasReceber.getDataVencimento());
            if (data1.equalsIgnoreCase(data2)) {
                if (contasReceber.getValorParcela() == null) {
                    contasReceber.setValorParcela(0.0f);
                }
                listaFluxo.get(i).setValorContasReceber(contasReceber.getValorParcela().floatValue());
                i = listaFluxo.size() + 10;
            }
        }
    }

    public void salvarLista() {
        if (listaFluxo != null) {
            for (int i = 0; i < listaFluxo.size(); i++) {
                Fluxocaixa fluxo = listaFluxo.get(i);
                saldo = saldo + (fluxo.getValorContasReceber() - fluxo.getValorContasPagar());
                fluxo.setSaldo(saldo);
                //fluxoCaixaDao.update(fluxo);
            }
        }
    }
}
