//Admin servlet test
package codeu.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
  private UserStore mockUserStore;

  @Before
  public void setup()  {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
      .thenReturn(mockRequestDispatcher);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);
 }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<User> fakeUsersList = new ArrayList<>();
    fakeUsersList.add(
        new User(UUID.randomUUID(), "test_username", Instant.now(), "test_password"));
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUsersList);

    adminServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("users", fakeUsersList);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);  }
}