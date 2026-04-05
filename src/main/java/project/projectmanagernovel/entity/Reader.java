package project.projectmanagernovel.entity;

import java.util.List;

public class Reader {
    private int idReader;
    private Account account;
    private List<ReadingHistory> readingHistories;
    private String displayname; //tên hiển thị

    public Reader() {
    }

    public Reader(int idReader, Account account, String displayname) {
        this.idReader = idReader;
        this.account = account;
        this.displayname = displayname;
    }

    public int getIdReader() {
        return idReader;
    }

    public void setIdReader(int idReader) {
        this.idReader = idReader;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
}
