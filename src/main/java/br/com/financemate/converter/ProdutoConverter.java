package br.com.financemate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financemate.model.Produto;

@FacesConverter(value="ProdutoConverter")
public class ProdutoConverter implements Converter{
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Produto> listaProduto = (List<Produto>) arg1.getAttributes().get("listaProduto");
	    if (listaProduto != null) {
	        for (Produto produto : listaProduto) {
	            if (produto.getDescricao().equalsIgnoreCase(arg2)) {
	                return produto;
	            }
	        }
	    } else {
	        Produto produto = new Produto();
	        return produto;
	    }
	    Produto produto = new Produto();
	    return produto;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	    	Produto produto = (Produto) arg2;
	        return produto.getDescricao();
	    }
	}
}
