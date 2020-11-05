package Catalog;

public class DateTime {
    private static double high = 0, low = 0;

    private static int day(){
        return (int) ((low + high * 65535)/(3600 * 2400));
    }

    private static double mod(){
        return (low + high * 65535) % (3600 * 2400);
    }

    private static int hour(){
        return (int) Math.floor((mod() / 360000));
    }

    private static int minute(){
        return (int) ((low + high * 65535 - 360000 * 24 * day() - hour() * 360000) / 6000);
    }

    private static int second(){
        return (int) ((low + high * 65535
                - 100 * Math.floor(day()) * 24 * 3600
                - 100 * Math.floor(hour()) * 3600
                - 100 * Math.floor(minute()) * 60) / 100);
    }
}
