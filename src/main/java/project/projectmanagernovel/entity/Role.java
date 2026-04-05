package project.projectmanagernovel.entity;

public class Role {
    private int idRole;
    private RoleType roleName;

    public Role(int idRole, RoleType roleName) {
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

    public RoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleType roleName) {
        this.roleName = roleName;
    }
}
