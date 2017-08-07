package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.model.Movimentobanco;

public class ConciliarBean {

    private Movimentobanco outroslancamentos;
    private TransacaoBean transacao;

    public Movimentobanco getOutroslancamentos() {
        return outroslancamentos;
    }

    public void setOutroslancamentos(Movimentobanco outroslancamentos) {
        this.outroslancamentos = outroslancamentos;
    }

    public TransacaoBean getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoBean transacao) {
        this.transacao = transacao;
    }
}
