<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
    <title>Greeting</title>
  </head>
  <body>
    <h1>Hello, Web</h1>
    <form method="get" action="/hello/get">
        <button type="submit">get</button>
    </form>
    <form method="post" action="/hello/post/">
        <button type="submit">post</button>
    </form>
    <form method="get" action="/hello/cc">
        <button type="submit">Cookie Counter</button>
    </form>
  </body>
</html>
