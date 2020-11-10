package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Контроллер")
@XmlType(propOrder = {"number", "code", "period", "status",
        "r", "s", "t", "u", "v", "w",
        "moment", "position", "cycleCount"})
public class DataFile {
   private int number, code, r, s, t, u, v, w, moment, position, cycleCount;
   private String numberS, codeS, rS, sS, tS, uS, vS, wS, momentS, positionS, cycleCountS;
   private String status, period;

    public DataFile(int number, int code, String period, String status,
                    int r, int s, int t, int u, int v, int w,
                    int moment, int position, int cycleCount) {
        this.number = number;
        this.code = code;
        this.period = period;
        this.status = status;
        this.r = r;
        this.s = s;
        this.t = t;
        this.u = u;
        this.v = v;
        this.w = w;
        this.moment = moment;
        this.position = position;
        this.cycleCount = cycleCount;

    }

    public DataFile() {

    }

    //Integer

    public int getNumber() {
        return number;
    }

    @XmlElement(name = "Номер")
    public void setNumber(int number) {
        this.number = number;
    }

    public int getCode() {
        return code;
    }

    @XmlElement(name = "Код_Записи")
    public void setCode(int code) {
        this.code = code;
    }

    public String getPeriod() {
        return period;
    }

    @XmlElement(name = "Время")
    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement(name = "Статус")
    public void setStatus(String status) {
        this.status = status;
    }

    public int getR() {
        return r;
    }

    @XmlElement(name = "Фаза_R")
    public void setR(int r) {
        this.r = r;
    }

    public int getS() {
        return s;
    }

    @XmlElement(name = "Фаза_S")
    public void setS(int s) {
        this.s = s;
    }

    public int getT() {
        return t;
    }

    @XmlElement(name = "Фаза_T")
    public void setT(int t) {
        this.t = t;
    }

    public int getU() {
        return u;
    }

    @XmlElement(name = "Фаза_U")
    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    @XmlElement(name = "Фаза_V")
    public void setV(int v) {
        this.v = v;
    }

    public int getW() {
        return w;
    }

    @XmlElement(name = "Фаза_W")
    public void setW(int w) {
        this.w = w;
    }

    public int getMoment() {
        return moment;
    }

    @XmlElement(name = "Момент")
    public void setMoment(int moment) {
        this.moment = moment;
    }

    public int getPosition() {
        return position;
    }

    @XmlElement(name = "Положение")
    public void setPosition(int position) {
        this.position = position;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    @XmlElement(name = "Смена_Цикла")
    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    //String


}
