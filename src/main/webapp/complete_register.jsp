
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  fit.hcmuaf.edu.vn.foodmart.model.GoogleAccount googleAcc = (fit.hcmuaf.edu.vn.foodmart.model.GoogleAccount) session.getAttribute("google_acc");
  if (googleAcc == null) {
    response.sendRedirect("login.jsp"); // Không có dữ liệu -> quay lại login
  }
%>
<html>
<head>
    <title>Hoàn tất đăng ký</title>
</head>
<body>
<h2>Hoàn tất đăng ký</h2>
<form action="completeRegister" method="post">
  <p>Email: <%= googleAcc.getEmail() %> (đã lấy từ Google)</p>
  <input type="hidden" name="email" value="<%= googleAcc.getEmail() %>">
  <input type="hidden" name="fullName" value="<%= googleAcc.getName() %>">

  <label>Username:</label><br>
  <input type="text" name="username" required><br><br>

  <label>Password:</label><br>
  <input type="password" name="password" required><br><br>

  <button type="submit">Hoàn tất</button>
</form>
</body>
</html>
