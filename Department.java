public class Department {
    private int deptId;
    private String deptName;
    private long helpLine;
    private String location;

    public Department(int deptId, String deptName, long helpLine, String location) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.helpLine = helpLine;
        this.location = location;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public long getHelpLine() {
        return helpLine;
    }

    public void setHelpLine(long helpLine) {
        this.helpLine = helpLine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
