/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		String prueba;
		try{
			prueba = utilidades.hashPasswordSha256("password");
			prueba = prueba;
		} catch(Exception e){
			
		}
		 */
		request.getSession().invalidate();
		request.logout();
		response.sendRedirect(request.getContextPath() + "/paginas/usuario/index.xhtml");
	}

}
