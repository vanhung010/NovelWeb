<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Web Truyện Chữ</title>
    <link rel="stylesheet" href="assests/css/style.css" />
</head>
<body> <header class="header">
    <div class="container header-container">
        <a href="#" class="logo">Novel<span>Web</span></a>

        <div class="search-bar">
            <input type="text" placeholder="Tìm kiếm truyện, tác giả..." />
            <button type="button">Tìm</button>
        </div>

        <nav class="nav-links">
            <div class="dropdown">
                <a href="#" class="dropdown-toggle">Thể Loại</a>
                <ul class="dropdown-menu">
                   <c:forEach items="${Category}" var="c">
                       <li> <a href="search?category=${c.idCategory}">${c.name}</a></li>
                   </c:forEach>


                </ul>
            </div>
            <a href="#">Xếp Hạng</a>
            <a href="#">Truyện Mới</a>
        </nav>

        <c:choose>
            <c:when test="${not empty loggedUser}">
                <div class="user-profile-menu">
                    <div class="avatar-circle">NA</div>
                <span class="user-name"><c:out value="${loggedUser.pofileName}"/></span>
                <ul class="dropdown-menu">
<%--                    <li><a href="#">Tài Khoản</a></li>--%>
<%--                    <li><a href="#">Thư Viện</a></li>--%>
<%--                    <li><a href="#">Lịch Sử Đọc</a></li>--%>
<%--                    <li><a href="#">Cài Đặt</a></li>--%>
    <c:choose>
        <c:when test="${loggedUser.role.idRole == 3}">
                               <li><a href="#">Tài Khoản</a></li>
                              <li><a href="#">Thư Viện</a></li>
                             <li><a href="#">Lịch Sử Đọc</a></li>
                         <li><a href="#">Cài Đặt</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="#">Tài Khoản</a></li>
            <li><a href="#">Quản lí truyện</a></li>
            <li><a href="#">Cài Đặt</a></li>
        </c:otherwise>
    </c:choose>
                    <li><hr></li>
                    <li><a href="logout" class="text-danger">Đăng Xuất</a></li>
                </ul>
            </div></c:when>
            <c:otherwise><div class="user-actions">
                <button class="btn-login" onclick="window.location.href='login'">Đăng Nhập</button>
                <button class="btn-signup" onclick="window.location.href='register'">Đăng Kí</button>
            </div></c:otherwise>
        </c:choose>

    </div>
</header>