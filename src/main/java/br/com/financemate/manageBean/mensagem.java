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

    public void liberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Liberado com Sucesso", ""));
    }
    
    public void naoLiberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você esta tentando liberar uma conta não autorizada", ""));
    }
    
    public void recebido() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebido com Sucesso", ""));
    }
    
    public void informacaoNaoPreenchida() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Informações obrigatórias para gerar a parcela não informada", ""));
    }
    
    public void recebidoParcial() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebimento Parcial com Sucesso", ""));
    }
    
    public void RecebimentoParcialAcimaValor() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você inseriu um valor parcial maior que o valor da parcela", ""));
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
        context.addMessage(null, new FacesMessage("Histórico Salvo com Sucesso", ""));
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
        context.addMessage(null, new FacesMessage("Alteração Negada", ""));
    }
    
    public void competencia() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Competência, Data Agendamento, Plano de Contas ou Compensão", "Alguns dos itens citados não foram informados"));
    }
    
    public void excluirConfirmacao() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com sucesso", ""));
    }
    
    public void cobrancasSelecionadas() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cobranças selecionadas com sucesso", ""));
    }
    
    public void cobrancasNaoSelecionadas() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cobranças não selecionadas no modulo contas a receber", ""));
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
        context.addMessage(null, new FacesMessage("Você precisa salvar a conta a receber para visualizar as parcelas", "Após salvar, vá em editar para visualizar"));
    }
    
    public void numeroDocumentosIguais() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você inseriu um número de documento que ja existe uma conta a receber", ""));
    }
    
    public void tipoDocumentoNaoFormaPagamento() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você não inseriu o tipo de documento", ""));
    }
    
    public void dataNaoFormaPagamento() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você não inseriu a data", ""));
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
        context.addMessage(null, new FacesMessage("Não contém uma venda", ""));
    }
    
    public void existeLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" Ja existe um cadastro de usuário com este login", ""));
    }
    
    public void notificacao(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(msg, ""));
    }
    
    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Campos em vermelho não preenchido."));
    }
}