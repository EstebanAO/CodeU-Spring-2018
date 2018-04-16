//admin servlet

package codeu.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;
import java.time.Instant;
/**
  * Servlet class responsible for administration.
  */
public class AdminServlet extends HttpServlet {
	private UserStore userStore;

	/**
  * Set up state for handling administration-related requests. This method is only called when
  * running in a server, not when running in a test.
  */
	@Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

   /**
  * Sets the UserStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }
/**
   * This function fires when a user requests the /admin URL. It simply forwards the request to
   * admin.jsp.
   */
  /**@Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }*/

 @Override 
 public void doGet(HttpServletRequest request, HttpServletResponse response) 
 	throws IOException, ServletException {

 		List<User> users = userStore.getUsers();
    System.out.println(users);
    request.setAttribute("users", users);
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
 		
 		}
 	}

