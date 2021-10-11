package Model;

public class MetaData {
    private int high, low;
    private String statusHex, date, decipher, ed, calibration, sourceCommand, lastCommand, stopping;

    public MetaData(String ed, String calibration, String sourceCommand, String lastCommand, String stopping) {
        this.ed = ed;
        this.calibration = calibration;
        this.sourceCommand = sourceCommand;
        this.lastCommand = lastCommand;
        this.stopping = stopping;
    }

    public MetaData() {

    }

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
        String formatted = ("0000" + hex).substring(hex.length());
        this.statusHex = "0x" + formatted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDecipher() {
        return decipher;
    }

    public void setDecipher(String decipher) {
        this.decipher = decipher;
    }

    // Meta data Status
    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getCalibration() {
        return calibration;
    }

    public void setCalibration(String calibration) {
        this.calibration = calibration;
    }

    public String getSourceCommand() {
        return sourceCommand;
    }

    public void setSourceCommand(String sourceCommand) {
        this.sourceCommand = sourceCommand;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public String getStopping() {
        return stopping;
    }

    public void setStopping(String stopping) {
        this.stopping = stopping;
    }
}
