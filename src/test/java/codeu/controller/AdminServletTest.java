//Admin servlet test
package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private MessageStore mockMessageStore;
  private UserStore mockUserStore;

  @Before
  public void setup()  {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
      .thenReturn(mockRequestDispatcher);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    adminServlet.setConversationStore(mockConversationStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    adminServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);
 }

  @Test
  public void testDoGet() throws IOException, ServletException {
    int fakeConversationsCount = 10;
    int fakeUsersCount = 4;
    int fakeMessagesCount = 132;

    Mockito.when(mockConversationStore.getConversationsCount()).thenReturn(fakeConversationsCount);
    Mockito.when(mockUserStore.getUsersCount()).thenReturn(fakeUsersCount);
    Mockito.when(mockMessageStore.getMessagesCount()).thenReturn(fakeMessagesCount);

    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversationsCount", fakeConversationsCount);
    Mockito.verify(mockRequest).setAttribute("usersCount", fakeUsersCount);
    Mockito.verify(mockRequest).setAttribute("messagesCount", fakeMessagesCount);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}