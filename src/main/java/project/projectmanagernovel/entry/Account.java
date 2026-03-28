package project.projectmanagernovel.entry;

public class Account {
    private int idAccount;
    private String userName;
    private String email;
    private Role role;
    private String status;

    public Account(int idAccount, String userName, String email, Role role, String status) {
        this.idAccount = idAccount;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public Account() {
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
