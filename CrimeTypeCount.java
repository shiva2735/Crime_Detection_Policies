public class CrimeTypeCount {
    private String crimeName;
    private int crimeCount;

    public CrimeTypeCount(String crimeName, int crimeCount) {
        this.crimeName = crimeName;
        this.crimeCount = crimeCount;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public int getCrimeCount() {
        return crimeCount;
    }
}
