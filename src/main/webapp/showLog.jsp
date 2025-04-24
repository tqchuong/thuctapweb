<%--<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Activity_log" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.Activity_logDAO" %>--%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>

<%--<html lang="vi">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <link rel="stylesheet" href="css/admin.css">--%>
<%--    <link href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" rel="stylesheet" type="text/css"/>--%>
<%--    <link rel="stylesheet" href="css/admin-responsive.css">--%>
<%--    <!-- Thêm CDN cho DataTables -->--%>
<%--    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">--%>
<%--    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>--%>
<%--    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>--%>
<%--    <title>Log</title>--%>

<%--    <style>--%>

<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<header class="header">--%>
<%--    <button class="menu-icon-btn">--%>
<%--        <div class="menu-icon">--%>
<%--            <i class="fa-regular fa-bars"></i>--%>
<%--        </div>--%>
<%--    </button>--%>
<%--</header>--%>
<%--<div class="container">--%>
<%--    <aside class="sidebar open">--%>
<%--        <div class="top-sidebar">--%>
<%--            <a href="#" class="channel-logo">--%>
<%--                <img src="image/shoppingcart/7.png" alt="Channel Logo">--%>
<%--            </a>--%>
<%--            <div class="hidden-sidebar your-channel">--%>
<%--                <img src="image/shoppingcart/8.png" alt="Your Channel">--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="middle-sidebar">--%>
<%--            <ul class="sidebar-list">--%>
<%--                <li class="sidebar-list-item tab-content active">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-house"></i></div>--%>
<%--                        <div class="hidden-sidebar">Trang tổng quan</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-pot-food"></i></div>--%>
<%--                        <div class="hidden-sidebar">Sản phẩm</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-users"></i></div>--%>
<%--                        <div class="hidden-sidebar">Khách hàng</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-basket-shopping"></i></div>--%>
<%--                        <div class="hidden-sidebar">Đơn hàng</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-chart-simple"></i></div>--%>
<%--                        <div class="hidden-sidebar">Thống kê</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-ticket"></i></div>--%>
<%--                        <div class="hidden-sidebar">Voucher</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-solid fa-building"></i></div>--%>
<%--                        <div class="hidden-sidebar">Nhà cung cấp</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-solid fa-truck-fast"></i></div>--%>
<%--                        <div class="hidden-sidebar">Vận chuyển</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item tab-content">--%>
<%--                    <a href="showLog.jsp" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-solid fas fa-list"></i></div>--%>
<%--                        <div class="hidden-sidebar">Nhật kí hoạt động</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--        <div class="bottom-sidebar">--%>
<%--            <ul class="sidebar-list">--%>
<%--                <li class="sidebar-list-item user-logout">--%>
<%--                    <a href="home.jsp" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-thin fa-circle-chevron-left"></i></div>--%>
<%--                        <div class="hidden-sidebar">Trang chủ</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item user-logout">--%>
<%--                    <a href="#" class="sidebar-link">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-circle-user"></i></div>--%>
<%--                        <div class="hidden-sidebar" id="name-acc"></div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--                <li class="sidebar-list-item user-logout">--%>
<%--                    <a href="login.jsp" class="sidebar-link" id="logout-acc">--%>
<%--                        <div class="sidebar-icon"><i class="fa-light fa-arrow-right-from-bracket"></i></div>--%>
<%--                        <div class="hidden-sidebar">Đăng xuất</div>--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--    </aside>--%>

