//admin servlet

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * Servlet class responsible for administration.
  */
public class AdminServlet extends HttpServlet {

  private UserStore userStore;

  private ConversationStore conversationStore;

  private MessageStore messageStore;

  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    int conversationsCount = conversationStore.getConversationsCount();
    int usersCount = userStore.getUsersCount();
    int messagesCount = messageStore.getMessagesCount();
    String newestUser = userStore.getNewestUser();
    UUID mostRecentAuthor = messageStore.getMostRecentAuthor();
    String mostRecentUser = mostRecentAuthor != null ? userStore.getUser(mostRecentAuthor).getName() : "";
    String mostRecentTime = messageStore.getMostRecentTime();
    request.setAttribute("conversationsCount", conversationsCount);
    request.setAttribute("usersCount", usersCount);
    request.setAttribute("messagesCount", messagesCount);
    request.setAttribute("newestUser", newestUser);
    request.setAttribute("mostRecentUser", mostRecentUser);
    request.setAttribute("mostRecentTime", mostRecentTime);
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }
}
