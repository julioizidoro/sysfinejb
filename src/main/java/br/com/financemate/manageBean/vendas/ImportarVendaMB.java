package br.com.financemate.manageBean.vendas;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import br.com.financemate.model.Vendas;

public class ImportarVendaMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Vendas vendas;
    private VendasSystmBean vendasSystmBean;
    private List<VendasSystmBean> listaVendasSystm;

    @PostConstruct
    public void init() {
        // getListaVendasSystm();
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

//    public void getListaVendasSystm() {
//        importaVendasBean importaVendasBean = new importaVendasBean();
//        try {
//            listaVendasSystm = importaVendasBean.pegarListaVendasSystm();
//            if (listaVendasSystm == null || listaVendasSystm.isEmpty()) {
//                listaVendasSystm = new ArrayList<>();
//            }
//        } catch (JAXBException e) {
//            mensagem m = new mensagem();
//            m.faltaInformacao("" + e);
//        }
//    }
}
