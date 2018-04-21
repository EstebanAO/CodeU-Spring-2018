
<%@ page import="java.util.List" %>
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
      <a href="/admin">Administrator</a>
  </nav>

  <div id= "container"> 
    <h1>Administrator</h1>

    <hr/>

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
  </div>


</body>
</html>
