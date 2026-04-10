package project.projectmanagernovel.entity;

import jakarta.ejb.Local;

import java.time.LocalDate;

public class Chapter {
    private int idChapter;
    private Novel novel;
    private int chapterNumber;
    private String title;
    private String content;
    private int viewCount;
    private int wordCount;
    private LocalDate create;

    public LocalDate getCreate() {
        return create;
    }

    public void setCreate(LocalDate create) {
        this.create = create;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public Chapter(int idChapter, Novel novel, int chapterNumber, String title, String content, int viewCount) {
        this.idChapter = idChapter;
        this.novel = novel;
        this.chapterNumber = chapterNumber;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public Chapter() {
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(int idChapter) {
        this.idChapter = idChapter;
    }

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public int getWordCount(){
        if(content == null || content.trim().isEmpty()){
            return 0;
        }
        else return content.trim().split("\\s+").length;
    }
}
