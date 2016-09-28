package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.financemate.model.Banco;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.util.Formatacao;
import javax.ejb.EJB;

@Named
@ViewScoped
public class ConciliacaoMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Banco banco;
    private List<Outroslancamentos> listaLacamentos;
    private List<TransacaoBean> listaTransacao;
    private Date dataInicial;
    private Date dataFinal;
    private UploadedFile arquivo;
    private List<ConciliarBean> listaConciliacaoBancaria;
    private String nomeBotaoConciliar = "Conciliar";
    private List<TransacaoBean> listaTransacaoNaoConciliado;
    private List<Outroslancamentos> listaOutrosNaoConciliado;
    private Integer sizeConciliada;
    private TransacaoBean transacaoBean;
    private Outroslancamentos outroslancamentos;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;

    public String getNomeBotaoConciliar() {
        return nomeBotaoConciliar;
    }

    public void setNomeBotaoConciliar(String nomeBotaoConciliar) {
        this.nomeBotaoConciliar = nomeBotaoConciliar;
    }

    public List<TransacaoBean> getListaTransacaoNaoConciliado() {
        return listaTransacaoNaoConciliado;
    }

    public void setListaTransacaoNaoConciliado(List<TransacaoBean> listaTransacaoNaoConciliado) {
        this.listaTransacaoNaoConciliado = listaTransacaoNaoConciliado;
    }

    public List<Outroslancamentos> getListaOutrosNaoConciliado() {
        return listaOutrosNaoConciliado;
    }

    public void setListaOutrosNaoConciliado(List<Outroslancamentos> listaOutrosNaoConciliado) {
        this.listaOutrosNaoConciliado = listaOutrosNaoConciliado;
    }

    public Integer getSizeConciliada() {
        return sizeConciliada;
    }

    public void setSizeConciliada(Integer sizeConciliada) {
        this.sizeConciliada = sizeConciliada;
    }

    public TransacaoBean getTransacaoBean() {
        return transacaoBean;
    }

    public void setTransacaoBean(TransacaoBean transacaoBean) {
        this.transacaoBean = transacaoBean;
    }

    public Outroslancamentos getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Outroslancamentos outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public List<ConciliarBean> getListaConciliacaoBancaria() {
        return listaConciliacaoBancaria;
    }

    public void setListaConciliacaoBancaria(List<ConciliarBean> listaConciliacaoBancaria) {
        this.listaConciliacaoBancaria = listaConciliacaoBancaria;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Outroslancamentos> getListaLacamentos() {
        return listaLacamentos;
    }

    public void setListaLacamentos(List<Outroslancamentos> listaLacamentos) {
        this.listaLacamentos = listaLacamentos;
    }

    public List<TransacaoBean> getListaTransacao() {
        return listaTransacao;
    }

    public void setListaTransacao(List<TransacaoBean> listaTransacao) {
        this.listaTransacao = listaTransacao;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public OutrosLancamentosDao getOutrosLancamentosDao() {
        return outrosLancamentosDao;
    }

    public void setOutrosLancamentosDao(OutrosLancamentosDao outrosLancamentosDao) {
        this.outrosLancamentosDao = outrosLancamentosDao;
    }

    public BancoDao getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }

    public void carregarArquivo(FileUploadEvent e) {
        listaConciliacaoBancaria = null;
        arquivo = e.getFile();
        File arq = new File(arquivo.getFileName());
        FileInputStream file = null;
        try {
            file = (FileInputStream) arquivo.getInputstream();
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        LerOFXBean ler = new LerOFXBean();
        ler.iniciarLeitura(file);
        listaTransacao = ler.getListaTransacao();
        if ((ler.getAgencia() != null) && (ler.getConta() != null)) {
            consultarBanco(ler.getAgencia(), ler.getConta());
        }
        if (banco != null) {
            dataInicial = listaTransacao.get(0).getData();
            dataFinal = listaTransacao.get(listaTransacao.size() - 1).getData();
            carregarOutrosLancamentos();
            if (listaLacamentos != null) {
                conciliar();
            }
        }

    }

    public void consultarBanco(String agencia, String conta) {
        String sql = "SELECT b FROM Banco b where b.agencia=" + agencia
                + " and b.conta=" + conta;
        List<Banco> listaBanco = bancoDao.list(sql);
        if (listaBanco != null) {
            if (listaBanco.size() > 0) {
                banco = listaBanco.get(0);
            }
        }
    }

    public void carregarOutrosLancamentos() {
        String sql = "SELECT o FROM Outroslancamentos o where o.dataCompensacao>='"
                + dataInicial + "' and o.dataCompensacao<='" + dataFinal + "' and o.banco.idbanco=" + banco.getIdbanco();
        listaLacamentos = outrosLancamentosDao.list(sql);
        if (listaLacamentos == null || listaLacamentos.isEmpty()) {
            listaLacamentos = new ArrayList<Outroslancamentos>();
        }
    }

    public void conciliar() {
        for (int i = 0; i < listaTransacao.size(); i++) {
            verficarLancamentos(listaTransacao.get(i));
        }
        sizeConciliada = listaConciliacaoBancaria.size();
        for (int i = 0; i < listaTransacao.size(); i++) {
            gerarListaTransacaoNaoConciliada(listaTransacao.get(i));
        }
        gerarListaOutrosLancamentosNaoConciliado();
        for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
            if (listaConciliacaoBancaria.get(i).getOutroslancamentos() == null) {
                listaConciliacaoBancaria.get(i).setOutroslancamentos(new Outroslancamentos());
                listaConciliacaoBancaria.get(i).getOutroslancamentos().setSelecionado(false);
            }
        }
    }

    public void verficarLancamentos(TransacaoBean transacao) {
        ConciliarBean cb = new ConciliarBean();
        if (listaConciliacaoBancaria == null) {
            listaConciliacaoBancaria = new ArrayList<ConciliarBean>();
        }
        for (int j = 0; j < listaLacamentos.size(); j++) {
            Float valor;
            String dataTransacao = Formatacao.ConvercaoDataPadrao(transacao.getData());
            String dataLancamento = Formatacao.ConvercaoDataPadrao(listaLacamentos.get(j).getDataCompensacao());
            if (transacao.getTipo().equalsIgnoreCase("DEBIT")) {
                valor = transacao.getValorSaida();
                if ((listaLacamentos.get(j).getValorSaida().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))) {
                    listaLacamentos.get(j).setConciliacao(transacao.getId());
                    listaLacamentos.get(j).setConciliada(true);
                    transacao.setConciliada(true);
                    cb = new ConciliarBean();
                    cb.setOutroslancamentos(listaLacamentos.get(j));
                    cb.setTransacao(transacao);
                    listaConciliacaoBancaria.add(cb);
                }
            } else if (transacao.getTipo().equalsIgnoreCase("CREDIT")) {
                valor = transacao.getValorEntrada();
                if ((listaLacamentos.get(j).getValorEntrada().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))) {
                    listaLacamentos.get(j).setConciliacao(transacao.getId());
                    listaLacamentos.get(j).setConciliada(true);
                    transacao.setConciliada(true);
                    cb = new ConciliarBean();
                    cb.setOutroslancamentos(listaLacamentos.get(j));
                    cb.setTransacao(transacao);
                    listaConciliacaoBancaria.add(cb);
                }
            }
        }
    }

    public void gerarListaTransacaoNaoConciliada(TransacaoBean transacao) {
        ConciliarBean conciliarBean = new ConciliarBean();
        if (listaTransacaoNaoConciliado == null) {
            listaTransacaoNaoConciliado = new ArrayList<TransacaoBean>();
        }
        if (transacao.getConciliada() == false) {
            listaTransacaoNaoConciliado.add(transacao);
            conciliarBean.setTransacao(transacao);
            listaConciliacaoBancaria.add(conciliarBean);
        }

    }

    public void gerarListaOutrosLancamentosNaoConciliado() {
        ConciliarBean conciliarBean = new ConciliarBean();
        if (listaOutrosNaoConciliado == null) {
            listaOutrosNaoConciliado = new ArrayList<Outroslancamentos>();
        }
        for (int i = 0; i < listaLacamentos.size(); i++) {
            if (listaLacamentos.get(i).getConciliada() == false) {
                listaOutrosNaoConciliado.add(listaLacamentos.get(i));
                conciliarBean.setOutroslancamentos(listaLacamentos.get(i));
                listaConciliacaoBancaria.get(sizeConciliada + i).setOutroslancamentos(conciliarBean.getOutroslancamentos());

            }
        }
    }

    public void retornoDialogConciliado(SelectEvent e) {
        listaConciliacaoBancaria = null;
        carregarOutrosLancamentos();
        conciliar();
    }

}
