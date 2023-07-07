public class DeptOfficer {
    private int officerId;
    private String name;
    private int deptId;
    private String role;

    public DeptOfficer(int officerId, String name, int deptId, String role) {
        this.officerId = officerId;
        this.name = name;
        this.deptId = deptId;
        this.role = role;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
