package project.projectmanagernovel.entity;

import java.util.ArrayList;
import java.util.List;

public class Novel {
    private int idNovel;
    private String title;
    private Author author;
    private String description; //mô tả truyên
    private String coverImage;
    private NovelStatus status;
    private int totalViews;
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

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public Novel(int idNovel, String title, Author author, String description, String coverImage, NovelStatus status, List<Category> categoryList) {
        this.idNovel = idNovel;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImage = coverImage;
        this.status = status;
        this.categoryList = categoryList;
    }

    public Novel() {
        this.categoryList = new ArrayList<>();
        this.chapterList = new ArrayList<>();

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

    public NovelStatus  getStatus() {
        return status;
    }

    public void setStatus(NovelStatus status) {
        this.status = status;
    }

    public int getTotalChapter() {
        return chapterList.size();
    }
}
