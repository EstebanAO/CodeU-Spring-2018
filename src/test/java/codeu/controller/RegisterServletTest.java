package codeu.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegisterServletTest {

  private RegisterServlet registerServlet;

  @Mock
  private HttpServletRequest mockRequest;

  @Mock
  private HttpServletResponse mockResponse;

  @Mock
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() throws IOException {
    registerServlet = new RegisterServlet();
    MockitoAnnotations.initMocks(this);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp"))
      .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    registerServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
