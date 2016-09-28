package br.com.financemate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financemate.model.Banco;

@FacesConverter(value="BancoConverter")
public class BancoConverter implements Converter {
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Banco> listaBanco = (List<Banco>) arg1.getAttributes().get("listaBanco");
	    if (listaBanco != null) {
	        for (Banco banco : listaBanco) {
	            if (banco.getNome().equalsIgnoreCase(arg2)) {
	                return banco;
	            }
	        }
	    } else {
	        Banco banco = new Banco();
	        return banco;
	    }
	    Banco banco = new Banco();
	    return banco;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	        Banco banco = (Banco) arg2;
	        return banco.getNome();
	    }
	}
}
