package project.projectmanagernovel.entry;

public class Role {
    private int idRole;
    private String roleName;

    public Role(int idRole, String roleName) {
        this.idRole = idRole;
        this.roleName = roleName;
    }

    public Role() {
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
