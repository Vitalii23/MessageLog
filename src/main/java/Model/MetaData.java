package Model;

public class MetaData {
    private int high, low;
    private String statusHex, date;

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getStatusHex() {
        return statusHex;
    }

    public void setStatusHex(int statusHex) {
        String hex = Integer.toHexString(statusHex);
        this.statusHex = "0x" + hex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
