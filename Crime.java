public class Crime {
    private int crimeId;
    private String crimeName;

    public Crime(int crimeId, String crimeName) {
        this.crimeId = crimeId;
        this.crimeName = crimeName;
    }

    public int getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(int crimeId) {
        this.crimeId = crimeId;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public void setCrimeName(String crimeName) {
        this.crimeName = crimeName;
    }
}
