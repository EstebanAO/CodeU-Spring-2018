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
<!DOCTYPE html>
<html>
<head>
  <title>Team1 Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
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

  <div id="container">
    <div>

      <h1>Welcome!</h1>

      <ul>
        <% if (request.getSession().getAttribute("user") != null) { %>
        <% String user = (String) request.getSession().getAttribute("user"); %>
        <li>View your<a href="/profile/<%= user %>"> profile page</a>.</li>
        <% } else { %>
        <li><a href="/login">Login</a> to get started.</li>
        <% }  %>

        <li>Go to the <a href="/conversations">conversations</a> page to
            create or join a conversation.</li>
        <li>View the <a href="/about.jsp">about</a> page to learn more about the
            project.</li>
      </ul>
    </div>
  </div>
</body>
</html>
