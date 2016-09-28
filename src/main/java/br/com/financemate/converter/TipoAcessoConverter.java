package br.com.financemate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financemate.model.Tipoacesso;

@FacesConverter(value = "TipoAcessoConverter")
public class TipoAcessoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Tipoacesso> listaTipoAcesso = (List<Tipoacesso>) arg1.getAttributes().get("listaTipoAcesso");
        for (Tipoacesso tipoacesso : listaTipoAcesso) {
                if (tipoacesso.getDescricao().equalsIgnoreCase(arg2)) {
                    return tipoacesso;
                }
            }
        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	    	Tipoacesso tipoacesso = (Tipoacesso) arg2;
	        return tipoacesso.getDescricao();
	    }
	}

}
