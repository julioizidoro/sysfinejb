/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.util;

import br.com.financemate.connection.ConectionFactory;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

import org.primefaces.context.RequestContext;



public class GerarRelatorio{
        
        @PersistenceContext
        private EntityManager manager;
    
	public void gerarRelatorioDSPDF(String caminhoRelatorio, Map parameters, JRDataSource jrds, String nomeArquivo ) throws JRException, IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        caminhoRelatorio = servletContext.getRealPath(caminhoRelatorio);  
        
        JasperPrint arquivoPrint=null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=\"" + nomeArquivo + ".pdf\"");
        arquivoPrint = JasperFillManager.fillReport(caminhoRelatorio, parameters, jrds);
        JasperExportManager.exportReportToPdfStream(arquivoPrint, response.getOutputStream());
        facesContext.getApplication().getStateManager().saveView(facesContext);
        facesContext.renderResponse();
        facesContext.responseComplete();
    }

    
    public void gerarRelatorioSqlPDF(String caminhoRelatorio, Map<String, Object> parameters, String nomeArquivo, String subDir ) throws JRException, IOException, SQLException{
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
        InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(caminhoRelatorio);
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Connection conn = ConectionFactory.getConexao();
        

        if (subDir!=null){
            subDir = servletContext.getRealPath(subDir);
            subDir = subDir + File.separator + "a";
            subDir = subDir.substring(0, (subDir.length()-1));
            System.out.println(subDir);
            parameters.put("SUBREPORT_DIR", subDir);
        }
        JasperPrint arquivoPrint=null;
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        ServletOutputStream servletOutputStream = response.getOutputStream();  
        RequestContext.getCurrentInstance().closeDialog(null);
      
        // envia para o navegador o PDF gerado  
        JasperRunManager.runReportToPdfStream(reportStream,  
                servletOutputStream, parameters, conn);  
          
        servletOutputStream.flush();  
        servletOutputStream.close();  
  
        facesContext.responseComplete();  
    }
    
    
}