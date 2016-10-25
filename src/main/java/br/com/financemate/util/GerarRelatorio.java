/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Wolverine
 */
public class GerarRelatorio {

    public void gerarRelatorioDSPDF(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        caminhoRelatorio = servletContext.getRealPath(caminhoRelatorio);

        JasperPrint arquivoPrint = null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=\"" + nomeArquivo + ".pdf\"");
        facesContext.responseComplete();
        arquivoPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, jrds);
        JasperExportManager.exportReportToPdfStream(arquivoPrint, response.getOutputStream());
        facesContext.getApplication().getStateManager().saveView(facesContext);
        facesContext.renderResponse();
        facesContext.responseComplete();
    }

    public void gerarRelatorioSqlPDF(String caminhoRelatorio, Map<String, Object> parameters, String nomeArquivo) throws JRException, IOException, SQLException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(caminhoRelatorio);

        JasperPrint arquivoPrint = null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        facesContext.responseComplete();   
        ServletOutputStream servletOutputStream = response.getOutputStream();
        RequestContext.getCurrentInstance().closeDialog(null);
        Connection conn = getConexao();
        JasperRunManager.runReportToPdfStream(reportStream,
                servletOutputStream, parameters, conn);

        servletOutputStream.flush();
        servletOutputStream.close();

        facesContext.responseComplete();
    }
    
    
    public static Connection getConexao(){
    	Connection conexao = null;
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://191.191.20.138:8082/sysfin", "root", "jfhmaster123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return conexao;
    }

}
