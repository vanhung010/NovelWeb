package project.projectmanagernovel.entity;

import java.time.LocalDate;

public class ReadingHistory {
    private Novel novel;
    private Chapter lastReadChapter;
    private LocalDate lastReadAt;

    public ReadingHistory(Novel novel, Chapter lastReadChapter, LocalDate lastReadAt) {
        this.novel = novel;
        this.lastReadChapter = lastReadChapter;
        this.lastReadAt = lastReadAt;
    }

    public ReadingHistory() {
    }

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public Chapter getLastReadChapter() {
        return lastReadChapter;
    }

    public void setLastReadChapter(Chapter lastReadChapter) {
        this.lastReadChapter = lastReadChapter;
    }

    public LocalDate getLastReadAt() {
        return lastReadAt;
    }

    public void setLastReadAt(LocalDate lastReadAt) {
        this.lastReadAt = lastReadAt;
    }
}
