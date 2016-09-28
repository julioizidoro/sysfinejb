package br.com.financemate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financemate.model.Planocontas;

@FacesConverter(value="PlanoContasConverter")
public class PlanoContasConverter implements Converter{
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Planocontas> listaPlanoConta = (List<Planocontas>) arg1.getAttributes().get("listaPlanoConta");
        for (Planocontas planoConta : listaPlanoConta) {
                if (planoConta.getDescricao().equalsIgnoreCase(arg2)) {
                    return planoConta;
                }
        }
        return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
            return "Selecione";
        } else {
            Planocontas planoConta = (Planocontas) arg2;
            return planoConta.getDescricao();
        }
	}
}
