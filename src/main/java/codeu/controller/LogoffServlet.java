package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the logoff page. */
public class LogoffServlet extends HttpServlet {
  @Override
  public void init() throws ServletException {
    super.init(); 
  }
  /**
   * This function fires when a user requests the /logoff URL. It simply forwards the request to
   * logoff.jsp.
   */
 	@Override
 	public void doGet(HttpServletRequest request, HttpServletResponse response)
   	  throws IOException, ServletException {
   	  request.getRequestDispatcher("/logoff").forward(request, response);
  }
  /**
   * This function fires when a user submits the logoff form.
   */
   
  @Override 
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      	request.getSession().removeAttribute("user");
      	response.sendRedirect("/");
      }
}
