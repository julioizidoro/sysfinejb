package br.com.financemate.manageBean.outrosLancamentos;

import java.util.List;


@SuppressWarnings("unchecked")
public class ConciliacaoFactory {

	private static List<ConciliacaoBean> listaConciliacao;

    public static List<ConciliacaoBean> getListaConciliacao() {
        return listaConciliacao;
    }

    public static void setListaConciliacao(List<ConciliacaoBean> listaConciliacao) {
        ConciliacaoFactory.listaConciliacao = listaConciliacao;
    }
    
    public static List<ConciliacaoBean> listar(){
        return listaConciliacao;
    }
}
