package Catalog;

public class DateTime {

    private static int day(double high, double low){
        return (int) ((low + high * 65535)/(3600 * 2400));
    }

    private static double mod(double high, double low){
        return (low + high * 65535) % (3600 * 2400);
    }

    private static int hour(double high, double low){
        return (int) Math.floor((mod(high, low) / 360000));
    }

    private static int minute(double high, double low){
        return (int) ((low + high * 65535 - 360000 * 24 * day(high, low) - hour(high, low) * 360000) / 6000);
    }

    private static int second(double high, double low){
        return (int) ((low + high * 65535
                - 100 * Math.floor(day(high, low)) * 24 * 3600
                - 100 * Math.floor(hour(high, low)) * 3600
                - 100 * Math.floor(minute(high, low)) * 60) / 100);
    }

    public String time(int high, int low){
        return day(high, low) + " день " +
                      hour(high, low) + ":" +
                      minute(high, low) + ":" +
                      second(high, low);
    }

}
