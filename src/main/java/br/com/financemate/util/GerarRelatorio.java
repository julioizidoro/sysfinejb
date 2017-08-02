/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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

    @Resource(lookup = "java:/sysfinDS")
    private DataSource dataSource;

    public void gerarRelatorioDSPDF(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        caminhoRelatorio = servletContext.getRealPath(caminhoRelatorio);

        JasperPrint arquivoPrint;
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
        Connection conn = null;
        try {
            conn = getConexao();
        } catch (NamingException ex) {
            Logger.getLogger(GerarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperRunManager.runReportToPdfStream(reportStream,
                servletOutputStream, parameters, conn);

        servletOutputStream.flush();
        servletOutputStream.close();

        facesContext.responseComplete();
    }

    public static Connection getConexao() throws NamingException {
        InitialContext context = null;
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(GerarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataSource ds = (DataSource) context.lookup("java:/sysfinDS");
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GerarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
