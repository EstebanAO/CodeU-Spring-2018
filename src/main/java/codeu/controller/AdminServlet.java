//admin servlet

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.List;
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
  * Sets the UserStore used by this servlet. This function provides a common setup method
  * for use by the test framework or the servlet's init() function.
  */
  public void setUserStore(UserStore userStore) {
	  this.userStore = userStore;
	  private UserStore userStore;
	  private ConversationStore conversationStore;
	  private MessageStore messageStore;
  }

  /**
  * Set up state for handling administration-related requests. This method is only called when
  * running in a server, not when running in a test.
  */
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }
    /**
   * This function fires when a user requests the /admin URL. It simply forwards the request to
   * admin.jsp.
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    int conversationsCount = conversationStore.getConversationsCount();
    int usersCount = userStore.getUsersCount();
    int messagesCount = messageStore.getMessagesCount();
    List<User> users = userStore.getUsers();
    request.setAttribute("conversationsCount", conversationsCount);
    request.setAttribute("usersCount", usersCount);
    request.setAttribute("messagesCount", messagesCount);
    request.setAttribute("users", users);
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }



