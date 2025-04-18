<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Activity_log" %>
<%@ page import="java.util.List" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.Activity_logDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/admin.css">
    <link href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/admin-responsive.css">
    <!-- Thêm CDN cho DataTables -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <title>Log</title>

    <style>
        .log-section {
            padding: 20px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            margin: 20px;
        }

        .log-section h2 {
            font-size: 1.5rem;
            margin-bottom: 16px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .table-container {
            overflow-x: auto;
        }

        .log-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.95rem;
        }

        .log-table thead {
            background-color: #f8f9fa;
        }

        .log-table th, .log-table td {
            padding: 10px 12px;
            border: 1px solid #dee2e6;
            text-align: left;
            vertical-align: top;
            max-width: 300px;
            word-break: break-word;
        }

        .log-table tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        thead tr {
            background: #B5292F;
            color: rgb(249, 249, 249);
        }

        /* Tùy chỉnh giao diện DataTables */
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 5px 10px;
            margin: 0 2px;
            border: 1px solid #ddd;
            background-color: #fff;
            color: #333;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            background-color: #B5292F;
            color: white !important;
            border: 1px solid #B5292F;
        }

        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            background-color: #f2f2f2;
            border: 1px solid #ddd;
        }

        .dataTables_wrapper .dataTables_filter input {
            padding: 5px;
            margin-left: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<header class="header">
    <button class="menu-icon-btn">
        <div class="menu-icon">
            <i class="fa-regular fa-bars"></i>
        </div>
    </button>
</header>
<div class="container">
    <aside class="sidebar open">
        <div class="top-sidebar">
            <a href="#" class="channel-logo">
                <img src="image/shoppingcart/7.png" alt="Channel Logo">
            </a>
            <div class="hidden-sidebar your-channel">
                <img src="image/shoppingcart/8.png" alt="Your Channel">
            </div>
        </div>

        <div class="middle-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item tab-content active">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-house"></i></div>
                        <div class="hidden-sidebar">Trang tổng quan</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-pot-food"></i></div>
                        <div class="hidden-sidebar">Sản phẩm</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-users"></i></div>
                        <div class="hidden-sidebar">Khách hàng</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-basket-shopping"></i></div>
                        <div class="hidden-sidebar">Đơn hàng</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-chart-simple"></i></div>
                        <div class="hidden-sidebar">Thống kê</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-ticket"></i></div>
                        <div class="hidden-sidebar">Voucher</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fa-building"></i></div>
                        <div class="hidden-sidebar">Nhà cung cấp</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fa-truck-fast"></i></div>
                        <div class="hidden-sidebar">Vận chuyển</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="showLog.jsp" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fas fa-list"></i></div>
                        <div class="hidden-sidebar">Nhật kí hoạt động</div>
                    </a>
                </li>
            </ul>
        </div>
        <div class="bottom-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item user-logout">
                    <a href="home.jsp" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-thin fa-circle-chevron-left"></i></div>
                        <div class="hidden-sidebar">Trang chủ</div>
                    </a>
                </li>
                <li class="sidebar-list-item user-logout">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-circle-user"></i></div>
                        <div class="hidden-sidebar" id="name-acc"></div>
                    </a>
                </li>
                <li class="sidebar-list-item user-logout">
                    <a href="login.jsp" class="sidebar-link" id="logout-acc">
                        <div class="sidebar-icon"><i class="fa-light fa-arrow-right-from-bracket"></i></div>
                        <div class="hidden-sidebar">Đăng xuất</div>
                    </a>
                </li>
            </ul>
        </div>
    </aside>

    <div class="log-section">
        <h2><i class="fas fa-list"></i> Nhật ký hoạt động</h2>
        <div class="table-container">
            <table id="logTable" class="log-table">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Người dùng</th>
                    <th>Hành động</th>
                    <th>Mức độ</th>
                    <th>IP</th>
                    <th>Thời gian</th>
                    <th>Trang</th>
                    <th>Tài nguyên</th>
                    <th>Dữ liệu cũ</th>
                    <th>Dữ liệu mới</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Activity_log> logs = Activity_logDAO.getAllLogs();
                    int stt = 1;
                    if (logs != null && !logs.isEmpty()) {
                        for (Activity_log log : logs) {
                %>
                <tr>
                    <td><%= stt++ %></td>
                    <td><%= log.getUsername() %></td>
                    <td><%= log.getAction() %></td>
                    <td><%= log.getLevel_log() %></td>
                    <td><%= log.getIp_address() %></td>
                    <td><%= log.getTime_log() %></td>
                    <td><%= log.getSource_page() %></td>
                    <td><%= log.getResource() %></td>
                    <td><%= log.getOld_data() %></td>
                    <td><%= log.getNew_data() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr><td colspan="10">Không có dữ liệu nhật ký.</td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="js/admin.js"></script>
<script>
    $(document).ready(function() {
        $('#logTable').DataTable({
            paging: true,
            searching: true,
            ordering: true,
            pageLength: 10,
            lengthMenu: [5, 10, 25, 50],
            language: {
                search: "Tìm kiếm:",
                paginate: {
                    first: "Đầu",
                    last: "Cuối",
                    next: "Tiếp",
                    previous: "Trước"
                },
                lengthMenu: "Hiển thị _MENU_ bản ghi mỗi trang",
                info: "Hiển thị _START_ đến _END_ của _TOTAL_ bản ghi",
                infoEmpty: "Không có bản ghi nào",
                emptyTable: "Không có dữ liệu trong bảng"
            }
        });
    });
</script>
</body>
</html>