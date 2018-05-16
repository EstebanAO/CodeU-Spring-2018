

<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<% 
UserStore userStore = UserStore.getInstance();
%>

<!DOCTYPE html>
<html>
<head>
  <title>Administrator</title>
  <link rel="stylesheet" href="/css/main.css">
  <style>
    Label {
      display: inline-block;
      width: 100px;
      </style>
</head>
<body>

    <nav>
      <a id="navTitle" href="/">Team1 Chat App</a>
      <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
      <% String user = (String) request.getSession().getAttribute("user"); %>
      <a href="/profile/<%= user %>">Hello <%= user %>!</a>
      <a href="/logoff.jsp">Logoff</a>
      <% } else { %>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
      <% } %>
      <a href="/about.jsp">About</a>
      <a href="/admin">Administrator</a>
</nav>

  <div id= "container"> 
    <h1>Administrator</h1>
    
    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h2>Site Statistics</h2>
    <p>Here are some site stats:</p>
    <ul>
      <li><strong>Users:</strong>
        <%= request.getAttribute("usersCount") %>
      </li>
      <li><strong>Conversations:</strong>
        <%= request.getAttribute("conversationsCount") %>
      </li>
      <li><strong>Messages:</strong>
        <%= request.getAttribute("messagesCount") %>
      </li>
      <li><strong>Newest user:</strong>
        <%= request.getAttribute("newestUser") %>
      </li>
      <li><strong>Most recent message:</strong>
        <%= request.getAttribute("mostRecentUser") %>, 
        <%= request.getAttribute("mostRecentTime") %>
      </li>
    </ul>

    
    <h2>All Users</h2>
    <% 
    List<User> users = (List<User>) request.getAttribute("users");
    if(users == null){
    %>
      <p>No Users</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(User user : users){
      %>
        <li><a href="/profile/<%= user.getName() %>">
          <%= user.getName() %></a></li>
      <%
      }
      %>
        </ul>
      <%
      }
      %>
      <hr/>

  </div>


</body>
</html>
