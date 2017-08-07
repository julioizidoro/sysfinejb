package br.com.financemate.manageBean.contasReceber;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasReceberDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.model.Vendas;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadContasReceberMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private List<Planocontas> listaPlanoContas;
    private Planocontas planoContas;
    private List<Banco> listaBanco;
    private Banco banco;
    private Contasreceber contasReceber;
    private Boolean repetir = false;
    private Boolean disabilitar = true;
    private String vezes;
    private String frequencia;
    private Cobranca cobranca;
    private Vendas vendas;
    private Boolean habilitarUnidade = false;
    private List<Contasreceber> listarParcelas;
    private List<Contasreceber> contasNumeroDocumentosIguais;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private ContasReceberDao contasReceberDao;
    private Planocontatipo planocontatipo;
    private List<Planocontatipo> listaPlanoContaTipo;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contareceber");
        frequencia = (String) session.getAttribute("frequencia");
        vezes = (String) session.getAttribute("vezes");
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("frequencia");
        session.removeAttribute("vezes");
        session.removeAttribute("contareceber");
        session.removeAttribute("cliente");
        gerarListaCliente();
        if (contasReceber == null) {
            contasReceber = new Contasreceber();
            cobranca = new Cobranca();
            vendas = new Vendas();
            if (usuarioLogadoMB.getCliente() != null) {
                cliente = usuarioLogadoMB.getCliente();
                gerarListaBanco();
                gerarListaPlanoContas();
            } else {
                if (cliente != null) {
                    gerarListaBanco();
                    gerarListaPlanoContas();
                }
            }
        } else {
            cliente = contasReceber.getCliente();
            gerarListaPlanoContas();
            gerarListaBanco();
            planoContas = contasReceber.getPlanocontas();
            banco = contasReceber.getBanco();
        }
        desabilitarUnidade();
    }

    public List<Contasreceber> getContasNumeroDocumentosIguais() {
        return contasNumeroDocumentosIguais;
    }

    public void setContasNumeroDocumentosIguais(List<Contasreceber> contasNumeroDocumentosIguais) {
        this.contasNumeroDocumentosIguais = contasNumeroDocumentosIguais;
    }

    public List<Contasreceber> getListarParcelas() {
        return listarParcelas;
    }

    public void setListarParcelas(List<Contasreceber> listarParcelas) {
        this.listarParcelas = listarParcelas;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public String getVezes() {
        return vezes;
    }

    public void setVezes(String vezes) {
        this.vezes = vezes;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public Boolean getDisabilitar() {
        return disabilitar;
    }

    public void setDisabilitar(Boolean disabilitar) {
        this.disabilitar = disabilitar;
    }

    public Boolean getRepetir() {
        return repetir;
    }

    public void setRepetir(Boolean repetir) {
        this.repetir = repetir;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Contasreceber getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(Contasreceber contasReceber) {
        this.contasReceber = contasReceber;
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
    
    

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("select c from Cliente c");
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }

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

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public Boolean habilitarCampoRepetir() {
        if (repetir) {
            disabilitar = false;
            return disabilitar;
        } else {
            disabilitar = true;
            return disabilitar;
        }

    }

    public String salvar() {
        if (vezes != null) {

            int numeroVezes = Integer.parseInt(vezes);
            for (int i = 1; i <= numeroVezes; i++) {
                contasReceber.setBanco(banco);
                contasReceber.setPlanocontas(planoContas);
                contasReceber.setCliente(cliente);
                contasReceber.setValorPago(0.0f);
                contasReceber.setDesagio(0.0f);
                contasReceber.setJuros(0.0f);
                contasReceber.setNumeroParcela(i + "/" + vezes);
                contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
                contasReceber.setStatus("Ativo");
                String mensagem = validarDados();
                if (mensagem.length() < 1) {
                    Contasreceber copia = new Contasreceber();
                    copia = contasReceber;
                    contasReceber = contasReceberDao.update(contasReceber);
                    if (frequencia != null) {
                        if (frequencia.equalsIgnoreCase("mensal")) {
                            Calendar c = new GregorianCalendar();
                            c.setTime(copia.getDataVencimento());
                            c.add(Calendar.MONTH, 1);
                            Date data = c.getTime();
                            copia.setDataVencimento(data);
                        } else if (frequencia.equalsIgnoreCase("Diaria")) {
                            Calendar c = new GregorianCalendar();
                            c.setTime(copia.getDataVencimento());
                            c.add(Calendar.DAY_OF_MONTH, 1);
                            Date data = c.getTime();
                            copia.setDataVencimento(data);
                        } else if (frequencia.equalsIgnoreCase("anual")) {
                            Calendar c = new GregorianCalendar();
                            c.setTime(copia.getDataVencimento());
                            c.add(Calendar.YEAR, 1);
                            Date data = c.getTime();
                            copia.setDataVencimento(data);
                        } else if (frequencia.equalsIgnoreCase("Semanal")) {
                            Calendar c = new GregorianCalendar();
                            c.setTime(copia.getDataVencimento());
                            c.add(Calendar.DAY_OF_MONTH, 7);
                            Date data = c.getTime();
                            copia.setDataVencimento(data);
                        }
                    }
                    if (i < numeroVezes) {
                        contasReceber = new Contasreceber();
                        contasReceber = copia;
                    }
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(mensagem, ""));
                }
            }
            RequestContext.getCurrentInstance().closeDialog(contasReceber);

        } else {

            contasReceber.setBanco(banco);
            contasReceber.setPlanocontas(planoContas);
            contasReceber.setCliente(cliente);
            contasReceber.setValorPago(0.0f);
            contasReceber.setDesagio(0.0f);
            contasReceber.setJuros(0.0f);
            contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
            contasReceber.setNumeroParcela("1/1");
            contasReceber.setStatus("Ativo");
            String mensagem = validarDados();
            if (mensagem.length() < 1) {
                contasReceber = contasReceberDao.update(contasReceber);
                RequestContext.getCurrentInstance().closeDialog(contasReceber);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(mensagem, ""));
            }
        }
        return "";
    }

    public String cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("contareceber");
        RequestContext.getCurrentInstance().closeDialog(new Contasreceber());
        return "";
    }

    public String validarDados() {
        String mensagem = "";
        if (cliente == null || cliente.getIdcliente() == null) {
            mensagem = mensagem + "Unidade não selecionada \r\n";
        }
        if (contasReceber.getNomeCliente().equals("")) {
            mensagem = mensagem + "Nome do Cliente não informado \r\n";
        }
        if (contasReceber.getDataVencimento() == null) {
            mensagem = mensagem + "Data de vencimento não informado \r\n";
        }
        if (contasReceber.getValorParcela() == null) {
            mensagem = mensagem + "Valor não informado \r\n";
        }
        if (banco == null || banco.getIdbanco() == null) {
            mensagem = mensagem + "Banco não selecionado";
        }
        if (contasReceber.getNumeroDocumento().equalsIgnoreCase("")) {
            mensagem = mensagem + "Número de documento não informado \r\n";
        }
        if (planoContas == null || planoContas.getIdplanoContas() == null) {
            mensagem = mensagem + "Selecione o plano de conta \r\n";
        }
        return mensagem;
    }

    public String abrirParcelamento() {
        if (contasReceber.getIdcontasReceber() != null) {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 500);
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("listarParcelas", listarParcelas);
            session.setAttribute("contasReceber", contasReceber);
            session.setAttribute("frequencia", frequencia);
            session.setAttribute("vezes", vezes);
            return "parcelas";

        } else {
            mensagem mensagem = new mensagem();
            mensagem.salvarVisualizarParcela();
            return "";
        }
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }
    
    
    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<>();
            }
            listaPlanoContas = new ArrayList<>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoContas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            mensagem mensagem = new mensagem();
            mensagem.faltaInformacao("" + e);
        }
    }

}
