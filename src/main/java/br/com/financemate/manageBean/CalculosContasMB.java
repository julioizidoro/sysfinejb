package br.com.financemate.manageBean;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.ContasReceberDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.financemate.util.Formatacao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

@Named
@SessionScoped
public class CalculosContasMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private String crVencer;
    private String crVencidas;
    private String crVencendo;
    private String crTotal;
    private String cpVencer;
    private String cpVencidas;
    private String cpVencendo;
    private String cpTotal;
    private String TotalRestante;
    private Boolean habilitarDesabilitarComCompra;
    private Boolean habilitarDesabilitarSemCompra;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private ContasReceberDao contasReceberDao;

    @PostConstruct
    public void init() {
        getCrTotal();
        calcularTotalContasPagar();
        calcularTotaisContasReceber();
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public String getTotalRestante() {
        return TotalRestante;
    }

    public void setTotalRestante(String totalRestante) {
        TotalRestante = totalRestante;
    }

    public Boolean getHabilitarDesabilitar() {
        return habilitarDesabilitarComCompra;
    }

    public void setHabilitarDesabilitar(Boolean habilitarDesabilitarComCompra) {
        this.habilitarDesabilitarComCompra = habilitarDesabilitarComCompra;
    }

    public String getCrVencer() {
        return crVencer;
    }

    public void setCrVencer(String crVencer) {
        this.crVencer = crVencer;
    }

    public String getCrVencidas() {
        return crVencidas;
    }

    public void setCrVencidas(String crVencidas) {
        this.crVencidas = crVencidas;
    }

    public String getCrVencendo() {
        return crVencendo;
    }

    public void setCrVencendo(String crVencendo) {
        this.crVencendo = crVencendo;
    }

    public String getCpVencer() {
        return cpVencer;
    }

    public void setCpVencer(String cpVencer) {
        this.cpVencer = cpVencer;
    }

    public String getCpVencidas() {
        return cpVencidas;
    }

    public void setCpVencidas(String cpVencidas) {
        this.cpVencidas = cpVencidas;
    }

    public String getCpVencendo() {
        return cpVencendo;
    }

    public void setCpVencendo(String cpVencendo) {
        this.cpVencendo = cpVencendo;
    }

    public String getCrTotal() {
        return crTotal;
    }

    public void setCrTotal(String crTotal) {
        this.crTotal = crTotal;
    }

    public String getCpTotal() {
        return cpTotal;
    }

    public void setCpTotal(String cpTotal) {
        this.cpTotal = cpTotal;
    }

    public Boolean getHabilitarDesabilitarSemCompra() {
        return habilitarDesabilitarSemCompra;
    }

    public void setHabilitarDesabilitarSemCompra(Boolean habilitarDesabilitarSemCompra) {
        this.habilitarDesabilitarSemCompra = habilitarDesabilitarSemCompra;
    }

    public Boolean getHabilitarDesabilitarComCompra() {
        return habilitarDesabilitarComCompra;
    }

    public void setHabilitarDesabilitarComCompra(Boolean habilitarDesabilitarComCompra) {
        this.habilitarDesabilitarComCompra = habilitarDesabilitarComCompra;
    }

    public ContasPagarDao getContasPagarDao() {
        return contasPagarDao;
    }

    public void setContasPagarDao(ContasPagarDao contasPagarDao) {
        this.contasPagarDao = contasPagarDao;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }
    
    

    public void calcularTotalContasPagar() {
        Float vencida = 0.0f;
        Float vencendo = 0.0f;
        Float vencer = 0.0f;
        List<Double> listaTotais = null;
        int idcliente = 0;
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            idcliente = usuarioLogadoMB.getUsuario().getCliente();
        } else {
            idcliente = 8;
        }
        try {
            listaTotais = contasPagarDao.calculaSaldos(Formatacao.ConvercaoDataSql(new Date()), idcliente);
        } catch (SQLException ex) {
            Logger.getLogger(CalculosContasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listaTotais != null) {
            vencida = listaTotais.get(0).floatValue();
            vencendo = listaTotais.get(1).floatValue();
            vencer = listaTotais.get(2).floatValue();
        }
        setCpVencidas(Formatacao.foramtarFloatString(vencida));
        setCpVencendo(Formatacao.foramtarFloatString(vencendo));
        setCpVencer(Formatacao.foramtarFloatString(vencer));
        setCpTotal(Formatacao.foramtarFloatString(vencida + vencer + vencendo));
    }

    public void calcularTotaisContasReceber() {
        Float vencida = 0.0f;
        Float vencendo = 0.0f;
        Float vencer = 0.0f;
        List<Double> listaTotais = null;
        int idcliente = 0;
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            idcliente = usuarioLogadoMB.getUsuario().getCliente();
        } else {
            idcliente = 8;
        }
        try {
            listaTotais = contasReceberDao.calculaSaldos(Formatacao.ConvercaoDataSql(new Date()), idcliente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (listaTotais != null) {
            vencida = listaTotais.get(0).floatValue();
            vencendo = listaTotais.get(1).floatValue();
            vencer = listaTotais.get(2).floatValue();
        }
        setCrVencidas(Formatacao.foramtarFloatString(vencida));
        setCrVencendo(Formatacao.foramtarFloatString(vencendo));
        setCrVencer(Formatacao.foramtarFloatString(vencer));
        setCrTotal(Formatacao.foramtarFloatString(vencida + vencer + vencendo));
        setTotalRestante(Formatacao.foramtarFloatString(vencer - vencendo));
    }

    public Boolean habilitarDesabilitarSemCompraTelaPrincipal(String valor) {
        Float valorVencendo = Formatacao.formatarStringfloat(valor);
        if (valorVencendo > 0f) {
            habilitarDesabilitarComCompra = false;
            return habilitarDesabilitarComCompra;
        } else {
            habilitarDesabilitarComCompra = true;
            return habilitarDesabilitarComCompra;
        }
    }

    public Boolean habilitarDesabilitarComCompraTelaPrincipal(String valor) {
        Float valorVencendo = Formatacao.formatarStringfloat(valor);
        if (valorVencendo > 0f) {
            habilitarDesabilitarSemCompra = true;
            return habilitarDesabilitarSemCompra;
        } else {
            habilitarDesabilitarSemCompra = false;
            return habilitarDesabilitarSemCompra;
        }
    }

}
