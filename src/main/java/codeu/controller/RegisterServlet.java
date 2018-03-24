package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.time.Instant;
import java.util.UUID;

/**
* Servlet class responsible for user registration.
*/
public class RegisterServlet extends HttpServlet {

  private final UserStore userStore = UserStore.getInstance();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

    User user = new User(UUID.randomUUID(), username, Instant.now(), passwordHash);
    userStore.addUser(user);
    request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
  }
}
