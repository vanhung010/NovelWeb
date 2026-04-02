<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../../include/header.jsp" />
<section class="section story-hero">
    <div class="story-cover-large">
        <div class="cover-placeholder">Ảnh Bìa</div>
    </div>
    <div class="story-info">
        <h1 class="story-title"><c:out value="${novel.title}" /> </h1>
        <div class="story-meta">
            <span class="meta-item text-success">Trạng thái: <c:out value="${novel.status}" />  </span>
            <span class="meta-item">Tác giả: <a href="#"><c:out value="${novel.author.pername}" /> </a></span>
            <span class="meta-item">
            Thể loại:
            <c:forEach items="listcategory" var="c">
                <span class="genre">${c.name}</span>
            </c:forEach>
          </span>
        </div>

        <div class="story-stats">
            <div class="stat-box">
                <span class="stat-number">1,245</span>
                <span class="stat-label">Chương</span>
            </div>
            <div class="stat-box">
                <span class="stat-number">1.5M</span>
                <span class="stat-label">Lượt đọc</span>
            </div>
        </div>

        <div class="story-actions">
            <button class="btn-primary">📖 Đọc Từ Đầu</button>
            <button class="btn-outline">🔖 Thêm Vào Tủ</button>
        </div>
    </div>
</section>
<main class="container">
    <div class="breadcrumb">
        <a href="index.html">Trang chủ</a> &raquo;
        <a href="#">Tiên Hiệp</a> &raquo;
        <span>Ngự Đạo Khuynh Thiên</span>
    </div>

    <section class="section chapter-section">
        <div class="chapter-header">
            <h2 class="section-title" style="margin-bottom: 0">
                Danh Sách Chương
            </h2>
            <span class="chapter-count">(1,245 chương)</span>
        </div>

        <div class="chapter-toolbar">
            <div class="toolbar-search">
                <input type="text" placeholder="Nhập số chương hoặc tên..." />
                <button type="button">🔍</button>
            </div>
            <div class="toolbar-actions">
                <button class="btn-sort">⇅ Mới nhất</button>
            </div>
        </div>

        <div class="chapter-list-container">
            <h3 class="volume-title">Quyển 5: Đỉnh Phong Chi Chiến</h3>

            <ul class="chapter-grid-full">
                <li><a href="read.html">Chương 1245: Đỉnh cao đối quyết</a></li>
                <li><a href="#">Chương 1244: Bí mật của Thần Vương</a></li>
                <li><a href="#">Chương 1243: Xé rách hư không</a></li>
                <li><a href="#">Chương 1242: Tinh không bạo động</a></li>
                <li><a href="#">Chương 1241: Gặp lại cố nhân</a></li>
                <li><a href="#">Chương 1240: Ranh giới sinh tử</a></li>
                <li><a href="#">Chương 1239: Bước đệm cuối cùng</a></li>
                <li><a href="#">Chương 1238: Phá vây</a></li>
                <li><a href="#">Chương 1237: Bẫy rập trùng trùng</a></li>
                <li><a href="#">Chương 1236: Tàn sát Tinh Không</a></li>
                <li><a href="#">Chương 1235: Huyết quang phủ xuống</a></li>
                <li><a href="#">Chương 1234: Nguy hiểm cận kề</a></li>
            </ul>
        </div>

        <div class="pagination">
            <button class="page-btn" disabled>&laquo; Trước</button>
            <button class="page-btn active">1</button>
            <button class="page-btn">2</button>
            <button class="page-btn">3</button>
            <span class="page-dots">...</span>
            <button class="page-btn">104</button>
            <button class="page-btn">Sau &raquo;</button>
        </div>
    </section>

    <div class="layout-2-cols">
        <div class="main-column">
            <section class="section">
                <h2 class="section-title">Giới Thiệu</h2>
                <div class="story-synopsis">
                    <p>
                        Một góc tĩnh mịch của vũ trụ, nơi ánh sáng quang mang từ chư
                        thần không thể soi rọi tới. Chàng thanh niên mang theo bí mật
                        động trời, từng bước quật khởi từ một tinh cầu xa xôi hẻo lánh.
                    </p>
                    <p>Người nói: Đạo pháp tự nhiên, vạn vật tuân theo quy luật.</p>
                    <p>
                        Hắn lại nói: Ngự đạo trên trời cao, khuynh đảo càn khôn, thế
                        gian này duy ngã độc tôn!
                    </p>
                    <p>
                        Đây là một câu chuyện về hành trình tìm kiếm sự thật của vũ trụ,
                        vươn lên đỉnh cao của hệ thống tu luyện, chém đứt xiềng xích của
                        vận mệnh...
                    </p>
                </div>
            </section>
        </div>
    </div>
</main>
<jsp:include page="../../include/footer.jsp" />