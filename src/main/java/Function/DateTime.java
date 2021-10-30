package Function;

public class DateTime {

    int d, h, m, s;

    private double mod(double high, double low){
        return (low + high * 65535) % (3600 * 2400);
    }

    private int mathFloor(double x){
        return (int) Math.floor(x);
    }

    private int day(double high, double low){
        double x = (low + high * 65535)/(3600 * 2400);
        d = (int) x;
        return d;
    }

    private int hour(double high, double low){
        double x = Math.floor((mod(high, low) / 360000));
        h = (int) x;
        return h;
    }

    private int minute(double high, double low){
        int y1 = (int) high, y2 = (int) low;

        int x1 = (y2 + y1 * 65535);
        int x2 = 360000 * 24 * d;
        int x3 = h * 360000;

        m = ((x1 - x2 - x3) / 6000);
        return m;
    }

    private int second(double high, double low){
        int y1 = (int) high, y2 = (int) low;

        int x1 = (y2 + y1 * 65535);
        int x2 = 100 * mathFloor(d) * 24 * 3600;
        int x3 = 100 * mathFloor(h) * 3600;
        int x4 = (100 * mathFloor(m) * 60);

        s = (x1 - x2 - x3 - x4) / 100;
        return s;
    }

    public String time(int high, int low){
        return day(high, low) + " день " +
                      hour(high, low) + ":" +
                      minute(high, low) + ":" +
                      second(high, low);
    }

}