<%--    <div class="section">--%>
<%--        <!-- Bộ lọc và tìm kiếm nhật ký -->--%>
<%--        <div class="admin-control">--%>
<%--            <div class="admin-control-left">--%>
<%--                <select name="level-log-filter" id="level-log-filter">--%>
<%--                    <option value="all">Tất cả</option>--%>
<%--                    <option value="info">Thông tin</option>--%>
<%--                    <option value="warning">Cảnh báo</option>--%>
<%--                    <option value="error">Lỗi</option>--%>
<%--                </select>--%>
<%--            </div>--%>
<%--            <div class="admin-control-center">--%>
<%--                <form action="" class="form-search">--%>
<%--                    <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>--%>
<%--                    <input id="form-search-log" type="text" class="form-search-input" placeholder="Tìm kiếm người dùng...">--%>
<%--                </form>--%>
<%--            </div>--%>
<%--            <div class="admin-control-right">--%>
<%--                <form action="" class="fillter-date">--%>
<%--                    <div>--%>
<%--                        <label for="time-start-log">Từ</label>--%>
<%--                        <input type="date" class="form-control-date" id="time-start-log">--%>
<%--                    </div>--%>
<%--                    <div>--%>
<%--                        <label for="time-end-log">Đến</label>--%>
<%--                        <input type="date" class="form-control-date" id="time-end-log">--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--                <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <!-- Bảng nhật ký hoạt động -->--%>
<%--        <div class="table" style="margin-top: 10px;">--%>
<%--            <table width="100%">--%>
<%--                <thead>--%>
<%--                <tr>--%>
<%--                    <td>STT</td>--%>
<%--                    <td>Người dùng</td>--%>
<%--                    <td>Hành động</td>--%>
<%--                    <td>Mức độ</td>--%>
<%--                    <td>IP</td>--%>
<%--                    <td>Thời gian</td>--%>
<%--                    <td>Trang</td>--%>
<%--                    <td>Tài nguyên</td>--%>
<%--                    <td>Dữ liệu cũ</td>--%>
<%--                    <td>Dữ liệu mới</td>--%>
<%--                </tr>--%>
<%--                </thead>--%>

<%--                <tbody id="log-activity-body">--%>
<%--                <%--%>
<%--                    List<Activity_log> logs = Activity_logDAO.getAllLogs();--%>
<%--                    int stt = 1;--%>
<%--                    if (logs != null && !logs.isEmpty()) {--%>
<%--                        for (Activity_log log : logs) {--%>
<%--                %>--%>
<%--                <tr>--%>
<%--                    <td><%= stt++ %></td>--%>
<%--                    <td><%= log.getUsername() %></td>--%>
<%--                    <td><%= log.getAction() %></td>--%>
<%--                    <td>--%>
<%--                        <span class="<%= log.getLevel_log().equals("ERROR") ? "status-no-complete" :--%>
<%--                                        log.getLevel_log().equals("WARNING") ? "status-warning" :--%>
<%--                                        "status-complete" %>">--%>
<%--                            <%= log.getLevel_log() %>--%>
<%--                        </span>--%>
<%--                    </td>--%>
<%--                    <td><%= log.getIp_address() %></td>--%>
<%--                    <td><%= log.getTime_log() %></td>--%>
<%--                    <td><%= log.getSource_page() %></td>--%>
<%--                    <td><%= log.getResource() %></td>--%>
<%--                    <td><%= log.getOld_data() %></td>--%>
<%--                    <td><%= log.getNew_data() %></td>--%>
<%--                </tr>--%>
<%--                <%--%>
<%--                    }--%>
<%--                } else {--%>
<%--                %>--%>
<%--                <tr><td colspan="10">Không có dữ liệu nhật ký.</td></tr>--%>
<%--                <%--%>
<%--                    }--%>
<%--                %>--%>
<%--                </tbody>--%>
<%--            </table>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<%--<script src="js/admin.js"></script>--%>
<%--<script>--%>
<%--    $(document).ready(function() {--%>
<%--        $('#logTable').DataTable({--%>
<%--            paging: true,--%>
<%--            searching: true,--%>
<%--            ordering: true,--%>
<%--            pageLength: 10,--%>
<%--            lengthMenu: [5, 10, 25, 50],--%>
<%--            language: {--%>
<%--                search: "Tìm kiếm:",--%>
<%--                paginate: {--%>
<%--                    first: "Đầu",--%>
<%--                    last: "Cuối",--%>
<%--                    next: "Tiếp",--%>
<%--                    previous: "Trước"--%>
<%--                },--%>
<%--                lengthMenu: "Hiển thị _MENU_ bản ghi mỗi trang",--%>
<%--                info: "Hiển thị _START_ đến _END_ của _TOTAL_ bản ghi",--%>
<%--                infoEmpty: "Không có bản ghi nào",--%>
<%--                emptyTable: "Không có dữ liệu trong bảng"--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>