package br.com.financemate.manageBean;

import br.com.financemate.dao.BancoDao;
import br.com.financemate.dao.ClienteDao;
import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.CpTransferenciaDao;
import br.com.financemate.dao.FtpDadosDao;
import br.com.financemate.dao.NomeArquivoDao;
import br.com.financemate.dao.OperacaoUsuarioDao;
import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.dao.PlanoContasDao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Ftpdados;
import br.com.financemate.model.Nomearquivo;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Planocontatipo;
import br.com.financemate.util.Ftp;
import javax.ejb.EJB;

@Named
@ViewScoped
public class CadContasPagarMB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Contaspagar contaPagar;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Planocontas> listaPlanoContas;
    private List<Cliente> listaCliente;
    private Planocontas planoContas;
    private Cliente cliente;
    private Banco banco;
    private List<Banco> listaBanco;
    private UploadedFile file;
    private Cptransferencia cptransferencia;
    Boolean selecionada = false;
    private String nomeAnexo = "Anexar";
    private String nomeAquivoFTP;
    private Date dataEnvio;
    private Boolean tipoAcesso;
    private Boolean habilitarUnidade = false;
    private Operacaousuairo operacaousuairo;
    private List<Cptransferencia> listaCptransferencia;
    private Nomearquivo nomearquivo;
    private List<Planocontatipo> listaPlanoContaTipo;
    private Planocontatipo planocontatipo;
    @EJB
    private BancoDao bancoDao;
    @EJB
    private ClienteDao clienteDao;
    @EJB
    private CpTransferenciaDao cpTransferenciaDao;
    @EJB
    private ContasPagarDao contasPagarDao;
    @EJB
    private FtpDadosDao ftpDadosDao;
    @EJB
    private NomeArquivoDao nomeArquivoDao;
    @EJB
    private PlanoContaTipoDao planoContaTipoDao;
    @EJB
    private OperacaoUsuarioDao operacaoUsuarioDao;
    @EJB
    private PlanoContasDao planoContasDao;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        file = (UploadedFile) session.getAttribute("file");
        cliente = (Cliente) session.getAttribute("cliente");
        contaPagar = (Contaspagar) session.getAttribute("contapagar");
        cptransferencia = (Cptransferencia) session.getAttribute("cptransferencia");
        banco = (Banco) session.getAttribute("banco");
        planoContas = (Planocontas) session.getAttribute("planocontas");
        session.removeAttribute("contapagar");
        session.removeAttribute("cliente");
        session.removeAttribute("banco");
        session.removeAttribute("planocontas");
        gerarListaCliente();
        //gerarListaPlanoContas();
        if (contaPagar == null) {
            contaPagar = new Contaspagar();
            if (usuarioLogadoMB.getCliente() != null) {
                cliente = usuarioLogadoMB.getCliente();
            } else if (cliente == null) {
                cliente = new Cliente();
            }
            cptransferencia = new Cptransferencia();
        } else {
            if (cliente == null) {
                cliente = contaPagar.getCliente();
            }
            if (planoContas == null) {
                planoContas = contaPagar.getPlanocontas();
            }
            if (banco == null) {
                banco = contaPagar.getBanco();
            }
            gerarListaPlanoContas();
            gerarListaBanco();
            transferenciaBancaria();
            if (contaPagar.getFormaPagamento().equalsIgnoreCase("transferencia")) {
                listaCptransferencia = cpTransferenciaDao.list("SELECT c FROM Cptransferencia c JOIN Contaspagar co on c.contaspagar.idcontasPagar=co.idcontasPagar WHERE c.contaspagar.idcontasPagar=" + contaPagar.getIdcontasPagar());
                for (int i = 0; i < listaCptransferencia.size(); i++) {
                    cptransferencia = cpTransferenciaDao.find(listaCptransferencia.get(i).getIdcptransferencia());
                }
                if (cptransferencia == null) {
                    cptransferencia = new Cptransferencia();
                }
            }
        }
        desabilitarUnidade();
    }

    public List<Cptransferencia> getListaCptransferencia() {
        return listaCptransferencia;
    }

    public void setListaCptransferencia(List<Cptransferencia> listaCptransferencia) {
        this.listaCptransferencia = listaCptransferencia;
    }

    public Operacaousuairo getOperacaousuairo() {
        return operacaousuairo;
    }

    public void setOperacaousuairo(Operacaousuairo operacaousuairo) {
        this.operacaousuairo = operacaousuairo;
    }

    public Boolean getHabilitarUnidade() {
        return habilitarUnidade;
    }

    public void setHabilitarUnidade(Boolean habilitarUnidade) {
        this.habilitarUnidade = habilitarUnidade;
    }

    public Boolean getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(Boolean tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getNomeAquivoFTP() {
        return nomeAquivoFTP;
    }

    public void setNomeAquivoFTP(String nomeAquivoFTP) {
        this.nomeAquivoFTP = nomeAquivoFTP;
    }

    public Boolean getSelecionada() {
        return selecionada;
    }

    public String getNomeAnexo() {
        return nomeAnexo;
    }

    public void setNomeAnexo(String nomeAnexo) {
        this.nomeAnexo = nomeAnexo;
    }

    public void setSelecionada(Boolean selecionada) {
        this.selecionada = selecionada;
    }

    public Cptransferencia getCptransferencia() {
        return cptransferencia;
    }

    public void setCptransferencia(Cptransferencia cptransferencia) {
        this.cptransferencia = cptransferencia;
    }

    public Contaspagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(Contaspagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public List<Planocontas> getListaPlanoContas() {
        return listaPlanoContas;
    }

    public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
        this.listaPlanoContas = listaPlanoContas;
    }

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Planocontas getPlanoContas() {
        return planoContas;
    }

    public void setPlanoContas(Planocontas planoContas) {
        this.planoContas = planoContas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Nomearquivo getNomearquivo() {
        return nomearquivo;
    }

    public void setNomearquivo(Nomearquivo nomearquivo) {
        this.nomearquivo = nomearquivo;
    }

    public List<Planocontatipo> getListaPlanoContaTipo() {
        return listaPlanoContaTipo;
    }

    public void setListaPlanoContaTipo(List<Planocontatipo> listaPlanoContaTipo) {
        this.listaPlanoContaTipo = listaPlanoContaTipo;
    }

    public Planocontatipo getPlanocontatipo() {
        return planocontatipo;
    }

    public void setPlanocontatipo(Planocontatipo planocontatipo) {
        this.planocontatipo = planocontatipo;
    }
  
    public void cancelar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("file");
        RequestContext.getCurrentInstance().closeDialog(new Contaspagar());
    }

    public void mostrarMensagem(Exception ex, String erro, String titulo) {
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

    public void gerarListaCliente() {
        listaCliente = clienteDao.list("Select c From Cliente c order by c.nomeFantasia");
        if (listaCliente == null) {
            listaCliente = new ArrayList<Cliente>();
        }

    }

    public void gerarListaBanco() {
        if (cliente != null) {
            String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoDao.list(sql);
            if (listaBanco == null) {
                listaBanco = new ArrayList<Banco>();
            }
        } else {
            listaBanco = new ArrayList<Banco>();
        }
    }

    //public void gerarListaPlanoContas() {
    //    PlanoContasFacade planoContasFacade = new PlanoContasFacade();
    //    try {
    //        listaPlanoContas = planoContasFacade.listar();
    //        if (listaPlanoContas == null) {
    //            listaPlanoContas = new ArrayList<Planocontas>();
    //       }
    //    } catch (Exception ex) {
    //       Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
    //       mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
    //   }
    //}
    public void salvar() {
        Operacaousuairo operacaousuairo = new Operacaousuairo();
        contaPagar.setBanco(banco);
        contaPagar.setCliente(cliente);
        contaPagar.setContaPaga("N");
        if (planoContas != null) {
            contaPagar.setPlanocontas(planoContas);
        } else {
            planoContas = planoContasDao.find(1);
            contaPagar.setPlanocontas(planoContas);
        }
        if (contaPagar.getCompetencia() == null) {
            contaPagar.setCompetencia("");
        }
        if (contaPagar.getDataAgendamento() == null) {
            contaPagar.setDataAgendamento(null);
        }
        if (contaPagar.getDataCompensacao() == null) {
            contaPagar.setDataCompensacao(null);
        }
        if (contaPagar.getAutorizarPagamento() == null || contaPagar.getAutorizarPagamento().equalsIgnoreCase("N")) {
            contaPagar.setAutorizarPagamento("N");
        }

        String mensagem = validarDados();
        if (mensagem == "") {
            contaPagar.setStatus("Ativo");
            if (contaPagar.getIdcontasPagar() != null) {
                operacaousuairo.setTipooperacao("Usu�rio Alterou");
            } else {
                operacaousuairo.setTipooperacao("Usu�rio Cadastrou");
            }
            contaPagar = contasPagarDao.update(contaPagar);
            operacaousuairo = salvarOperacaoUsuario(contaPagar, operacaousuairo);
            if (cptransferencia != null) {

                salvarTransferencia();
            }
            if (file != null) {
                nomeArquivo();
                salvarArquivoFTP();
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.removeAttribute("file");
            RequestContext.getCurrentInstance().closeDialog(contaPagar);

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }
    }

    public void salvarRepetir() {
        Operacaousuairo operacaousuairo = new Operacaousuairo();
        contaPagar.setBanco(banco);
        contaPagar.setPlanocontas(planoContas);
        contaPagar.setCliente(cliente);
        contaPagar.setContaPaga("N");
        if (planoContas != null) {
            contaPagar.setPlanocontas(planoContas);
        } else {
            planoContas = planoContasDao.find(1);
            contaPagar.setPlanocontas(planoContas);
        }
        if (contaPagar.getCompetencia() == null) {
            contaPagar.setCompetencia("");
        }
        if (contaPagar.getDataAgendamento() == null) {
            contaPagar.setDataAgendamento(null);
        }
        if (contaPagar.getDataCompensacao() == null) {
            contaPagar.setDataCompensacao(null);
        }

        if (contaPagar.getAutorizarPagamento() == null || contaPagar.getAutorizarPagamento().equalsIgnoreCase("N")) {
            contaPagar.setAutorizarPagamento("N");
        }

        String mensagem = validarDados();
        if (mensagem == "") {
            contaPagar.setStatus("Ativo");
            if (contaPagar.getIdcontasPagar() != null) {
                operacaousuairo.setTipooperacao("Usu�rio Alterou");
            } else {
                operacaousuairo.setTipooperacao("Usu�rio Cadastrou");
            }
            contaPagar = contasPagarDao.update(contaPagar);
            operacaousuairo = salvarOperacaoUsuario(contaPagar, operacaousuairo);
            if (cptransferencia != null) {
                salvarTransferencia();
                Cptransferencia copiaTranferencia = new Cptransferencia();
                copiaTranferencia = cptransferencia;
                cptransferencia = repetirValoresTransferencia(copiaTranferencia);
            }
            if (file != null) {
                salvarArquivoFTP();
            }
            Contaspagar copia = new Contaspagar();
            copia = contaPagar;
            contaPagar = repetirValoresContasPagar(copia);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
        }
    }

    public Contaspagar repetirValoresContasPagar(Contaspagar conta) {
        contaPagar = new Contaspagar();
        contaPagar.setBanco(conta.getBanco());
        contaPagar.setCliente(conta.getCliente());
        contaPagar.setPlanocontas(conta.getPlanocontas());
        contaPagar.setDataEnvio(conta.getDataEnvio());
        contaPagar.setFormaPagamento(conta.getFormaPagamento());
        contaPagar.setFornecedor(conta.getFornecedor());
        contaPagar.setValor(conta.getValor());
        contaPagar.setDescricao(conta.getDescricao());
        contaPagar.setDataAgendamento(conta.getDataAgendamento());
        contaPagar.setDataCompensacao(conta.getDataCompensacao());
        contaPagar.setCompetencia(conta.getCompetencia());
        contaPagar.setNumeroDocumento(conta.getNumeroDocumento());
        contaPagar.setDataVencimento(conta.getDataVencimento());
        return contaPagar;
    }

    public Cptransferencia repetirValoresTransferencia(Cptransferencia transferencia) {
        cptransferencia = new Cptransferencia();
        cptransferencia.setBanco(transferencia.getBanco());
        cptransferencia.setAgencia(transferencia.getAgencia());
        cptransferencia.setConta(transferencia.getConta());
        cptransferencia.setBeneficiario(transferencia.getBeneficiario());
        cptransferencia.setCpfcnpj(transferencia.getCpfcnpj());
        return cptransferencia;
    }

    public void salvarTransferencia() {
        cptransferencia.setContaspagar(contaPagar);
        cptransferencia = cpTransferenciaDao.update(cptransferencia);
    }

    public String validarDados() {
        String mensagem = "";
        if (contaPagar.getFornecedor() == null) {
            mensagem = mensagem + "Fornecedor n�o informado \r\n";
        }
        if (contaPagar.getValor().equals(0f)) {
            mensagem = mensagem + "Valor n�o informado \n";
        }
        if (contaPagar.getDescricao().equalsIgnoreCase("")) {
            mensagem = mensagem + "Descri��o n�o informado \r\n";
        }
        if (contaPagar.getBanco() == null) {
            mensagem = mensagem + "Conta n�o selecionada \r\n";
        }
        if (contaPagar.getDataVencimento() == null) {
            mensagem = mensagem + "Data de Vencimento n�o informada \r\n";
        }
        if (contaPagar.getFormaPagamento() == null) {
            mensagem = mensagem + "Forma de Pagamento n�o selecionada \r\n";
        }
        if (contaPagar.getNumeroDocumento() == "") {
            mensagem = mensagem + "N�mero de documento n�o informado \r\n";
        }

        return mensagem;
    }

    public void upload(FileUploadEvent event) {
        if (file != null) {
            FacesMessage message = new FacesMessage("Anexado com Sucesso", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Boolean transferenciaBancaria() {
        selecionada = false;
        if (contaPagar.getFormaPagamento() != null) {
            if (contaPagar.getFormaPagamento().equalsIgnoreCase("transferencia")) {
                selecionada = true;
                if (cptransferencia == null) {
                    cptransferencia = new Cptransferencia();
                }
            } else {
                cptransferencia = null;
            }
        }
        return selecionada;
    }

    public String nomeAnexo() {
        if (consultarArquivos().equalsIgnoreCase("N�o existe arquivo anexado")) {
            nomeAnexo = "Anexar";
            return nomeAnexo;
        } else {
            nomeAnexo = "Anexado";
            return nomeAnexo;
        }
    }

    public String AdicionarNomeAnexado() {
        if (file != null) {
            return "" + file;
        } else {
            return "Anexar";
        }
    }

    public String corIconeClips() {
        if (file != null) {
            return "../../resources/img/iconeClipVerde.png";
        }
        return "../../resources/img/iconeClipsVermelho.png";
    }

    public boolean salvarArquivoFTP() {
        Ftpdados dadosFTP = null;
        dadosFTP = ftpDadosDao.find(1);
        if (dadosFTP == null) {
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()) {
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
            nomeAquivoFTP = nomeArquivo();
            String msg = ftp.enviarArquivo(file, nomeAquivoFTP);
            if (contaPagar != null && contaPagar.getIdcontasPagar() != null) {
                Nomearquivo nomearquivo = new Nomearquivo();
                nomearquivo.setNomearquivo01(nomeAquivoFTP);
                nomearquivo.setContaspagar(contaPagar);
                nomeArquivoDao.update(nomearquivo);
            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, ""));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        }
        try {
            ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
        }
        return false;
    }

    public boolean salvarArquivoFTP(String nomeArquivoLocal, String nomeArquivoFTP) {
        Ftpdados dadosFTP = null;
        dadosFTP = ftpDadosDao.find("Select f From FtpDados f");
        if (dadosFTP == null) {
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()) {
                mostrarMensagem(null, "Erro conectar FTP", "Erro");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
            ftp.receberArquivo("" + file.getFileName(), nomeAquivoFTP);
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Salvar Arquivo", "Erro");
            return false;
        }
        try {
            ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
            return false;
        }
        return true;

    }

    public void baixarFile() {
        nomeArquivo();
        salvarArquivoFTP("" + file, nomeAquivoFTP);
    }

    public String nomeArquivo() {
        if (contaPagar.getIdcontasPagar() != null) {
            nomeAquivoFTP = "ContasPagar-" + contaPagar.getIdcontasPagar() + "-" + file.getFileName().trim();
        } else {
            nomeAquivoFTP = "ContasPagar-" + file.getFileName().trim();
        }
        return nomeAquivoFTP;
    }

    public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
        salvarArquivoFTP();
    }

    public Date dataEnvio() {
        dataEnvio = new Date();
        contaPagar.setDataEnvio(dataEnvio);
        return dataEnvio;
    }

    public Boolean tipoAcesso() {
        if (usuarioLogadoMB.getCliente() != null) {
            cliente.setNomeFantasia(usuarioLogadoMB.getCliente().getNomeFantasia());
            return tipoAcesso = true;
        }
        return tipoAcesso = false;
    }

    public void desabilitarUnidade() {
        if (usuarioLogadoMB.getCliente() != null) {
            habilitarUnidade = true;
        } else {
            habilitarUnidade = false;
        }

    }

    public String anexarArquivo() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("planocontas", planoContas);
        return "anexarArquivo";
    }

    public String anexarArquivoPrincipal() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("planocontas", planoContas);
        return "anexarArquivoPrincipal";
    }

    public String voltarCadContasPagar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("file", file);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("planocontas", planoContas);
        return "cadContasPagar";
    }

    public String voltarCadContasPagarPrincipal() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("file", file);
        session.setAttribute("cliente", cliente);
        session.setAttribute("banco", banco);
        session.setAttribute("planocontas", planoContas);
        return "cadContasPagarPrincipal";
    }

    public Operacaousuairo salvarOperacaoUsuario(Contaspagar contaspagar, Operacaousuairo operacaousuairo) {
        operacaousuairo.setContaspagar(contaspagar);
        operacaousuairo.setData(new Date());
        operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
        operacaousuairo = operacaoUsuarioDao.update(operacaousuairo);
        return operacaousuairo;
    }

    public String consultarArquivos() {
        String nomeFile = "";
        if (contaPagar.getIdcontasPagar() != null) {
            nomearquivo = nomeArquivoDao.find("SELECT n FROM Nomearquivo n where n.contaspagar.idcontasPagar=" + contaPagar.getIdcontasPagar());
            if (nomearquivo == null) {
                nomearquivo = new Nomearquivo();
                nomearquivo.setNomearquivo01("N�o existe arquivo anexado");

            }
            nomeFile = nomearquivo.getNomearquivo01();
            return nomeFile;
        } else {
            if (file != null) {
                nomeFile = file.getFileName();
            } else {
                nomeFile = "N�o existe arquivo anexado";
            }
            return nomeFile;
        }
    }

    public void gerarListaPlanoContas() {
        try {
            listaPlanoContaTipo = planoContaTipoDao.list("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas=" + cliente.getTipoplanocontas().getIdtipoplanocontas());
            if (listaPlanoContaTipo == null || listaPlanoContaTipo.isEmpty()) {
                listaPlanoContaTipo = new ArrayList<Planocontatipo>();
            }
            listaPlanoContas = new ArrayList<Planocontas>();
            for (int i = 0; i < listaPlanoContaTipo.size(); i++) {
                listaPlanoContas.add(listaPlanoContaTipo.get(i).getPlanocontas());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void gerarListaTotalPlanoConta(){
        listaPlanoContas = planoContasDao.list("Select p From Planoconta p");
    }

}
