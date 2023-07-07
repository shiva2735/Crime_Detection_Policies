public class Policy {
    private int policyId;
    private String policyName;
    private String description;

    public Policy(int policyId, String policyName, String description) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.description = description;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
