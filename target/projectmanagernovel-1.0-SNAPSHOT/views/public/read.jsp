<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../../include/header.jsp" />
<main class="reading-container">
    <div class="chapter-header-info">
        <h1 class="chapter-title">Chương ${currentchapter.chapterNumber}: ${currentchapter.title}</h1>
        <div class="chapter-meta">
            <span>Cập nhật: 10 phút trước</span>
            <span>•</span>
            <span>${currentchapter.wordCount} chữ</span>
        </div>
    </div>

    <div class="chapter-nav">
        <a href="read?idchapter=${idchapterprevious}&idnovel=${currentchapter.novel.idNovel}" class="btn-nav">&laquo; Chương Trước</a>
        <a href="detail.html" class="btn-nav btn-nav-menu">🗂️ Mục Lục</a>
        <a href="read?idchapter=${idchapternext}&idnovel=${currentchapter.novel.idNovel}" class="btn-nav">Chương Sau &raquo;</a>
    </div>

    <div class="reading-content">
        <p>
            ${currentchapter.content}
        </p>
    </div>

    <div class="chapter-nav nav-bottom">
        <a href="#" class="btn-nav">&laquo; Chương Trước</a>
        <a href="detail.html" class="btn-nav btn-nav-menu">🗂️ Mục Lục</a>
        <a href="#" class="btn-nav">Chương Sau &raquo;</a>
    </div>

    <div class="chapter-comments">
        <h3>Bình luận (128)</h3>
        <div class="comment-input-box">
          <textarea
                  placeholder="Nhập bình luận của bạn về chương này..."
          ></textarea>
            <button class="btn-primary">Gửi Bình Luận</button>
        </div>
    </div>
</main>
<jsp:include page="../../include/footer.jsp" />