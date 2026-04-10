<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../../include/header.jsp" />
<main class="container search-container">
    <!-- Search Results Header -->
    <div class="search-header">
        <h1 class="search-title">Kết Quả Tìm Kiếm</h1>
        <p class="search-query" id="search-query">
            <c:choose>
                <c:when test="${not empty key}">
                    Từ khóa: <span><c:out value="${key}"/></span>
                </c:when>
                <c:otherwise>
                    Từ khóa: <span>Tất cả</span>
                </c:otherwise>
            </c:choose>
        </p>
        <p class="search-results-count" id="results-count">
            Tìm thấy <span id="count"><c:out value="${empty totalResults ? 0 : totalResults}"/></span> kết quả
        </p>
    </div>

    <!-- Filters Section -->
    <div class="search-filters">
        <div class="filter-group">
            <label for="filter-category">Thể Loại:</label>
            <select id="filter-category" class="filter-select">
                <option value="">Tất Cả</option>
                <option value="tienihiep">Tiên Hiệp</option>
                <option value="huyenhuyenz">Huyền Huyễn</option>
                <option value="vongdu">Võng Du</option>
                <option value="dothi">Đô Thị</option>
                <option value="khohuyenz">Khoa Huyễn</option>
                <option value="linhdi">Linh Dị</option>
                <option value="ngontinh">Ngôn Tình</option>
                <option value="quantruong">Quan Trường</option>
            </select>
        </div>

        <div class="filter-group">
            <label for="filter-status">Trạng Thái:</label>
            <select id="filter-status" class="filter-select">
                <option value="">Tất Cả</option>
                <option value="ongoing">Đang Tiến Hành</option>
                <option value="completed">Hoàn Thành</option>
            </select>
        </div>

        <div class="filter-group">
            <label for="filter-sort">Sắp Xếp Theo:</label>
            <select id="filter-sort" class="filter-select">
                <option value="relevance">Mức Độ Liên Quan</option>
                <option value="latest">Mới Nhất</option>
                <option value="popular">Phổ Biến Nhất</option>
                <option value="rating">Xếp Hạng Cao Nhất</option>
            </select>
        </div>

        <button class="btn-reset-filters" id="reset-filters">Đặt Lại</button>
    </div>

    <!-- Search Results Grid -->
    <div class="search-results">
        <div id="results-container" class="results-grid">
            <!-- Results will be loaded here -->
            <c:forEach items="${listnovel}" var="c" >
                <a href="detail?id=${c.idNovel}">
                        <div class="novel-card">
                            <div class="novel-cover">
                                <img src="${c.coverImage}">
                            </div>
                            <div class="novel-info">
                                <h3 class="novel-title"><c:out value="${c.title}"/> </h3>
                                <p class="novel-author"><c:out value="${c.author.pername}"/></p>
                                <p class="novel-description">
                                    <c:out value="${c.description}"/>
                                </p>
                                <div class="novel-meta">
                                    <span class="novel-category"><c:out value="${c.categoryList[0].name}"/></span>
                                    <span class="novel-status"><c:out value="${c.status}"/></span>

                                </div>
                            </div>
                        </div>
                </a>
            </c:forEach>

        </div>
        <!-- No Results Message -->
        <div id="no-results" class="no-results" style="display: none">
            <div class="no-results-icon">🔍</div>
            <h2>Không Tìm Thấy Kết Quả</h2>
            <p>Vui lòng thử với từ khóa khác</p>
        </div>
    </div>

    <!-- Pagination -->
    <c:if test="${totalPages > 1}">
        <div class="pagination" id="pagination">
            <c:choose>
                <c:when test="${currentPage > 1}">
                    <c:url value="search" var="prevUrl">
                        <c:param name="key" value="${key}"/>
                        <c:param name="page" value="${currentPage - 1}"/>
                    </c:url>
                    <a class="pagination-btn prev-btn" href="${prevUrl}">← Trước</a>
                </c:when>
                <c:otherwise>
                    <span class="pagination-btn prev-btn disabled">← Trước</span>
                </c:otherwise>
            </c:choose>

            <div class="pagination-pages">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:url value="search" var="pageUrl">
                        <c:param name="key" value="${key}"/>
                        <c:param name="page" value="${i}"/>
                    </c:url>
                    <a class="pagination-btn page-btn ${i == currentPage ? 'active' : ''}" href="${pageUrl}">${i}</a>
                </c:forEach>
            </div>

            <c:choose>
                <c:when test="${currentPage < totalPages}">
                    <c:url value="search" var="nextUrl">
                        <c:param name="key" value="${key}"/>
                        <c:param name="page" value="${currentPage + 1}"/>
                    </c:url>
                    <a class="pagination-btn next-btn" href="${nextUrl}">Tiếp →</a>
                </c:when>
                <c:otherwise>
                    <span class="pagination-btn next-btn disabled">Tiếp →</span>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</main>
<jsp:include page="../../include/footer.jsp" />