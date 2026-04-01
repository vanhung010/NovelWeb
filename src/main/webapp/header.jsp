<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Web Truyện Chữ</title>
    <link rel="stylesheet" href="style.css" />
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

        <div class="user-actions">
            <button class="btn-login">Đăng Nhập</button>
            <button class="btn-signup">Đăng Kí</button>
        </div>
    </div>
</header>