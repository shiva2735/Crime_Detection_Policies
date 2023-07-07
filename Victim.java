public class Victim {
    private int ID;
    private String name;
    private long phoneNo;
    private String address;

    public Victim(int ID, String name, long phoneNo, String address) {
        this.ID = ID;
        this.name = name;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
