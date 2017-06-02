/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.util;

import br.com.financemate.dao.ClienteDao;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import java.sql.SQLException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.swing.JComboBox;

/**
 *
 * @author Woverine
 */
public class Formatacao {

    @EJB
    private static ClienteDao clinteDao;

    public static String foramtarDoubleString(Double valor) {
        NumberFormat format = new DecimalFormat("#,##0.00");
        format.setMinimumFractionDigits(2);
        String valorFormatado = format.format(valor);
        return valorFormatado;
    }

    public static Date ConvercaoStringData(String data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dataFormatada = null;
        try {
            dataFormatada = df.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataFormatada;
    }

    public static Date ConvercaoStringDataBrasil(String data) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFormatada = null;
        try {
            dataFormatada = df.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataFormatada;
    }

    public static String foramtarQuantidade(Double valor) {
        NumberFormat format = new DecimalFormat("#,##0.000");
        format.setMinimumFractionDigits(3);
        String valorFormatado = format.format(valor);
        return valorFormatado;
    }

    public static String foramtarFloatString(Float valor) {
        NumberFormat format = new DecimalFormat("#,##0.00");
        format.setMinimumFractionDigits(2);
        String valorFormatado = format.format(valor);
        return valorFormatado;
    }

    public static Float formatarStringfloat(String valor) {
        String novoValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ',') {
                novoValor = novoValor + ".";
            } else if (!(valor.charAt(i) == '.')) {
                novoValor = novoValor + valor.charAt(i);
            }
        }
        return Float.parseFloat(novoValor);
    }

    public static Integer formatarStringInteger(String valor) {
        String novoValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ',') {
                novoValor = novoValor + ".";
            } else if (!(valor.charAt(i) == '.')) {
                novoValor = novoValor + valor.charAt(i);
            }
        }
        return Integer.parseInt(novoValor);
    }

    public static Double formatarStringDouble(String valor) {
        String novoValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ',') {
                novoValor = novoValor + ".";
            } else if (!(valor.charAt(i) == '.')) {
                novoValor = novoValor + valor.charAt(i);
            }
        }
        return Double.parseDouble(novoValor);
    }

    public static JComboBox preencherComobox(List lista, JComboBox combo) {
        for (int i = 0; i < lista.size(); i++) {
            combo.addItem(lista.get(i));
        }
        return combo;
    }

    public static String formatarIntegerString(Integer valor) {
        return String.valueOf(valor);
    }

    public static String formatarLogString(long valor) {
        return String.valueOf(valor);
    }

    public static Date SomarDiasData(Date data, int dias) {
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = new GregorianCalendar();
        c.setTime(data);
        if (dias != 0) {
            c.add(Calendar.DAY_OF_MONTH, dias);
        }
        return (c.getTime());
    }

    public static boolean ValidaCNPJ(String str_cnpj) {
        String ncpf = "";
        for (int i = 0; i < str_cnpj.length(); i++) {// for para retirar tudo
            // que nao for numero
            if ((str_cnpj.charAt(i) != '.') && (str_cnpj.charAt(i) != '-') && (str_cnpj.charAt(i) != '/')) {
                ncpf += str_cnpj.charAt(i);
            }
        }
        str_cnpj = ncpf;

        int soma = 0, aux, dig;
        String cnpj_calc = str_cnpj.substring(0, 12);// Pego CNPJ sem os Dogitos

        if (str_cnpj.length() != 14) {
            return false;
        }

        char[] chr_cnpj = str_cnpj.toCharArray();// Transforma a String para
        // Array de char

        /* Primeira parte */
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);

        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        /* Segunda parte */
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }
        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        return str_cnpj.equals(cnpj_calc);
    }

    public static String SomarDatas(Date data, int dias, String formato) throws Exception {
        SimpleDateFormat sd = new SimpleDateFormat(formato);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_MONTH, dias);
        return sd.format(c.getTime());
    }

    public static String SubtarirDatas(Date data, int dias, String formato) {
        SimpleDateFormat sd = new SimpleDateFormat(formato);
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_MONTH, (dias - (2 * dias)));
        return sd.format(c.getTime());
    }

    public static String ConvercaoDataSql(Date data) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dataFormatada = df.format(data);
        return dataFormatada;
    }

    public static String ConvercaoCalendarSql(Calendar data) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dataFormatada = df.format(data);
        return dataFormatada;
    }

    public static String ConvercaoDataNfe(Date data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = df.format(data);
        return dataFormatada;
    }

    public static String ConvercaoDataPadrao(Date data) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = df.format(data);
        return dataFormatada;
    }

    public static Float ConvercaoMonetariaFloat(String valor) {
        String cValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ',') {
                cValor = cValor + ".";
            } else if (valor.charAt(i) != '.') {
                cValor = cValor + valor.charAt(i);
            }
        }
        float novoValor = Float.parseFloat(cValor);
        return novoValor;
    }

    public static Double ConvercaoMonetariaDouble(String valor) {
        String cValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ',') {
                cValor = cValor + ".";
            } else if (valor.charAt(i) != '.') {
                cValor = cValor + valor.charAt(i);
            }
        }
        double novoValor = Double.parseDouble(cValor);
        return novoValor;
    }

    public static Double calcularValorPercentual(double percentualDesejado, double valorCusto) {
        double percentual = ((100 - percentualDesejado) / 100);
        double novoValorCusto = valorCusto / percentual;
        return novoValorCusto;
    }

    public static Double calcularValorValorVenda(double valorVenda, double valorCusto) {
        valorCusto = valorCusto * 100;
        double percentual = valorCusto / valorVenda;
        percentual = 100 - percentual;
        return percentual;
    }

    // public static Double calcularValorVenda(double percentualDesejado, double
    // valorCusto){
    // double percentual = valorCusto * percentualDesejado;
    // double novoValorVenda = valorCusto / 100;
    // return novoValorVenda;
    // }
    //
    // public static Double calcularPercentualVenda(double valorVenda, double
    // valorCusto){
    // valorVenda = valorVenda * 100;
    // double percentual = valorVenda / valorCusto;
    // percentual = percentual - 100;
    // return percentual;
    // }
    // public static String criptografarSenha(char[] s){
    // Criptografia cripto = new CriptografiaMD5();
    // String senha="";
    // for (int i=0;i<s.length;i++){
    // senha+= s[i];
    // }
    // try {
    // senha = cripto.encrypt(senha);
    // } catch (NoSuchAlgorithmException ex) {
    // Logger.getLogger(Formatacao.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // return senha;
    // }
    public static String getDataDoDia() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String data = df.format(new Date(System.currentTimeMillis()));
        return data;
    }

    public static int gerarDiaSemana(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int dia = cal.get(Calendar.DAY_OF_WEEK);
        return dia;
    }

    public static String decinalValorMonetario(String texto) {
        String novoTexto = "";
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '.') {
                novoTexto = novoTexto + ",";
            } else {
                novoTexto = novoTexto + texto.charAt(i);
            }
        }
        return novoTexto;

    }

    public static boolean validaCPF(String s_aux) {
        String ncpf = "";
        for (int i = 0; i < s_aux.length(); i++) {
            if ((s_aux.charAt(i) != '.') && (s_aux.charAt(i) != '-')) {
                ncpf += s_aux.charAt(i);
            }
        }
        s_aux = ncpf;

        // ------- Rotina para CPF
        if (s_aux.length() == 11) {
            int d1, d2;
            int digito1, digito2, resto;
            int digitoCPF;
            String nDigResult;
            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;
            for (int n_Count = 1; n_Count < s_aux.length() - 1; n_Count++) {
                digitoCPF = Integer.valueOf(s_aux.substring(n_Count - 1, n_Count)).intValue();
                // --------- Multiplique a ultima casa por 2 a seguinte por 3 a
                // seguinte por 4 e assim por diante.
                d1 = d1 + (11 - n_Count) * digitoCPF;
                // --------- Para o segundo digito repita o procedimento
                // incluindo o primeiro digito calculado no passo anterior.
                d2 = d2 + (12 - n_Count) * digitoCPF;
            }

            // --------- Primeiro resto da divisão por 11.
            resto = (d1 % 11);
            // --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário
            // o digito é 11 menos o resultado anterior.
            if (resto < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - resto;
            }
            d2 += 2 * digito1;
            // --------- Segundo resto da divisão por 11.
            resto = (d2 % 11);
            // --------- Se o resultado for 0 ou 1 o digito é 0 caso contrário
            // o digito é 11 menos o resultado anterior.
            if (resto < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - resto;
            }
            // --------- Digito verificador do CPF que está sendo validado.
            String nDigVerific = s_aux.substring(s_aux.length() - 2, s_aux.length());
            // --------- Concatenando o primeiro resto com o segundo.
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
            // --------- Comparar o digito verificador do cpf com o primeiro
            // resto + o segundo resto.
            return nDigVerific.equals(nDigResult);
        } // -------- Rotina para CNPJ
        else if (s_aux.length() == 14) {
            int soma = 0;
            int aux = 0;
            int dig = 0;
            String cnpj_calc = s_aux.substring(0, 12);
            char[] chr_cnpj = s_aux.toCharArray();
            // --------- Primeira parte
            for (int i = 0; i < 4; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i]) - 4 * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                    soma += (chr_cnpj[i + 4]) - 4 * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
            // --------- Segunda parte
            soma = 0;
            for (int i = 0; i < 5; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i]) - 4 * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                    soma += (chr_cnpj[i + 5]) - 4 * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
            return s_aux.equals(cnpj_calc);
        } else {
            return false;
        }
    }

    public static String valorPorExtenso(double vlr) {
        if (vlr == 0) {
            return ("ZERO");
        }
        String ve = String.valueOf(vlr);
        String nve = "";
        for (int i = 0; i < ve.length(); i++) {
            if (ve.charAt(i) != '.') {
                nve = nve + ve.charAt(i);
            } else {
                int numeroI = ve.length() - i;
                if (numeroI > 3) {
                    nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 4);
                } else if (numeroI == 2) {
                    nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 2);
                } else if (numeroI == 3) {
                    nve = nve + ve.charAt(i) + ve.substring(i + 1, i + 3);
                }

                i = 1000;
            }
        }
        vlr = Double.parseDouble(nve);
        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro; // parte fracionária do valor
        String vresto = String.valueOf(resto);
        if (vresto.length() > 5) {
            vresto = vresto.substring(0, 5);
        }
        double valorvResto = Double.parseDouble(vresto);
        if (valorvResto > 0.990) {
            resto = 0.0;
            inteiro = inteiro + 1;
        } else {
            resto = Double.parseDouble(vresto);
        }

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "UM", "DOIS", "TRÊS", "QUATRO", "CINCO", "SEIS", "SETE", "OITO", "NOVE", "DEZ",
            "ONZE", "DOZE", "TREZE", "QUATORZE", "QUINZE", "DEZESSEIS", "DEZESSETE", "DEZOITO", "DEZENOVE"};
        String[] centena = {"", "CENTO", "DUZENTOS", "TREZENTOS", "QUATROCENTOS", "QUINHENTOS", "SEISCENTOS",
            "SETECENTOS", "OITOCENTOS", "NOVECENTOS"};
        String[] dezena = {"", "", "VINTE", "TRINTA", "QUARENTA", "CINQUENTA", "SESSENTA", "SETENTA", "OITENTA",
            "NOVENTA"};
        String[] qualificaS = {"", "MIL", "MILHÃO", "BILHÃO", "TRILHÃO"};
        String[] qualificaP = {"", "MIL", "MILHÕES", "BILHÕES", "TRILHÕES"};

        // definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
            // retira do valor a 1a. parte, 2a. parte, por exemplo, para
            // 123456789:
            // 1a. parte = 789 (centena)
            // 2a. parte = 456 (mil)
            // 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "CEM";
                } else {
                    n = Integer.parseInt(vlrP, 10); // para n = 371, tem-se:
                    cent = n / 100; // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10; // dez = 7 (dezena setenta)
                    unid = (n % 100) % 10; // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " E " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " E " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " E " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão,
            // ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " REAL";
            } else if (tem) {
                s = s + " REAIS";
            } else {
                s = s + " DE REAIS";
            }
        }

        // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
            {
                s = s + " E ";
            }
            if (centavos.equals("1")) {
                s = s + "UM CENTAVO";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else { // para n = 37, tem-se:
                    unid = n % 10; // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta)

                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " E " + unidade[unid];
                    }
                }
                s = s + " CENTAVOS";
            }
        }
        return (s);
    }

    public static String configurarCaminhoArquivos(String localIni) {
        String novo = "";
        for (int i = 0; i < localIni.length(); i++) {
            if (localIni.charAt(i) == '\\') {
                novo = novo + "/";
            } else {
                novo = novo + localIni.charAt(i);
            }
        }
        return novo;
    }

    public static String retornaDataInicia(int mes) {
        if (mes == 1) {
            return "01-01";
        } else if (mes == 2) {
            return "02-01";
        } else if (mes == 3) {
            return "03-01";
        } else if (mes == 4) {
            return "04-01";
        } else if (mes == 5) {
            return "05-01";
        } else if (mes == 6) {
            return "06-01";
        } else if (mes == 7) {
            return "07-01";
        } else if (mes == 8) {
            return "08-01";
        } else if (mes == 9) {
            return "09-01";
        } else if (mes == 10) {
            return "10-01";
        } else if (mes == 11) {
            return "11-01";
        } else if (mes == 12) {
            return "12-01";
        } else {
            return "0";
        }
    }

    public static String retornaDataIniciaBrasil(int mes) {
        if (mes == 1) {
            return "01/01";
        } else if (mes == 2) {
            return "01/02";
        } else if (mes == 3) {
            return "01/03";
        } else if (mes == 4) {
            return "01/04";
        } else if (mes == 5) {
            return "01/05";
        } else if (mes == 6) {
            return "01/06";
        } else if (mes == 7) {
            return "01/07";
        } else if (mes == 8) {
            return "01/08";
        } else if (mes == 9) {
            return "01/09";
        } else if (mes == 10) {
            return "01/10";
        } else if (mes == 11) {
            return "01/11";
        } else if (mes == 12) {
            return "01/12";
        } else {
            return "0";
        }
    }

    public static String retornaDataFinal(int mes) {
        if (mes == 1) {
            return "01-31";
        } else if (mes == 2) {
            return "02-28";
        } else if (mes == 3) {
            return "03-31";
        } else if (mes == 4) {
            return "04-30";
        } else if (mes == 5) {
            return "05-31";
        } else if (mes == 6) {
            return "06-30";
        } else if (mes == 7) {
            return "07-31";
        } else if (mes == 8) {
            return "08-31";
        } else if (mes == 9) {
            return "09-30";
        } else if (mes == 10) {
            return "10-31";
        } else if (mes == 11) {
            return "11-30";
        } else if (mes == 12) {
            return "12-31";
        } else {
            return "0";
        }
    }

    public static String retornaDataFinalBrasil(int mes) {
        if (mes == 1) {
            return "31/01";
        } else if (mes == 2) {
            return "28/02";
        } else if (mes == 3) {
            return "31/03";
        } else if (mes == 4) {
            return "30/04";
        } else if (mes == 5) {
            return "31/05";
        } else if (mes == 6) {
            return "30/06";
        } else if (mes == 7) {
            return "31/07";
        } else if (mes == 8) {
            return "31/08";
        } else if (mes == 9) {
            return "30/09";
        } else if (mes == 10) {
            return "31/10";
        } else if (mes == 11) {
            return "30/11";
        } else if (mes == 12) {
            return "31/12";
        } else {
            return "0";
        }
    }

    public static String retornaDataFinalBarras(int mes) {
        if (mes == 1) {
            return "01/31";
        } else if (mes == 2) {
            return "02/28";
        } else if (mes == 3) {
            return "02/31";
        } else if (mes == 4) {
            return "04/30";
        } else if (mes == 5) {
            return "05/31";
        } else if (mes == 6) {
            return "06/30";
        } else if (mes == 7) {
            return "07/31";
        } else if (mes == 8) {
            return "08/31";
        } else if (mes == 9) {
            return "09/30";
        } else if (mes == 10) {
            return "10/31";
        } else if (mes == 11) {
            return "11/30";
        } else if (mes == 12) {
            return "12/31";
        } else {
            return "0";
        }
    }

    public static String nomeMes(int mes) {
        if (mes == 1) {
            return "Janeiro";
        } else if (mes == 2) {
            return "Fevereiro";
        } else if (mes == 3) {
            return "Março";
        } else if (mes == 4) {
            return "Abril";
        } else if (mes == 5) {
            return "Maio";
        } else if (mes == 6) {
            return "Junho";
        } else if (mes == 7) {
            return "Julho";
        } else if (mes == 8) {
            return "Agosto";
        } else if (mes == 9) {
            return "Setembro";
        } else if (mes == 10) {
            return "Outubro";
        } else if (mes == 11) {
            return "Novembro";
        } else if (mes == 12) {
            return "Dezembro";
        } else {
            return "nao encontrado";
        }
    }

    public static int subtrairDatas(Date dataInicial, Date dataFinal) {
        int resultado = 0;
        resultado = (int) ((dataInicial.getTime() - dataFinal.getTime()) / 86400000L);
        return resultado;
    }

    public static String foramtarHoraString() {
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = formato.format(new Date());
        return formattedDate;
    }

    public static Cliente consultarCliente(UsuarioLogadoMB usuarioLogadoMB) {
        Cliente cliente = null;
        if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
            cliente = clinteDao.find(usuarioLogadoMB.getUsuario().getCliente());
            if (cliente != null) {
                return cliente;
            }

        }
        return null;
    }

    public static String retirarNegativo(String valor) {
        String novoValor = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.substring(i) != "-") {
                novoValor = novoValor + valor.substring(i + 1);
                i = 1000;
            }
        }
        return novoValor;
    }

    public static String removeAcentos(final String str) {
        String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
        strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
        return strSemAcentos;
    }

    public static String gerarCopetencia(Date data) {
        String copetencia = "";
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int mes = calendar.get(GregorianCalendar.MONTH);
        int ano = calendar.get(GregorianCalendar.YEAR);
        String smes = "";
        if (mes > 9) {
            smes = String.valueOf(mes);
        } else {
            smes = "0" + String.valueOf(mes);
        }
        copetencia = smes + "/" + String.valueOf(ano);
        return copetencia;

    }

    public static String formatarFloatString(Float valor) {
        NumberFormat format = new DecimalFormat("#,##0.00");
        format.setMinimumFractionDigits(2);
        String valorFormatado = format.format(valor);
        return valorFormatado;
    }

    public static int diaSemana(Date data) {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(data);
        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        return diaSemana;
    }

    public static String formatarHoraString() {
        DateFormat formato = new SimpleDateFormat("HH:mm");
        String formattedDate = formato.format(new Date());
        return formattedDate;
    }

    public static int getAnoData(Date data) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int ano = calendar.get(GregorianCalendar.YEAR);
        return ano;
    }

    public static String diaSemanaEscrito(Date data) {
        Calendar cal = Calendar.getInstance();    // hoje  
        cal.setTime(data);    // uma Date  
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        if (diaSemana == 1) {
            return "Domingo";
        } else if (diaSemana == 2) {
            return "Segunda";
        } else if (diaSemana == 3) {
            return "Terça";
        } else if (diaSemana == 4) {
            return "Quarta";
        } else if (diaSemana == 5) {
            return "Quinta";
        } else if (diaSemana == 6) {
            return "Sexta";
        } else if (diaSemana == 7) {
            return "Sabado";
        }
        return "Erro";
    }

    public static int getMesData(Date data) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int mes = calendar.get(GregorianCalendar.MONTH);
        return mes;
    }

    public static int getDiaData(Date data) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        return dia;
    }
}
