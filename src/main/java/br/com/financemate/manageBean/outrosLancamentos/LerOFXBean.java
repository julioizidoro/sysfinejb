package br.com.financemate.manageBean.outrosLancamentos;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.ofx4j.domain.data.MessageSetType;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.banking.BankStatementResponse;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.banking.BankingResponseMessageSet;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.io.AggregateUnmarshaller;

public class LerOFXBean {
	
	private List<TransacaoBean> listaTransacao;
	private String agencia;
	private String conta;
	
	public void iniciarLeitura(FileInputStream file){

		try{
			AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(ResponseEnvelope.class);
			ResponseEnvelope envelope = unmarshaller.unmarshal(file);
			BankingResponseMessageSet messageSet = (BankingResponseMessageSet) envelope.getMessageSet(MessageSetType.banking);
			if (messageSet != null) {
				List<BankStatementResponseTransaction> listBanck = messageSet.getStatementResponses();
				for (BankStatementResponseTransaction b : listBanck) {
					String dados = b.getMessage().getAccount().getAccountNumber();
					agencia = dados.substring(0, 4);
					conta = dados.substring(4,dados.length()-1);
					System.out.println("cc: " + b.getMessage().getAccount().getAccountNumber());
					System.out.println("ag: " + b.getMessage().getAccount().getBranchId());
					System.out.println("balanço final: " + b.getMessage().getLedgerBalance().getAmount());
					System.out.println("dataDoArquivo: " + b.getMessage().getLedgerBalance().getAsOfDate());
					BankStatementResponse message = b.getMessage();
					List<Transaction> listTransactions = message.getTransactionList().getTransactions();
					listaTransacao = new ArrayList<TransacaoBean>();
					for (Transaction transaction : listTransactions) {
						TransacaoBean transacao = new TransacaoBean();
						transacao.setTipo(transaction.getTransactionType().name());
						transacao.setId(transaction.getId());
						transacao.setData(transaction.getDatePosted());
						Double valor = transaction.getAmount();
						if (valor<0){
							transacao.setValorSaida(valor.floatValue()*-1);
							transacao.setValorEntrada(0.0f);
						}else {
							transacao.setValorEntrada(valor.floatValue());
							transacao.setValorSaida(0.0f);
						}
						transacao.setDescricao(transaction.getMemo());
						listaTransacao.add(transacao);
					}
				}
			}
		}catch (Exception ex){
			
		}
	}

	public List<TransacaoBean> getListaTransacao() {
		return listaTransacao;
	}

	public void setListaTransacao(List<TransacaoBean> listaTransacao) {
		this.listaTransacao = listaTransacao;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}
	
	
	
	
}