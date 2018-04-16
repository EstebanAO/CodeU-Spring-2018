
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Conversation" %>

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
      <a id="navTitle" href="/">CodeU Chat App</a>
      <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
        <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <% } else { %>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
      <% } %>
      <a href="/about.jsp">About</a>
  </nav>

  <div id= "container"> 
    <h1>Administrator</h1>

    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <%--<% if (request.getSession().getAttribute("users") !=null { %>
        <h1>New Use})
        --%>
   <hr/>
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
        <li><a href="/admin/<%= user.getName() %>">
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
