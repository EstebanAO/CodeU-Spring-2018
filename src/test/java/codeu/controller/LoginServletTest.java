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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.junit.Ignore;
import org.mindrot.jbcrypt.BCrypt;

public class LoginServletTest {

  private LoginServlet loginServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    loginServlet = new LoginServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    loginServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_BadUsername() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    loginServlet.setUserStore(mockUserStore);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest)
        .setAttribute("error", "Please enter only letters, numbers, and spaces.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Ignore
  @Test
  public void testDoPost_NewUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(false);
    loginServlet.setUserStore(mockUserStore);

    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    loginServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    Mockito.verify(mockUserStore).addUser(userArgumentCaptor.capture());
    Assert.assertEquals(userArgumentCaptor.getValue().getName(), "test username");

    Mockito.verify(mockSession).setAttribute("user", "test username");
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_ExistingUserCorrectPassword() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("test password");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);

    User mockUser = Mockito.mock(User.class);
    Mockito.when(mockUserStore.getUser("test username")).thenReturn(mockUser);
    Mockito.when(mockUser.getPassword()).thenReturn(BCrypt.hashpw("test password", BCrypt.gensalt()));

    loginServlet.setUserStore(mockUserStore);

    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockSession).setAttribute("user", "test username");
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_ExistingUserNotRightPassword() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("incorrect test password");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);

    User mockUser = Mockito.mock(User.class);
    Mockito.when(mockUserStore.getUser("test username")).thenReturn(mockUser);
    Mockito.when(mockUser.getName()).thenReturn("test username");
    Mockito.when(mockUser.getPassword()).thenReturn(BCrypt.hashpw("correct test password", BCrypt.gensalt()));

    loginServlet.setUserStore(mockUserStore);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "Invalid password.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_BlankUsername() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("test password");

    UserStore mockUserStore = Mockito.mock(UserStore.class);

    loginServlet.setUserStore(mockUserStore);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "Please enter a username.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_BlankPassword() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("test username");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("");

    UserStore mockUserStore = Mockito.mock(UserStore.class);

    loginServlet.setUserStore(mockUserStore);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "Please enter a password.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_NotExistingUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("unregistered test username");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("irrelevant test password");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("unregistered test username")).thenReturn(false);

    loginServlet.setUserStore(mockUserStore);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "That username was not found");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
