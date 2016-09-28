package br.com.financemate.manageBean.contasReceber;

import java.util.List;

import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;


public class RelatorioCobrancaBean {
	
	private Cobranca cobranca;
	private List<Contasreceber> listaContas;
	private float valorTotal;
	private Contasreceber contasreceber;
	
	public Cobranca getCobranca() {
		return cobranca;
	}
	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}
	public List<Contasreceber> getListaContas() {
		return listaContas;
	}
	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}
	
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Contasreceber getContasreceber() {
		return contasreceber;
	}
	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
	}
	
	

}
