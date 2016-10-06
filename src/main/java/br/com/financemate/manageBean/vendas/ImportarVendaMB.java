package br.com.financemate.manageBean.vendas;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ProdutoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;
import br.com.financemate.model.Vendas;
import javax.ejb.EJB;

public class ImportarVendaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendas vendas;
	private VendasSystmBean vendasSystmBean;
	private List<VendasSystmBean> listaVendasSystm;
	private Cliente cliente;
	private Produto produto;
        @EJB
        private ClienteDao clienteDao;
        @EJB
        private ProdutoDao produtoDao;
        
	
	@PostConstruct
	public void init(){
		getListaVendasSystm();
	}


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}


	public VendasSystmBean getVendasSystmBean() {
		return vendasSystmBean;
	}


	public void setVendasSystmBean(VendasSystmBean vendasSystmBean) {
		this.vendasSystmBean = vendasSystmBean;
	}
	
	

	
	public void setListaVendasSystm(List<VendasSystmBean> listaVendasSystm) {
		this.listaVendasSystm = listaVendasSystm;
	}


	public void getListaVendasSystm(){
		importaVendasBean importaVendasBean = new importaVendasBean();
		try {
			listaVendasSystm = importaVendasBean.pegarListaVendasSystm();
			if (listaVendasSystm == null || listaVendasSystm.isEmpty()) {
				listaVendasSystm = new ArrayList<VendasSystmBean>();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	public void importaVenda(VendasSystmBean vendasSystmBean){
//		ClienteFacade clienteFacade = new ClienteFacade();
//		ProdutoFacade produtoFacade = new ProdutoFacade();
//		try {
//			cliente = clienteFacade.consultarUnidade(vendasSystmBean.getIdUnidade());
//			if (cliente != null) {
//				gerarListaProduto();
//				produto = produtoFacade.consultarProduto(vendasSystmBean.getIdProduto(), cliente.getIdcliente());
//			}
//			vendas.setNomeFornecedor(vendasSystmBean.getFornecedor());
//			vendas.setConsultor(vendasSystmBean.getConsultor());
//			vendas.setNomeCliente(vendasSystmBean.getNomeCliente());
//			vendas.setValorBruto(vendasSystmBean.getValorBruto());
//			calculoTotalVenda();
//			vendas.setDataVenda(vendasSystmBean.getDataVenda());
//			vendas.setComissaoLiquidaTotal(vendasSystmBean.getLiquidoFranquia());
//			calculoValoresBackOffice();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
