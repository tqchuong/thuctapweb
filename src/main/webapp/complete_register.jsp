
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  fit.hcmuaf.edu.vn.foodmart.model.Account acc = (fit.hcmuaf.edu.vn.foodmart.model.Account) session.getAttribute("account");
  if (acc == null) {
    response.sendRedirect("login.jsp"); // Không có dữ liệu -> quay lại login
    return;
  }
%>
<html>
<head>
    <title>Hoàn tất đăng ký</title>
</head>
<body>
<h2>Hoàn tất đăng ký</h2>
<!-- Hiển thị thông báo lỗi nếu có -->
<c:if test="${not empty error}">
  <div style="color: red;">${error}</div>
  <br>
</c:if>
<form action="completeRegister" method="post">

  <%
    String email = acc.getEmail();
    if (email != null && !email.isEmpty()) {
  %>
  <p>Email: <%= email %></p>
  <input type="hidden" name="email" value="<%= email %>">
  <%
  } else {
  %>
  <label>Email:</label><br>
  <input type="email" name="email" required><br><br>
  <%
    }
  %>

  <input type="hidden" name="fullName" value="<%= acc.getName() %>">

  <label>Username:</label><br>
  <input type="text" name="username" required><br><br>

  <label>Password:</label><br>
  <input type="password" name="password" required><br><br>

  <label>Nhap lai mk:</label><br>
  <input type="password" name="passwordConfirm" required><br><br>

  <button type="submit">Hoàn tất</button>
</form>
</body>
</html>
