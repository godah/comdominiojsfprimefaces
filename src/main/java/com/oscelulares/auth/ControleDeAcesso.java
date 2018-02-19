/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oscelulares.auth;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author luciano
 */
@WebFilter(servletNames = { "Faces Servlet" })
public class ControleDeAcesso implements Filter{
     private String login = null;
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
                     
                HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
                

		if ((session.getAttribute("USUARIOLogado") != null)
				|| (req.getRequestURI().endsWith("login.xhtml"))
				|| (req.getRequestURI().contains("javax.faces.resource/"))) {
		
			chain.doFilter(request, response);
		}

		else {
                        System.out.println("Vai para login.xhtml");
                        //pagina inicial
                        
                        if(login == null){
                            login = "faces/login.xhtml";
                            
                            redireciona(login, response);
                        }else{
                            login = "../../../../oscelulares/faces/login.xhtml";
                            redireciona(login, response);
                        }
                        
                        
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	private void redireciona(String url, ServletResponse response)
			throws IOException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(url);
	}
}
