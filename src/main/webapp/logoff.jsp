
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="codeu.model.data.Conversation" %>

<!DOCTYPE html>
<html>
<head>
  <title>Logoff</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<nav>
      <a id="navTitle" href="/">Team1 Chat App</a>
      <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
      <% String user = (String) request.getSession().getAttribute("user"); %>
      <a href="/profile/<%= user %>">Hello <%= user %>!</a>
      <% } else { %>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
      <% } %>
      <a href="/about.jsp">About</a>
      <a href="/admin">Administrator</a>
</nav>

 <div id="container">
    <form action="/logoff" method="POST">
      <button type="submit" >Logoff</button>
    </form>
  </div>
    <hr/>
  </body>
  </html>

