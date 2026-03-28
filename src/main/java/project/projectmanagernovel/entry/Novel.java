package project.projectmanagernovel.entry;

import java.util.List;

public class Novel {
    private int idNovel;
    private String title;
    private Author author;
    private String description; //mô tả truyên
    private String coverImage;
    private String status;
    private List<Category> categoryList;
    private List<Chapter> chapterList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public Novel(int idNovel, String title, Author author, String description, String coverImage, String status, List<Category> categoryList) {
        this.idNovel = idNovel;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImage = coverImage;
        this.status = status;
        this.categoryList = categoryList;
    }

    public Novel() {
    }

    public int getIdNovel() {
        return idNovel;
    }

    public void setIdNovel(int idNovel) {
        this.idNovel = idNovel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
