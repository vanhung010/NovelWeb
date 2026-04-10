package project.projectmanagernovel.entity;

import java.util.List;

public class Author {
    private int idAuthor;
    private Account account;
    private String pername; //kí danh
    private double balance;
    private int totalView;//không có trong sql
    private int totalNovel;

    public int getTotalNovel() {
        return totalNovel;
    }

    public void setTotalNovel(int totalNovel) {
        this.totalNovel = totalNovel;
    }

    private List<WithdrawalRequest> withdrawalRequests;


    public Author(int idAuthor, Account account, String pername, double balance, int totalView) {
        this.idAuthor = idAuthor;
        this.account = account;
        this.pername = pername;
        this.balance = balance;
        this.totalView = totalView;
    }

    public Author() {
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }
}
