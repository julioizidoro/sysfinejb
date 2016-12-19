package br.com.financemate.manageBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class mensagem {

    private String messagem="";

    public String getMessagem() {
        return messagem;
    }

    public void setMessagem(String messagem) {
        this.messagem = messagem;
    }

    public void saveMessagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
    }

    public void excluiMessagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
    }

    public void autorizar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Autorizado com Sucesso", ""));
    }
    
    public void desautorizar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Desautorizado com Sucesso", ""));
    }
 

    public void liberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Liberado com Sucesso", ""));
    }
    
    public void naoLiberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� esta tentando liberar uma conta n�o autorizada", ""));
    }
    
    public void recebido() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebido com Sucesso", ""));
    }
    
    public void informacaoNaoPreenchida() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Informa��es obrigat�rias para gerar a parcela n�o informada", ""));
    }
    
    public void recebidoParcial() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebimento Parcial com Sucesso", ""));
    }
    
    public void RecebimentoParcialAcimaValor() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� inseriu um valor parcial maior que o valor da parcela", ""));
    }
    
    public void cancelado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cancelado com Sucesso", ""));
    }
    
    public void editado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Editado com Sucesso", ""));
    }
    
    public void historico() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Hist�rico Salvo com Sucesso", ""));
    }
    
    public void acesso() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Acesso Negado", ""));
    }
    
    public void downloadSucesso() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Arquivo baixado", "Em sua pasta download com sucesso"));
    }
    
    public void alteracao() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Altera��o Negada", ""));
    }
    
    public void competencia() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Compet�ncia, Data Agendamento, Plano de Contas ou Compens�o", "Alguns dos itens citados n�o foram informados"));
    }
    
    public void excluirConfirmacao() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com sucesso", ""));
    }
    
    public void cobrancasSelecionadas() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cobran�as selecionadas com sucesso", ""));
    }
    
    public void cobrancasNaoSelecionadas() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cobran�as n�o selecionadas no modulo contas a receber", ""));
    }
    
    public void valorAcimaPermitidoGerarParcela() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("O valor total maior que o valor total a ser recebido", ""));
    }
    
    public void valorAbaixoPermitidoGerarParcela() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("O valor total menor que o valor total a ser recebido", ""));
    }
    
    public void salvarVisualizarParcela() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� precisa salvar a conta a receber para visualizar as parcelas", "Ap�s salvar, v� em editar para visualizar"));
    }
    
    public void numeroDocumentosIguais() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� inseriu um n�mero de documento que ja existe uma conta a receber", ""));
    }
    
    public void tipoDocumentoNaoFormaPagamento() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� n�o inseriu o tipo de documento", ""));
    }
    
    public void dataNaoFormaPagamento() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Voc� n�o inseriu a data", ""));
    }
    
    public void alteracaoSenha() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Alteração de senha", ""));
    }
    
    public void valorSuperiorAoRestante() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Valor maior que o valor restante", ""));
    }
    
    public void naoContemVenda() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("N�o cont�m uma venda", ""));
    }
    
    public void existeLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" Ja existe um cadastro de usu�rio com este login", ""));
    }
    
    public void notificacao(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg, ""));
    }
    
     public void cobranca(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg, "Consulta de cobrança feita com sucesso"));
    }
    
    public void resetaSenha() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Senha resetada com sucesso!!", ""));
    }
    
    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aten��o!", "Campos em vermelho n�o preenchido."));
    }
}