import java.util.Date;

public class CrimeScene {
    private int victimId;
    private String crimeName;
    private String policyName;
    private int officerId;
    private String location;
    private Date crimeDate;

    public CrimeScene(int victimId, String crimeName, String policyName, int officerId, String location, Date crimeDate) {
        this.victimId = victimId;
        this.crimeName = crimeName;
        this.policyName = policyName;
        this.officerId = officerId;
        this.location = location;
        this.crimeDate = crimeDate;
    }

    public int getVictimId() {
        return victimId;
    }

    public void setVictimId(int victimId) {
        this.victimId = victimId;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public void setCrimeName(String crimeName) {
        this.crimeName = crimeName;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;
    }
}
