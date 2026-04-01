<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="header.jsp" />
<main class="container">
    <section class="section featured-section">
        <h2 class="section-title">Truyện Đề Cử</h2>
        <div class="featured-grid">
          <c:forEach items="${featuredNovel}" var="d">
              <div class="novel-card">
                <a href="search?idNovel=${d.idNovel}">   <div class="novel-cover" style="background-color: #cbd5e1">
                      Cover
                  </div>    <!-- Hình ảnh -->
                  <div class="novel-info">
                      <h3 class="novel-title">${d.title}</h3>
                      <p class="novel-author"> ${d.author.pername}</p>
                  </div>
                </a>
              </div>
          </c:forEach>

        </div>
    </section>

    <div class="layout-2-cols">
        <div class="main-column">
            <section class="section">
                <h2 class="section-title">Mới Cập Nhật</h2>
                <div class="update-list">
                    <c:forEach items="${novelRecentUpdate}" var="c">
                        <div class="update-item">
                            <span class="tag tag-genre">${c.categoryList[0]}</span>
                            <a href="search?novel=${c.idNovel}" class="update-title">${c.title}</a>
                            <a href="#" class="update-chapter">${c.chapterList[0].title}</a>
                            <span class="update-author">${c.author.pername}</span>
                            <span class="update-time">10 phút trước</span>
                        </div>
                    </c:forEach>

                </div>
            </section>
        </div>

        <aside class="sidebar-column">
            <section class="section">
                <h2 class="section-title">Đọc Nhiều Nhất</h2>
                <ul class="ranking-list">
                   <c:forEach items="${commonNovels}" var="c" varStatus="loop">
                       <li class="ranking-item">
                           <span class="rank-num rank-1">${loop.count}</span>
                           <div class="rank-info">
                               <a href="search?idNovel=${c.idNovel}" class="rank-title">${c.title}</a>
                               <span class="rank-views">${c.totalViews} lượt đọc</span>
                           </div>
                       </li>
                   </c:forEach>

                </ul>
            </section>
        </aside>
    </div>
</main>
<jsp:include page="footer.jsp" />