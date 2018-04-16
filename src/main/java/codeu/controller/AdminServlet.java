//admin servlet

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.store.basic.ConversationStore;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * Servlet class responsible for administration.
  */
public class AdminServlet extends HttpServlet {

  private ConversationStore conversationStore;

  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
  }

  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Conversation> conversations = conversationStore.getAllConversations();
    int conversationsCount = conversations != null ? conversations.size() : 0;
    request.setAttribute("conversationsCount", conversationsCount);
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }
}
