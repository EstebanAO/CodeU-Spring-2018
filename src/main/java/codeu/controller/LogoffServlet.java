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
    /** Store class that gives access to Users. */
    private UserStore userStore;

  @Override
  public void init() throws ServletException {
      super.init();
      setUserStore(UserStore.getInstance());
  }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
   * This function fires when a user requests the /logoff URL. It simply forwards the request to
   * logoff.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.getRequestDispatcher("/logoff").forward(request, response);

    //Updates lastConnection of the user.
    String username = (String) request.getSession().getAttribute("user");
    if (username != null)
    {
      User userLastConnection = userStore.getUser(username);
      userLastConnection.setLastConnection(Instant.now());
      userStore.updateUser(userLastConnection);
    }
}
  /**
   * This function fires when a user submits the logoff form.
   */
   
  @Override 
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      //Updates lastConnection of the user.
      String username = (String) request.getSession().getAttribute("user");
      if (username != null)
      {
        User userLastConnection = userStore.getUser(username);
        userLastConnection.setLastConnection(Instant.now());
        userStore.updateUser(userLastConnection);
      }

    request.getSession().removeAttribute("user");
    response.sendRedirect("/");
  }
}
