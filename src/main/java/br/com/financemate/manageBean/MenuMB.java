package br.com.financemate.manageBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.model.Contasreceber;

@Named
@SessionScoped
public class MenuMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init(){
		
	}

	public String contasPagar(){
		return "consContaPagar";
	}
	
	public String principal(){
		return "principal";
	}
	
	public String FinanceMate(){
		return "http://www.financemate.com.br/";
	}
	
	
	public String relatorioContasPagar(){
		return "imprimir";
	}
	
	public String consContasReceber(){
		return "consContasReceber";
	}
	
	public String consCliente(){
		return "consCliente";
	}
	
	public String consBanco(){
		return "consBanco";
	}
	
	public String consTipoPlanoConta(){
		return "consTipoPlanoConta";
	}
	
	public String consOutrosLancamentos(){
		return "consOutrosLancamentos";
	}
	
	public String consPlanoConta(){
		return "consPlanoConta";
	}
	
	public String consVendas(){
		return "consVendas";
	}
	
	public String consProduto(){
		return "consProduto";
	}
	
	public String consUsuario(){
		return "consUsuario";
	}
	
	
	public String cobranca() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		List<Contasreceber> listaContasSelecionadas = (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
		if (listaContasSelecionadas != null) {
	        Map<String, Object> options = new HashMap<String, Object>();
	        options.put("contentWidth", 500);
	        RequestContext.getCurrentInstance().openDialog("cobranca"); 
	        return "";
		}else{
			mensagem mensagem = new mensagem();
			mensagem.cobrancasNaoSelecionadas();
			return "";
		}
    }
	
	
	

}
