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
import java.time.Instant;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.Instant;
import org.mockito.ArgumentCaptor;

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
    List<User> fakeUsersList = new ArrayList<>();
    fakeUsersList.add(
        new User(UUID.randomUUID(), "test_username", Instant.now(), "test_password", "test_aboutme"));
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUsersList);

    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("users", fakeUsersList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetWithFakeData() throws IOException, ServletException {
    int fakeConversationsCount = 10;
    int fakeUsersCount = 4;
    int fakeMessagesCount = 132;
    String fakeNewestUser = "test_user1096";
    UUID fakeMostRecentAuthor = UUID.randomUUID();
    User mockUser = Mockito.mock(User.class);
    String fakeMostRecentUser = "test_username";
    String fakeMostRecentTime = "April 15, 10:08 PM";

    Mockito.when(mockConversationStore.getConversationsCount()).thenReturn(fakeConversationsCount);
    Mockito.when(mockUserStore.getUsersCount()).thenReturn(fakeUsersCount);
    Mockito.when(mockMessageStore.getMessagesCount()).thenReturn(fakeMessagesCount);
    Mockito.when(mockUserStore.getNewestUser()).thenReturn(fakeNewestUser);
    Mockito.when(mockMessageStore.getMostRecentAuthor()).thenReturn(fakeMostRecentAuthor);
    Mockito.when(mockUserStore.getUser(fakeMostRecentAuthor)).thenReturn(mockUser);
    Mockito.when(mockUser.getName()).thenReturn(fakeMostRecentUser);
    Mockito.when(mockMessageStore.getMostRecentTime()).thenReturn(fakeMostRecentTime);

    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversationsCount", fakeConversationsCount);
    Mockito.verify(mockRequest).setAttribute("usersCount", fakeUsersCount);
    Mockito.verify(mockRequest).setAttribute("messagesCount", fakeMessagesCount);
    Mockito.verify(mockRequest).setAttribute("newestUser", fakeNewestUser);
    Mockito.verify(mockRequest).setAttribute("mostRecentUser", fakeMostRecentUser);
    Mockito.verify(mockRequest).setAttribute("mostRecentTime", fakeMostRecentTime);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
