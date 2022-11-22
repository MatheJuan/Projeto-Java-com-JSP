package Servlets;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
	
//@WebServlet(urlPatterns = {"/principal/ServletLogin","/ServletLogin"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public ServletLogin() {
      }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String login =request.getParameter("login");
		 String senha=request.getParameter("senha");
		 String url = request.getParameter("url");
		 
		 if(login!= null && !login.isEmpty() && senha!=null && !senha.isEmpty()) {
			 
			 ModelLogin modeloLogin = new ModelLogin();
			 modeloLogin.setLogin(login);
			 modeloLogin.setSenha(senha);
			 
			 if(modeloLogin.getLogin().equalsIgnoreCase("admin") 
			    && modeloLogin.getSenha().equalsIgnoreCase("admin")) {
				 
				 	request.getSession().setAttribute("usuario", modeloLogin.getLogin());
				 	
				 	if(url == null || url.equals("null")) {
				 		url = "principal/principal.jsp";
				 	}
				 	RequestDispatcher redirecionar = request.getRequestDispatcher(url);
				 	redirecionar.forward(request, response);
				 	
			 }else {
				 RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp");
				 request.setAttribute("msg", "Informe o login e senha corretamente.");
				 redireciona.forward(request, response);
			 }
		}else {
			RequestDispatcher redireciona = request.getRequestDispatcher("principal/principal.jsp");
			request.setAttribute("msg", "Informe o login e senha corretamente.");
			redireciona.forward(request, response);
		}
	}

}
