package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.model.Outroslancamentos;

public class ConciliarBean {
	
	private Outroslancamentos outroslancamentos;
	private TransacaoBean transacao;
	
	
	
	public Outroslancamentos getOutroslancamentos() {
		return outroslancamentos;
	}
	public void setOutroslancamentos(Outroslancamentos outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}
	public TransacaoBean getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoBean transacao) {
		this.transacao = transacao;
	}
}
