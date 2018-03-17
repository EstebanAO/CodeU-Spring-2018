package codeu.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
  private PrintWriter mockPrintWriter;

  @Mock
  private HttpServletResponse mockResponse;

  @Before
  public void setup() throws IOException {
    registerServlet = new RegisterServlet();
    MockitoAnnotations.initMocks(this);
    Mockito.when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    registerServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockPrintWriter).println("<h1>RegisterServlet GET request.</h1>");
  }
}
