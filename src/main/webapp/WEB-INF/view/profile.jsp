<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>

<% User user = (User) request.getAttribute("user");
   List<Message> messages = user.getMessages();
%>


<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<nav>
      <a id="navTitle" href="/">Team1 Chat App</a>
      <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
      <% String userString = (String) request.getSession().getAttribute("user"); %>
      <a href="/profile/<%= userString %>">Hello <%= userString %>!</a>
      <a href="/logoff.jsp">Logoff</a>
      <% } else { %>
        <a href="/login">Login</a>
        <a href="/register">Register</a>
      <% } %>
      <a href="/about.jsp">About</a>
      <a href="/admin">Administrator</a>
</nav>


<div id="container">

    <% if(request.getAttribute("error") != null) { %>
    <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <h1><%= user.getName()%>'s Profile Page</h1>
    <h3>About Me</h3>
    <%= user.getAboutMe() %>
    <br/>
    <% if (request.getSession().getAttribute("user") != null &&
            request.getSession().getAttribute("user").equals(user.getName())) { %>
        <form action="/profile" method="POST">
            <h3>Edit your About Me (Only you can see this) </h3>
          <input type="text" name="aboutMe" id="aboutMe" size="80" value="<%= user.getAboutMe()%>">
         <br/><br/>
         <button type="submit">Submit</button>
     </form>
    <%}%>
    <h4>Sent Messages</h4>
    <br/>
    <% if (request.getSession().getAttribute("user") != null) {
          for (Message message : messages) {%>
            <%=user.getName()%>
            <strong><a href="/chat/<%=user.getConversationTitle(message.getConversationId())%>"><%=user.getConversationTitle(message.getConversationId())%></a></strong>
            <%=message.getContent()%>
            <br/>
          <%}
    }%>

</div>
</body>
</html>
