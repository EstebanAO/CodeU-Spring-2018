// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

/** Servlet class responsible for the login page. */
public class LoginServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
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
   * This function fires when a user requests the /login URL. It simply forwards the request to
   * login.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the login form. It gets the username and the password
   * from the submitted form data, then it checks that they are valid, either way it adds the username
   * to the session to know that the user is logged in or that an error was thrown to the user.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username == "") {
      request.setAttribute("error", "Please enter a username.");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      return;
    }

    if (password == "") {
      request.setAttribute("error", "Please enter a password.");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      return;
    }

    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      return;
    }

    if (userStore.isUserRegistered(username)) {
      User user = userStore.getUser(username);
      if (BCrypt.checkpw(password, user.getPassword())) {
        //Updates the last connection of the User
        user.setLastConnection(Instant.now());
        userStore.updateUser(user);
        request.getSession().setAttribute("user", username);
        response.sendRedirect("/conversations");
      } else {
        request.setAttribute("error", "Invalid password.");
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
      }
    } else {
      request.setAttribute("error", "That username was not found");
      request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }
  }
}
