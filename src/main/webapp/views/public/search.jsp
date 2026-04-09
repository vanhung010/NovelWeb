<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../../include/header.jsp" />
<main class="container search-container">
    <!-- Search Results Header -->
    <div class="search-header">
        <h1 class="search-title">Kết Quả Tìm Kiếm</h1>
        <p class="search-query" id="search-query"></p>
        <p class="search-results-count" id="results-count">
            Tìm thấy <span id="count">0</span> kết quả
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
            <c:forEach items="${listnovel}" ></c:forEach>

            <div class="novel-card">
                <div class="novel-cover" style="background-color: #94a3b8">
                    Cover
                </div>
                <div class="novel-info">
                    <h3 class="novel-title">Quang Âm Chi Ngoại</h3>
                    <p class="novel-author">Nhĩ Căn</p>
                    <p class="novel-description">
                        Một bộ truyện huyền huyễn đầy kịch tính và những bất ngờ...
                    </p>
                    <div class="novel-meta">
                        <span class="novel-category">Huyền Huyễn</span>
                        <span class="novel-status">Hoàn Thành</span>
                        <span class="novel-rating">⭐ 4.6</span>
                    </div>
                </div>
            </div>

            <div class="novel-card">
                <div class="novel-cover" style="background-color: #64748b">
                    Cover
                </div>
                <div class="novel-info">
                    <h3 class="novel-title">Đại Phụng Đả Canh Nhân</h3>
                    <p class="novel-author">Mại Báo Tiểu Lang Quân</p>
                    <p class="novel-description">
                        Câu chuyện về những chiến binh dũng cảm và danh dự...
                    </p>
                    <div class="novel-meta">
                        <span class="novel-category">Võng Du</span>
                        <span class="novel-status">Đang Tiến Hành</span>
                        <span class="novel-rating">⭐ 4.5</span>
                    </div>
                </div>
            </div>

            <div class="novel-card">
                <div class="novel-cover" style="background-color: #475569">
                    Cover
                </div>
                <div class="novel-info">
                    <h3 class="novel-title">Quỷ Bí Chi Chủ</h3>
                    <p class="novel-author">Ái Tiềm Thủy Đích Ô Tặc</p>
                    <p class="novel-description">
                        Một câu chuyện bí ẩn lôi cuốn về thế giới quỷ quái...
                    </p>
                    <div class="novel-meta">
                        <span class="novel-category">Linh Dị</span>
                        <span class="novel-status">Hoàn Thành</span>
                        <span class="novel-rating">⭐ 4.7</span>
                    </div>
                </div>
            </div>

            <div class="novel-card">
                <div class="novel-cover" style="background-color: #334155">
                    Cover
                </div>
                <div class="novel-info">
                    <h3 class="novel-title">Ta Là Thần Hào</h3>
                    <p class="novel-author">Vô Danh</p>
                    <p class="novel-description">
                        Một tác phẩm đô thị hiện đại với những tình tiết gay cấn...
                    </p>
                    <div class="novel-meta">
                        <span class="novel-category">Đô Thị</span>
                        <span class="novel-status">Đang Tiến Hành</span>
                        <span class="novel-rating">⭐ 4.4</span>
                    </div>
                </div>
            </div>

            <div class="novel-card">
                <div class="novel-cover" style="background-color: #1e293b">
                    Cover
                </div>
                <div class="novel-info">
                    <h3 class="novel-title">Vạn Cổ Thần Đế</h3>
                    <p class="novel-author">Phi Thiên Cá</p>
                    <p class="novel-description">
                        Kinh điển tiên hiệp toàn từ với những bí kíp võ công tối
                        thượng...
                    </p>
                    <div class="novel-meta">
                        <span class="novel-category">Tiên Hiệp</span>
                        <span class="novel-status">Hoàn Thành</span>
                        <span class="novel-rating">⭐ 4.9</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- No Results Message -->
        <div id="no-results" class="no-results" style="display: none">
            <div class="no-results-icon">🔍</div>
            <h2>Không Tìm Thấy Kết Quả</h2>
            <p>Vui lòng thử với từ khóa khác</p>
        </div>
    </div>

    <!-- Pagination -->
    <div class="pagination" id="pagination">
        <button class="pagination-btn prev-btn" disabled>← Trước</button>
        <div class="pagination-info">
            <span id="current-page">1</span> / <span id="total-pages">5</span>
        </div>
        <button class="pagination-btn next-btn">Tiếp →</button>
    </div>
</main>
<jsp:include page="../../include/footer.jsp" />