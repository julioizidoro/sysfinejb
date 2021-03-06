package br.com.financemate.manageBean.outrosLancamentos;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Movimentobanco;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import javax.ejb.EJB;

@Named
@ViewScoped
public class cadOutrosLancamentosMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Movimentobanco outrosLancamentos;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Banco banco;
    private List<Banco> listaBanco;
    private Boolean habilitarUnidade = false;
    private Planocontas planoContas;
    private List<Planocontas> listaPlanoContas;
    private String tipoDocumento;
    private Planocontatipo planocontatipo;
    private List<Planocontatipo> listaPlanoContaTipo;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private OutrosLancamentosDao outrosLancamentosDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    @EJB
    private PlanoContasDao planoContasDao;
    private Date dataInicial;
    private Date dataFinal;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        outrosLancamentos = (Movimentobanco) session.getAttribute("outroslancamentos");
        cliente = (Cliente) session.getAttribute("cliente");
        banco = (Banco) session.getAttribute("banco");
        dataInicial = (Date) session.getAttribute("dataInicial");
        dataFinal = (Date) session.getAttribute("dataFinal");
        session.removeAttribute("outroslancamentos");
        session.removeAttribute("cliente");
        session.removeAttribute("banco");
        session.removeAttribute("dataInicial");
        session.removeAttribute("dataFinal");
        if (outrosLancamentos == null) {
            outrosLancamentos = new Movimentobanco();
        } else {
            cliente = outrosLancamentos.getCliente();
            planoContas = outrosLancamentos.getPlanocontas();
            banco = outrosLancamentos.getBanco();
            tipoDocumento = outrosLancamentos.getTipoDocumento();
            gerarListaBanco();
            gerarListaPlanoContas();
        }
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            cliente = clienteDao.find(usuarioLogadoMB.getUsuario().getCliente());
            gerarListaBanco();
        }
        gerarListaCliente();
        gerarListaPlanoContas();
        desabilitarUnidade();
        outrosLancamentos.setDataRegistro(new Date());
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public List<Planocontas> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public Planocontas getPlanoContas() {
        return planoContas;
    }

    public void setPlanoContas(Planocontas planoContas) {
        this.planoContas = planoContas;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Movimentobanco getOutrosLancamentos() {
        return outrosLancamentos;
    }

    public void setOutrosLancamentos(Movimentobanco outrosLancamentos) {
        this.outrosLancamentos = outrosLancamentos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Planocontatipo getPlanocontatipo() {
        return planocontatipo;
    }

    public void setPlanocontatipo(Planocontatipo planocontatipo) {
        this.planocontatipo = planocontatipo;
    }

    public List<Planocontatipo> getListaPlanoContaTipo() {
        return listaPlanoContaTipo;
    }

    public void setListaPlanoContaTipo(List<Planocontatipo> listaPlanoContaTipo) {
        this.listaPlanoContaTipo = listaPlanoContaTipo;
    }

    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoDao.list(sql);
            if (listaBanco == null) {
                listaBanco = new ArrayList<>();
            }
        } else {
            listaBanco = new ArrayList<>();
        }
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void salvar() {
        outrosLancamentos = setaValoresOutrosLancamentos(outrosLancamentos);
        String mensagem = validarDados();
        if (mensagem.length() < 1) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("cliente", cliente);
            session.setAttribute("banco", banco);
            session.setAttribute("dataInicial", dataInicial);
            session.setAttribute("dataFinal", dataFinal);
            outrosLancamentos.setConciliacao("não");
            outrosLancamentos = outrosLancamentosDao.update(outrosLancamentos);
            mensagem msg = new mensagem();
            msg.saveMessagem();
            RequestContext.getCurrentInstance().closeDialog(outrosLancamentos);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }

    }

    public void salvarRepetir() {
        outrosLancamentos = setaValoresOutrosLancamentos(outrosLancamentos);
        String mensagem = validarDados();
        if (mensagem.length() < 1) {
            outrosLancamentos = outrosLancamentosDao.update(outrosLancamentos);
            Movimentobanco copia = outrosLancamentos;
            outrosLancamentos.setConciliacao("não");
            outrosLancamentos = repetirValoresOutrosLancamentos(copia);
            mensagem msg = new mensagem();
            msg.saveMessagem();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }

    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String validarDados() {
        String mensagem = "";
        if (outrosLancamentos.getCliente() == null) {
            mensagem = mensagem + "Unidade não informada \r\n";
        }
        if (outrosLancamentos.getDataRegistro() == null) {
            mensagem = mensagem + " Data de Registro não informada \n";
        }
        if (outrosLancamentos.getTipoDocumento().equalsIgnoreCase(null)) {
            mensagem = mensagem + "Tipo de documento não informado \r\n";
        }
        if (banco == null || banco.getIdbanco() == null) {
            mensagem = mensagem + "Conta não selecionada \r\n";
        }
        if (outrosLancamentos.getDataVencimento() == null) {
            mensagem = mensagem + "Data de Vencimento não informada \r\n";
        }
        if (outrosLancamentos.getValorEntrada() == null) {
            outrosLancamentos.setValorEntrada(0.0f);
        }
        if (outrosLancamentos.getValorSaida() == null) {
            outrosLancamentos.setValorSaida(0.0f);
        }
        if (outrosLancamentos.getDescricao().equalsIgnoreCase(null)) {
            mensagem = mensagem + "Descrição não informada \r\n";
        }
        if (planoContas == null) {
            mensagem = mensagem + "Plano de contas não informada \r\n";
        }
        if (outrosLancamentos.getDataCompensacao() == null) {
            mensagem = mensagem + "Data de compensação não informada \r\n";
        }

        return mensagem;
    }

    public void cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("dataInicial", dataInicial);
        session.setAttribute("dataFinal", dataFinal);
        RequestContext.getCurrentInstance().closeDialog(new Movimentobanco());
    }

    public Movimentobanco setaValoresOutrosLancamentos(Movimentobanco outros) {
        outros.setBanco(banco);
        outros.setCliente(cliente);
        outros.setPlanocontas(planoContas);
        outros.setTipoDocumento(tipoDocumento);
        outros.setUsuario(usuarioLogadoMB.getUsuario());
        return outros;
    }

    public Movimentobanco repetirValoresOutrosLancamentos(Movimentobanco outros) {
        outrosLancamentos = new Movimentobanco();
        outrosLancamentos.setBanco(outros.getBanco());
        outrosLancamentos.setCliente(outros.getCliente());
        outrosLancamentos.setCompentencia(outros.getCompentencia());
        outrosLancamentos.setDataCompensacao(outros.getDataCompensacao());
        outrosLancamentos.setDataRegistro(outros.getDataCompensacao());
        outrosLancamentos.setDataVencimento(outros.getDataVencimento());
        outrosLancamentos.setDescricao(outros.getDescricao());
        outrosLancamentos.setPlanocontas(outros.getPlanocontas());
        outrosLancamentos.setSaldo(outros.getSaldo());
        outrosLancamentos.setTipoDocumento(outros.getTipoDocumento());
        outrosLancamentos.setUsuario(outros.getUsuario());
        outrosLancamentos.setValorEntrada(outros.getValorEntrada());
        outrosLancamentos.setValorSaida(outros.getValorSaida());
        return outrosLancamentos;
    }

    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where tipoplanocontas.idtipoplanocontas="
                    + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<>();
            }
            listaPlanoContas = new ArrayList<>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoContas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            mensagem m = new mensagem();
            m.faltaInformacao("" + e);
        }
    }

}
