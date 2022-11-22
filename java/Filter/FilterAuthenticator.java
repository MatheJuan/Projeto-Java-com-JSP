package Filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connectionDB.SingleConnection;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

 @WebFilter(urlPatterns = {"/principal/*"})
public class FilterAuthenticator implements Filter {
    
	private static Connection connection; 
    
    public FilterAuthenticator() {

    }

	
	public void destroy() {
		 try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			     HttpServletRequest requisicao = (HttpServletRequest) request;
				 HttpSession session = requisicao.getSession();
				 
				 String userLogado = (String) session.getAttribute("usuario");
				 
				 String urlParaAutenticar = requisicao.getServletPath();/*URL esta sendo acessada*/
				 
				 /*Validacao se estiver logado senao será redirecionado */
				 if(userLogado == null && !urlParaAutenticar.equals("principal/ServletLogin")) {
					 
					 RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
					 request.setAttribute("msg", "Por favor, realize o login!");
					 redireciona.forward(requisicao, response);
					 return;//Para a execucao e redirecionar para o  login
		 }else {
			chain.doFilter(request, response);
		 }
			connection.commit();
	}catch(Exception e){
		e.printStackTrace();
		try {
			connection.rollback();
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	  }
	}
	 
	public void init(FilterConfig fConfig) throws ServletException {
		 connection = SingleConnection.getConnection();
		 
	}

}
