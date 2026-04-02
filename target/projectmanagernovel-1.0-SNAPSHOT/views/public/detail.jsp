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
            <c:forEach items="${listcategory}" var="c">
                <span class="genre">${c.name}</span>
            </c:forEach>
          </span>
        </div>

        <div class="story-stats">
            <div class="stat-box">
                <span class="stat-number"><c:out value="${totalpage}" /> </span>
                <span class="stat-label">Chương</span>
            </div>
            <div class="stat-box">
                <span class="stat-number"><c:out value="${novel.totalViews}" /> M</span>
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


    <section class="section chapter-section">
        <div class="chapter-header">
            <h2 class="section-title" style="margin-bottom: 0">
                Danh Sách Chương
            </h2>
            <span class="chapter-count">( <c:out value="${novel.chapterList.size()}" /> chương)</span>
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
              <c:forEach items="${listchapter}" var="c">
                  <li><a href="read?idchapter=${c.idChapter}">Chương ${c.chapterNumber}: ${c.title}</a></li>
              </c:forEach>
            </ul>
        </div>

        <div class="pagination">
            <c:if test="${currentpage} >1">
                <button href="detail?id=${idNovel}&page=${currentpage -1}" class="page-btn" disabled>&laquo; Trước</button>--%>
            </c:if>

            <c:forEach begin="1" end="${totalpage}" var="i">
                <c:choose>
                    <c:when test="${i == 1 || i == totalpage || (i<= currentpage-1 && i >= currentpage+1)}">
                        <button class="page-btn ${currentpage == 1 ? "active" :""}">${i}</button>
                    </c:when>

                    <c:otherwise>
                        span class="page-dots">...</span>--%>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
<%--            <button class="page-btn" disabled>&laquo; Trước</button>--%>
<%--            <button class="page-btn active">1</button>--%>
<%--            <button class="page-btn">2</button>--%>
<%--            <button class="page-btn">3</button>--%>
<%--            <span class="page-dots">...</span>--%>
<%--            <button class="page-btn">104</button>--%>
<%--            <button class="page-btn">Sau &raquo;</button>--%>
        </div>
    </section>

    <div class="layout-2-cols">
        <div class="main-column">
            <section class="section">
                <h2 class="section-title">Giới Thiệu</h2>
                <div class="story-synopsis">
                  <c:out value="${novel.description}" />
                </div>
            </section>
        </div>
    </div>
</main>
<jsp:include page="../../include/footer.jsp" />